# Getting Started

This is a simple stock order matching system built with **Spring Boot** and **H2 in-memory database**. It simulates basic functionalities of placing and matching buy/sell orders in a stock exchange environment.

---

## 🚀 Features

- 🟢 Place buy and sell orders
- ⚖️ Automatically match compatible orders
- 📋 List open and executed orders
- 💾 H2 in-memory database
- ✅ DTO & Entity layer separation with validation
- 🧪 Unit testing with JUnit & Mockito

---

## ⚙️ Requirements

- Java 21 or later
- Gradle

---

## 🛠️ Build & Run

### Clone the repository (if needed)

# bash
git clone https://github.com/selectedhawk/brokage-test.git
cd brokage-test
./gradlew build
# Run the application
./gradlew bootRun
# Or run the generated JAR:
java -jar build/libs/stock-order-system-0.0.1-SNAPSHOT.jar

# H2 Console Access
http://localhost:8080/h2-console

# Running Tests
./gradlew test

🧠 Technologies Used
Java 21

Spring Boot

Spring Data JPA

H2 Database

Lombok

Gradle

JUnit & Mockito

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.5/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.5/gradle-plugin/packaging-oci-image.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.4.5/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.5/reference/web/servlet.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

