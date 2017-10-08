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
* Rating microservice
* Book microservice
* Random search microservice
* User microservice
* User Preferences microservice
* Edge service (Zuul from Netflix OSS)
* Eureka service registry (from Netflix OSS)
* Custom API Gateway

## Picture of application architecture
 ![alt text][logo]
 
 [logo]: https://github.com/alisakrstova/soa-book-api/blob/master/app_architecture.png "app architecture"


## Available endpoints
Following are all the endpoints available with their corresponding HTTP methods grouped by logical parts of the system.
### Books
* GET: __/api/books__ - retrieves paged result of all books
	* Query string:
		* size (int) - page size
		* page (int) - page number
* GET: __/api/books/{bookId}__ - retrieves book details
* GET: __/api/books/search__ - searches books by name and description
	* Query string:
		* query (string) - the name and description of the books to be returned
* POST: __/api/books__ - creates new book
	* Data parameters (for JSON object in request body):
		* name
		* yearPublished
		* description
		* numPages
		* genreId
		* authorId
* PUT: __/api/books/{bookId}__ - updates details for book with given ID
* DELETE: __/api/books/{bookId}__ - deletes book with given ID
* GET: __/api/random__ - retrieves randomized list of books (5 books per page)

### Authors
* GET: __/api/authors__ - retrieves all authors
* GET: __/api/authors/{authorId}__ - retrieves author by given ID
* POST: __/api/authors__ - creates new author
	* Data parameters:
		* name
		* surname
		* born
		* website
* PUT: __/api/authors/{authorId}__ - updates details for author with given ID
* DELETE: __/api/authors/{authorId}__ - deletes author with given ID

### Genres
* GET: __/api/genres__ - retrieves all genres
* GET: __/api/genres/{genreId}__ - retrieves genre by given ID
* POST: __/api/genres__ - creates new genre
	* Data parameter:
		* name
* PUT: __/api/genres/{genreId}__ - updates details for genre with given ID
* DELETE: __/api/genres/{genreId}__ - deletes genre with given ID

### Ratings
* GET: __/api/ratings__ - lists all books with a value for the rating equal to the one supplied in the request parameters
	* Query string:
		* pageSize (int) - number of results per page
		* pageNumber (int) - specific page you want to retrieve
		* grade (float) - rating/grade to filter by
* GET: __/api/ratings/{bookId}__ - retrieves average grade for book with given ID
* POST: __/api/ratings/{bookId}__ - rates book (updates average grade)
* DELETE: __/api/ratings/{bookId}__ - deletes rating for book

### Users
* GET: __/api/users__ - retrieves a list of all registered users
* POST: __/api/users/register__ - creates a new user
	* Data parameters:
		* userName
		* password
		* email

 
## Team Members
* [Alisa Krstova 141501](https://github.com/alisakrstova) 
* [Gjorgji Kirkov 141021](https://github.com/kirkovg)
* [Alek Petreski 141507](https://github.com/alekkki)
