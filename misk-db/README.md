# misk-db 

A set of implementations of common Misk abstractions backed by a local database instead of AWS primitives.

Extending the existing Misk interfaces makes it possible for services to start with simpler architectures and then grow into fuller AWS offerings without changing any implementation code. Bind up the AWS alternative, migrate in-flight jobs etc., and you're good to go.

## Feature

Feature is a DB backed implementation for `Misk.Feature`, allowing for full use of Misk's feature flags abstraction using a SqlDelight DB table instead of 3rd party LaunchDarkly or limited FakeFeatureFlags in-memory representation.
