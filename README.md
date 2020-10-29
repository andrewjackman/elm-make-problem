# Gradle elm-plugin issue

Trying to build an `http4k` server that serves up an `Elm` UI. Using the gradle Elm plugin `https://github.com/tmohme/gradle-elm-plugin`.

## Solved

Solution (`executionDir` specified from project root 🤮):

```
elm {
    sourceDir = file('src/main/elm/ui')
    executionDir = "${rootProject.projectDir}/webserver/src/main/elm"
    executable = org.mohme.gradle.Executable.Provided.INSTANCE
    targetModuleName = 'elm.js'
    debug = false
    optimize = true
}
```

## Original Problem

The following fails:

```
./gradlew elmMake
 ```

But, if do the following, `elmMake` works:

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