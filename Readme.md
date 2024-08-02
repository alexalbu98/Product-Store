# Product Store Project

This is a spring boot project that exposes RESTFUL apis for
handling a product store.

## Prerequisite

In order to run the project a java 17 JRE is required alongside a Docker engine.  
The project uses Docker to create a Postgres database.  
Maven is required for building the application.  
The app uses spring docker compose support, so the container will automatically start when the app starts.

## Description

The application exposes apis for handling of multiple product stores owned by different users.  
An user can create an account as a store owner, or as a client.  
Once an account is created, a store owner can create stores and insert products into each store.  
The app expose endpoints for store management such as add product, update product details and increment stock.  
Every user can see the stores and the products inside, but only store owners can update their stores.

## Technical overview

The app is built using spring boot web mvc alongside spring security and spring data jdbc.  
Spring data jdbc was chosen as it is simpler (reduced overhead) and makes use of domain driven design concepts.  
For testing, testcontainers were used for starting and removing a Postgres container during tests.  
Besides unit testing and integration testing, the apis were tested and validated using Spring mockmvc.  
For documenting the apis spring doc openapi library was used, which expose a web interface
for seeing a detailed description of all the apis at **/swagger-ui/index.html**.

