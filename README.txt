This is a simple REST-API microservice responsible for managing resources related to Users. The service was developed in JAVA using Spring Boot framework, using mongoDB as datasource and has a Basic Auth authentication (using Spring Security) to protect the resources. 

The users are divided in two categories: ADMIN and USER. Users of the type USER can only use get and getAll methods while ADMIN can also create, update or delete an existing user. The exacts ways to interact with the resource "/users" can be accessed through this app documentation, which is in the URL "http://[base url]/swagger-ui.html".

The User Microservice can be added to a Kubernetes cluster through its .yaml files inside the "kubernetes" folder. There are two files: "mongo.yalm" and "user.yaml". "mongo.yalm" is responsible to maintening the MongoDB Instance (therefore the database data will be cleaned if the pod goes down) while "user.yaml" is responsible for managing the JAVA application. Both .yaml lookup for tomasoares/user-microservice and tomasoares/mongo in the Docker.hub to load up the images in the Kubernetes Pods. 

There's also a docker-compose file to boot up the mongodb, mongodb-test and mongo-express instances through Docker locally. Mongodb is the main database which the application will interact, and mongodb-test is responsible to dealing with integration tests with the database. Mongo-express offer a UI interface to interact with the main database, through the port 8081.

The application also builds an docker image when building Maven. It's necessary to have both databases up due to the Unit tests. Unfortunately I didn't have enough time to Mock the authentication for the Resource Tests, so it'll need to access the main database to do it. After the build is done, the .jar is generated and the Mavin Spotify Plugin generates a docker image, which can be pushed to docker.hub and then avaliable for Kubernetes.

To access the API, two Users have been created among with the MongoDB database: "creativedriver@gmail.com" with ADMIN role and "regularuser@gmail.com" with USER role. Both users passwords are cr34t1v3dr1v3r.

--------------------------
REMAINING IMPLEMENTATIONS:
--------------------------

- Mocking authentication tests so the main database isn't required to test the resource API.
- Add Unit Test for the services.
- Use a different and more robust Auth mechanism.
- Use volumes for database container so the data isn't lost when the container is stopped.