# misk-db feature

`misk.Feature` is a key abstraction to support feature flags in a Misk service.

Feature flags provide for dynamic configuration, rollout by rules, and are a building block for experimentation and deploy-less operations.

`misk.db.DbFeatureFlags` is an implementation of the `misk.Feature` ecosystem that uses a SqlDelight database as the storage layer instead of in-memory (like `FakeFeatureFlags`) or a 3rd-party API (ie. `LaunchDarklyFeatureFlags`).

`DbFeatureFlags` will apply additional read load to databases given `misk.Feature` usage within high load business logic. Use of Redis or other caching layers could improve scaling properties.

High QPS services should probably use a 3rd-party API better suited for high scale.

## Usage

- Copy `sqldelight-migrations/v001_create_features.sqm` to your `resources/db-migrations/` directory with an incremented version number after your existing migrations.
- Install `DbFeatureFlagsModule()` in your service builder.
- Bind access to set ACLs on gRPC FeatureService endpoints
  ```kotlin
  import misk.db.feature.api.MiskDbFeatureApiAccess
  
  multibind<AccessAnnotationEntry>().toInstance(
    AccessAnnotationEntry<MiskDbFeatureApiAccess>(capabilities = listOf("admins"))
  )
  ```
- Inject `misk.Feature("feature-name")` in business logic code where you want to evaluate the flag to control code flow.
  - Before usage in deployed code, feature flag **must** be created with initial config using the gRPC FeatureService or admin dashboard.

## TODO

- [ ] Ignore sqldelight generated files, migrations...etc in the library JAR
