
## VSee Code Challenge Repo

### Description
This repository contains the test scripts for VSee Code assessment. Test scenarios/ requirements covered are listed below:

- Write a web testing framework do the following:
  - Open [room_url] on the browser and enter the waiting room (userA).
  - Open [room_url] on another browser, click For Providers, login [provider_credential] as userB and call userA (select “Continue on this browser”)
  - UserB sends a chat message and verifies userA can receive it.
  - UserB ends the call, then userA ends the call.
- (Optional) Expand the framework so that the test script performs on different machines.
- For example:
  - Machine C → run the test script
  - Machine A → userA
  - Machine B → userB
  - Hint: use selenium grid
- Write an automated test script to retrieve information from this a github project (for example this one https://github.com/SeleniumHQ) using github API
  - How many total open issues are there across all repositories?
  - Sort the repositories by date updated in descending

### Technologies used
Selenium, TestNG, RestAssured, SeleniumGrid, Docker

### Setup and Installation

#### Clone the Repo
```
    git clone https://github.com/thaohuynh44/vsee-code-challenge.git
    cd vsee-code-challenge
```

#### Build the project
```
    mvn clean install
```

#### Run tests
```
    mvn clean test
```

### Usage notes

#### How to run tests in Selenium Grid with Docker

- **Prerequistes**: Make sure your Docker is up and running
- At the root directory, run the command line ```docker-compose up -d```
- Navigate to ```http:localhost:4444``` to verify the Selenium Grid
- In file ```pom.xml``` update the property ```seleniun.grid.enabled``` to true
- Run tests directly by clicking on TestNG file or by running the command line ```mvn clean test```

Before running tests, go to file ```test/resources/config/default.properties``` and put your login credentials into ```providerEmail``` and ```providerEmail```

#### Project structure
TBD