# Social Multiplication

## Prerequisites
* SpringBoot 1.5.21
* Java 8 (1.8)

## Installation
### Java JDK (not JRE)
1.  Download JDK (https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and run installer

## Run
1. ```./mvnw spring-boot:run```
2. Visit: ```http://localhost:8080```
3. DB Portal: ```http://localhost:8080/h2-console```

## Tests
* ```./mvnw test```
* ```./mvnw -Dtest=MultiplicationServiceTest test```

## Deploying
1. Package into Jar: ```./mvnw package```
2. ```cd ./target```
3. Run (computer must have JRE installed): ```java -jar social-multiplication-v3-0.3.0-SNAPSHOT.jar```

## Notes
### Monolith First
When you develop software the Agile way, you can’t wait a long time to deliver your software. Neither can you spend weeks designing your complete system in advance, with details. If you start splitting your project into microservices from the beginning, it will take much longer than building a monolith. Why? Because it’s technically more difficult to deploy, orchestrate, and test a system based on microservices.

Another good reason to not to start from scratch with microservices: your system will likely have poor software design, worse than when building a monolith. This prediction has more to do with the way people work: when you divide the work into different pieces and assign them to different teams, the teams often start caring about their pieces of work and not about the entire system. Design, as it was done at the beginning, can be corrupted easily. Teams can start ignoring the company’s common guidelines and principles unless you keep all microservices in control (and that is a very hard work for architects). End-to-end testing is much more difficult to set up with all those pieces evolving on their own. In this scenario, I’m assuming you already had a clear picture of the APIs and ways of communication between your different microservices. If you don’t even have that, I’d stick to the monolith to start with, no doubt.

That’s not to say that you can’t be successful in predicting your microservice boundaries in advance and developing them in parallel
but, in that case, you should pay much more attention to deployment, integration testing, common standards compliance, clear APIs, logging and monitoring, error handling, communication channels between teams, etc. Failing only in one of these topics can jeopardize your project if you start directly with microservices.

Consider a better approach: plan a monolithic application first. Plan it in a way that it can be split later with little effort:
* *Compartmentalize your code in root packages defining your domain contexts*. For instance, your application may have functionalities related to customers (person, company, address, etc.) and others related to orders (order generation, dispatch, handling, etc.). Instead of packaging your root structure directly by layers, you can create top levels where you first split customers and orders. Then, replicate the layering for each of them (e.g., controller, repository, domain, and service) and make sure you follow good practices for class visibility (implementations are package-private). The main advantages you get with this structure are that you keep business logic inaccessible across domain contexts, and that later you should be able to extract one complete root package as a microservice if you need it, with less refactoring.
* *Take advantage of dependency injection: base your code on interfaces, and let Spring do its job injecting the implementations*. Refactoring using this pattern is much easier. For example, you may change an implementation to call to a different microservice instead of keeping all the business logic in one place
* *Once you have identified the contexts (e.g., customers and orders) give them a consistent name across your application*. Move your domain logic here and there (easier with a small monolith) during the design phase until boundaries are clear, and then respect the boundaries. Never take shortcuts tangling business logic across contexts just because you can. Always keep in mind that the monolith should be prepared to evolve.
* *Find common patterns and identify what can be later extracted as common libraries, for example*.
* *Clearly communicate to the project manager to plan time in later releases to split the monolith*. Explain the strategy and create the culture: refactoring is going to be necessary and there is nothing wrong with it.



## Book Source Code
https://github.com/microservices-practical

## Upto
Page 96


Completing User Story 2
