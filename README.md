# Neuralm-Client

The neuralm client communicates with the [neuralm-server](https://github.com/neuralm/Neuralm-Server) and receives and simulates the neural networks.

These neural networks are simulated and tested on the client, and mutated on the server. This way, we can combine the processing power of multiple computers and run numerous training sessions at the same time.
This is needed to reduce the overall training time.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
You will need the following tools:

* [Jetbrains IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Java JDK 8](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

### Setup
Follow these steps to get your development environment set up:

  1. Clone the repository.
  2. Import the build.gradle into IntelliJ.

## Running the tests
Run the gradle test task to run the tests.

## Deployment
To deploy the project to your local maven repository run the gradle publishToMavenLocal task 

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/neuralm/Neuralm-Java-Client/tags). 

## Authors

* **Glovali** - *Initial work* - [Metalglove](https://github.com/metalglove)
* **Suppergerrie2** - *Initial work* - [Suppergerrie2](https://github.com/suppergerrie2)

See also the list of [contributors](https://github.com/neuralm/Neuralm-Java-Client/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
