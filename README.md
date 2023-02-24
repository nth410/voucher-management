# Voucher Management Application

This is a simple voucher management application that allows you to create, retrieve, update, delete and redeem vouchers.

## Prerequisites
- Java 17 or higher
- Spring Boot 3.0.2
- Spring Data MongoDB
- MongoDB
- JUnit5
```$xslt
# To check java version, please run the command
java -version
# To check maven version, please run the command
mvn -version
```

## Getting Started
- Clone the repository: git clone https://github.com/nth410/voucher-management.git
- Navigate to the project root directory: cd voucher-management
- Build the project: mvn clean install
- Run the application: mvn spring-boot:run

## API testing:
Please check in "/postman_collection" folder.

## Project Structure:
- src/main/java/com/example/vouchermanagement contains the main source code for the application
- src/test/java/com/example/vouchermanagement contains the test source code for the application
- src/main/resources contains the application configuration files and the database scripts
