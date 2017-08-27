# SOA Book API
Project done as a proof of concept API with microservice architecture

Course: Service Oriented Architecture @ [FCSE](https://www.finki.ukim.mk)

## Short description

The bussiness logic of the API will consist of mostly search operations
on various categories of books like name, authors, year, publication date etc.

## Application architecture

The functionalities of searching through various criterias are separated
in different microservices (see microservice list below.) to balance the strain in case of heavy load.
While searching through the book data the requests will mostly depend on the
User Preferences microservice which will hold the the data about what the user likes
(favorite genres, authors etc.).
There will be a second operation which will be supported by the Random Search microservice,
which is going to randomise searches for the user based on the book search criteria.
The User microservice will be responsible for the user management of the application.
The authentication will be available through the Zuul (Edge) microservice.
For load balancing we will use traefik, Eureka as a service registry and custom API Gateway
as a central unification point of the microservices.
The initial database records will be filled with data scraped from the web.

## Full microservice list
* Author microservice
* Genre microservice
* Publication Year microservice
* Rating microservice
* Book microservice
* Random search microservice
* User microservice
* User Preferences microservice
* Edge service (Zuul from Netflix OSS)
* Eureka service registry (from Netflix OSS)
* Custom API Gateway

## Picture of application architecture
 ** TBA
 
## Team Members
* [Alisa Krstova 141501](https://github.com/alisakrstova) 
* [Gjorgji Kirkov 141021](https://github.com/kirkovg)
* [Alek Petreski 141507](https://github.com/alekkki)
