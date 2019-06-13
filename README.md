# Social Multiplication

## Prerequisites
* SpringBoot 1.5.21
* Java 8 (1.8)
* RabbitMQ

## Installation
### Java JDK (not JRE)
1.  Download JDK (https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and run installer

### RabbitMQ
1. ```brew install rabbitmq```

### Jetty
1. Download latest release (.tgz file) from https://www.eclipse.org/jetty/download.html
2. Unzip it into a some location you have read and write access (```~/Documents/development``` for now)

## Run
### Broker
1. ```brew services start rabbitmq```
2. ```http://localhost:15672``` (username: guest, password: guest)

### Service Registry
1. ```cd service-registry```
2. ```./mvnw spring-boot:run```
3. Visit Eureka Dashboard: http://localhost:8761

### API Gateway
1. ```cd gateway```
2. ```./mvnw spring-boot:run```
3. Visit: http://localhost:8000
4. For Trace Debugging:  http://localhost:8000/trace

### Multiplication Service
1. ```cd multiplication```
2. ```./mvnw spring-boot:run```
3. DB Portal: ```http://localhost:8080/h2-console```

### Gamification Service
1. ```cd gamification```
2. ```./mvnw spring-boot:run```
3. DB Portal: ```http://localhost:8081/h2-console```

### Jetty (web server for UI)
1. ```cd ui```
2. ```java -jar ~/Documents/development/jetty/start.jar```
3. Visit: http://localhost:9090/ui

### Or use the Script
```./start.sh``` and ```./stop.sh``` to stop all services

## Unit Tests
1 ```cd multiplication```
2 ```./mvnw test```
3 ```./mvnw -Dtest=MultiplicationServiceTest test``` (run a specific test)

1 ```cd gamification```
2 ```./mvnw test```

### End-2-End Tests
1. Start system: ```./start-test.sh```
2 ```cd tests_e2e```
3 ```./mvnw test```
4. Stop system: ```./stop.sh```

## Deploying
1 ```cd multiplication```
2. Package into Jar: ```./mvnw package```
3. ```cd ./target```
4. Run (computer must have JRE installed): ```java -jar social-multiplication-v3-0.3.0-SNAPSHOT.jar```

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

### Pros and Cons of Event-Driven Architecture
#### Pros
* Loose Coupling

#### Cons
* Transactions. In an architecture based on events you need to assume that you don’t have ACID transactions across services anymore (or understand that, if you want to support them, you need to introduce complexity). Instead, you have eventual consistency, if you stop all interactions with the system and let all the events propagate and be consumed, you'll get to a consistent state. A solution for this is using a message broker implementation that guarantees delivery of the events at least once. Not having transactions across services is not bad per se. The big risk here is that it requires a change in the way you design and translate your functional requirements (e.g., what happens if the process is interrupted at step N?).
* Fault Tolerance. As a consequence of not having (or minimizing) transactions, fault tolerance becomes more important in these systems. One of the services might not be able to complete its part of the process, but that shouldn’t make the whole system fail. You need to prevent that from happening (e.g., by aiming for high availability with microservice redundancy and load-balancing) and also think of a way to recover from possible errors (e.g., by having a maintenance console from which you can recreate events).
* Orchestration and Monitoring. Not having a centralized orchestration layer might be problematic in systems where it’s critical to have process monitoring. In an event-driven architecture, you span processes across services that are triggering and reacting to events. You can’t follow them in a centralized way: they’re distributed across your microservices. To monitor such processes, you need to implement mechanisms to trace the flow of events and you need a common logging system where you can keep track of what’s going on between services. We can implement our own integrated mechanism to correlate events (by tagging them as they cross the services), or we can use an existing tool like Zipkin.

### Use Skinny events
To illustrate the risks of fat events, I’ll use another example. Imagine that we are receiving events when user details are updated, and we decided to model the event including the changes made to the user. Think of the case in which there are multiple subscribers, one of them is failing and the broker is dispatching those rejected messages back again. Now the order of events is not the real sequence of the changes. We can’t be sure on the consumer’s side if that change reflects the latest status. As a possible solution, we could use a timestamp on events, but then extra logic is needed on the consumer’s end to handle time: discarding older changes, etc.

Including data in events modeling changes in a mutable object is risky. In this case, it might be better to notify that the user with the given identifier has been updated, and leave the consumers to ask for the latest state whenever they decide to process their logic.

Another potential drawback of including too much data in
events can be shown in the following example: if in future we
include an extra microservice (e.g., a stats analyzer) and it needs to
use the timestamp of attempts, we could just add the timestamp to MultiplicationSolvedEvent. But, in that case, we would be tailoring the events from the publisher’s side to the needs of all our consumers. We would have not only a fat event but also a smart publisher who knows too much about the business logic of their consumers: an anti-pattern
of event-driven architecture. In general, it’s more advisable to let the consumers ask for the data they need and avoid including it as part of the event’s content.

But make sure your events are NOT too skinny. If your event does not include all the data that consumers need, your service could be bombarded by extra REST requests from consumers.

### Spring Autowire
1. Finds bean by type
```
@Autowired
public SomeClass(final RestTemplate restTemplate) {
  ...
}

@Bean
public RestTemplate bean1(RestTemplateBuilder builder) {
  ...
}
```

In the above example, even though the qualifier names are different (restTemplate and bean1), Spring will still be able to resolve the bean because there is only one bean of type RestTemplate.

2. Finds bean by qualifier

However, if there are two beans of RestTemplate configured:
```
@Bean
public RestTemplate bean1(RestTemplateBuilder builder) {
  ...
}

@Bean
public RestTemplate bean2(RestTemplateBuilder builder) {
  ...
}
```
The autowiring above will fail because there is disambiguation since there are two beans it resolves to. The above wiring will need to be altered to use the qualifier name to choose which bean.
```
@Autowired
public SomeClass(final RestTemplate bean2) {
  ...
}
```
### Edge Services
Many people often get scared when conversations arise about having centralized parts in a microservices architecture. parts that are critical, like routing and filtering, may become a single point of failure. this api gateway service in particular, as the gate to our microservices, is also known as an *edge service*.

The key to making this kind of services work in a microservices infrastructure is to apply proper load balancing to them. to do that, we usually need to go a level deep, and use solutions such as an infrastructure Dns load balancer, in which for example our gateway located at http://gateway.ourwebapp.com is backed by three different server instances. Most cloud providers offer these services out of the box, and we can also implement it by ourselves with tools like nginx.

### Scaling Microservices
One of the most critical things when we start working with scalable systems is that we need to be aware of some important basic concepts when designing our microservices.

#### Databases and Stateless Services
Our services need to be stateless, meaning that they shouldn’t keep any data or state in memory. Otherwise, we need to have *session affinity*.

In our system, the databases are embedded in the services, thus preventing us from scaling up correctly. Every instance of our service shouldn’t have its own database since that would cause retrieving different data per request. All instances need to keep their data in the same place, in the same, shared database server.

#### Event-Driven Architecture and Load Balancing
You need to make sure only one instance of a microservice processes an event triggered. Every service instance should act as a worker that connects to a shared queue in RabbitMQ. Only one instance consumes each event, processes it, and stores the result in the shared database.

## Trouble Shooting
### No separate test db
If tests fail with this message:
```
HHH000342: Could not obtain connection to query metadata : Database may be already in use: null. Possible solutions: close all other connection(s); use the server mode [90020-199]
```
It means you are probably running the server in the background. It seems only one connection to the db is allowed.

Solution is not to have server running when running tests. Or set up a separate test db.

## Jetty
To create a new jetty base:
1. Create a new folder for the jetty base
2. cd to new folder and execute this command: ```java -jar ~/Documents/development/jetty/start.jar --add-to-startd=http,deploy```
