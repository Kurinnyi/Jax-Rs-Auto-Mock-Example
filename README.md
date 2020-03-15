# Jax-Rs-Auto-Mock-Example
This is a template example project that shows how to use Jax-Rs-Auto-Mock<br>
There is an example of kotlin and YAML DSLs that can be used to define mocks.<br>
Kotlin definitions are located in here /src/main/java/example/mocks<br>
Yaml located here /src/main/resources/stubs.

### How to use it:
- Please clone this repository.
- Add your resource interfaces and DTOs as a dependency into build.gradle.
- Edit stubs definitions to match your needs
- Remove 'removeit' package
- Start the main class from your ide to launch the mock server 
- Alternatively, to the last step, execute `gradlew shadowJar` to produce a fat jar
- Find it here \build\libs\jax-rs-auto-mock-example-0.1-all.jar
- Launch mock server with java -jar jax-rs-auto-mock-example-0.1-all.jar


