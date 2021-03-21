# Serviceability Calculator CLI Application

This project is an application to calculate serviceability of a given loan application.

## Getting Started

### Prerequisites
* Git
* JDK 11
* Gradle 6.0 or later

### Clone
To get started you can simply clone this repository using git:
```
https://github.com/KalpaD/TechnicalChallenge.git
cd part-1/serviceability-cal
```

### Build an executable JAR
You can run the application from the command line using:
```
./gradlew bootRun --args '<PATH_TO_LOAN_APPLICATION>/application-with-full-data.json'

with following JSON content in the application-with-full-data.json file

{
  "incomes": [
    {
      "description": "salary",
      "frequency": "M",
      "value": 1000.00
    },
    {
      "description": "rental income",
      "frequency": "M",
      "value": 400.00
    }
  ],
  "expenses": [
    {
      "description": "mortgage",
      "frequency": "M",
      "value": 200.00
    },
    {
      "description": "entertainment",
      "frequency": "M",
      "value": 13.00
    }
  ]
}

```
Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources with:
```
./gradlew clean build
```
Then you can run the JAR file with:
```
cd build/libs
java -jar *.jar
```
application will prompt you to enter the absolute path for the application file.
```
Enter the absolute path for the application.json file:

<PATH_TO_LOAN_APPLICATION>/application-with-full-data.json
```

### Assumptions

##### At the moment this method assumes there are only two frequencies for income and expenses.

