# Feature tests

## Instructions
Copy the chrome driver `chromedriver` to `/usr/local/bin/`.
Make sure you update the driver with the version you want to test.

## Running the test

```
sbt clean test -Denv=stg

```

- env: You can specify the environment you want to execute your test (qa|stg|prd). This is optional value 
(in that case it is going to read the default config from `application.conf`).

You can verify the config running `ConfigRunner` app.

### Run specific tests

If you want to run only feature test you need to execute:
```
sbt "test-only *FeatureTestRunner"
```

If you want to run only feature test you need to execute:
```
sbt "test-only *SmokeTestRunner"
```

## Report generation

You can generate the report at the end, executing:

```
sbt run

```

 