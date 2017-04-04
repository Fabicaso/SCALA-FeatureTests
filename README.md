#Feature tests

##Instructions
Copy the chrome driver in `/usr/local/bin/chromedriver`.
Make sure you update the driver with the version you want to test.

## Running the test

```
sbt clean test -Denv=stg

```

- env: You can specify the environment you want to execute your test (qa|stg|prd). This is optional value 
(in that case it is going to read the default config from `application.conf`).

You can verify the config running `ConfigRunner` app.

##TODO
 - Build the report
 