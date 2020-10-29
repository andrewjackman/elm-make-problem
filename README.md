# Gradle elm-plugin-issue

Trying to build an `http4k` server that serves up an `Elm` UI.

The following fails:

```
./gradlew elmMake
 ```

But if do the following, `elmMake` works:

```
cd webserver
../gradlew elmMake
 ``` 

This also fails:
```
./gradlew webserver:elmMake
```

with:
```
Caused by: java.io.IOException: Cannot run program "elm" (in directory "src/main/elm"): error=2, No such file or directory
        at org.mohme.gradle.ElmMakeTask.elmMake(ElmMakeTask.kt:114)
        ... 91 more
Caused by: java.io.IOException: error=2, No such file or directory
        ... 92 more
```