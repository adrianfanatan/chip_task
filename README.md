# Random User API Tests

## Overview

This project contains automated tests for the Random User API using Java, TestNG, and RestAssured.

The tested microservice is https://randomuser.me/

All the tests are running to every commit pushed in the main branch. This is configured in the pom.xml file.

However, there other 2 tests suites created:

   - **regression.xml** - the scope of this xml is to run all the tests from the project by running this suite
   - **sanity.xml** - the scope of this xml is to run a minim um set of tests that should always pass (the critical tests)

## Prerequisites

- JDK 11 or higher
- Maven

## Setup

1. Clone the repository:
   ```bash
   git clone git@github.com:adrianfanatan/chip_task.git

2. Run tests:
   
   Just run the class or the test you want --> no needed extra parameters in the VM options to run the tests

## CI/CD

There is a CI/CD pipeline used directly from GitHub and can be visible on the GitHub project from **Actions** tab 

or directly access the link below

https://github.com/adrianfanatan/chip_task/actions

**Note!** - the pipeline it's failed cause one of the tests is failing somehow intended.

## Reporting mechanism

AllureReport with testNG was used for reporting the stats of the project tests.

You can check these reports in 2 ways:

   - GitHub - on the Actions tab 
   - Locally: run the commant to run the tests  ```bash mvn test``` and after that ```bash allure serve allure-results```
     (this will open a new browser window with all the stats from the tests interpreted in browser) 
            