# misk-db feature sample

This project serves as a sample service to show how one could use the misk-db-feature library to leverage DB backed misk.Feature flags.

The service in this example is named `flagpole`. It includes feature flagged business logic for an imaginary business that does recurring maintenance on different billboard locations.

## Getting Started

```bash
$ gradle :misk-db:misk-db-feature-sample:run 
```

Then open in your browser:

- [0.0.0.0:8080/_admin/feature/](0.0.0.0:8080/_admin/feature/): Admin dashboard Feature tab using Hotwire, Tailwinds, and SqlDelight