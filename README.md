# Gradle elm-plugin-issue

Trying to build an `http4k` server that serves up an `Elm` UI.

The following fails:

```
./gradlew clean test elmMake
 ```

But if do the following, `elmMake` works:

```
cd webserver
../gradlew elmMake
 ``` 