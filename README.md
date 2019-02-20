. The application is a spring boot rest api (having a user interface as well) which provides end points 
for making CRUD operations on an in memory database for an entity named CakeEntity.

. To compile, package and run the application, go to the root folder and run the following commands;

If you have maven installed you may compile with;

    mvn clean compile test
 
You may also compile with mvnw;

    ./mvnw clean compile test

. To pacakage the project;
    
    mvn package

You may also package with mvnw;

    ./mvnw package
    
. To run the project;   

   Go to target folder;
   
    cd target
    
   confirm the .jar is there with;
   
    -ls *.jar
    
   Run the jar file (Tomcat runs on port 8080, no application should be running on this port);
   
     java -jar springboot-crud-cake-master-0.0.1-SNAPSHOT.jar
     
   You may then go to localhost:8080 and see the list of entities after authenticating with the user
   scott and password tiger. At this point you may do all the CRUD operations with UI.
   
  . Application provides a Rest Api as well.
    
  . Doing CRUD operations with the api;
  
  If you have Postman installed you may try all the CRUD operations that the api provides.
  
  GET http://localhost:8080/cakes (Selects all cakes)
  
  GET http://localhost:8080/cakes/1 (Select the cake  with having the id 1)
  
  POST http://localhost:8080/cakes (Insert a new cake)
  
  Body;
  
    { 
        "title": "Brand new cake",
        "description": "A ground-breaking new cake",
        "image": "https://www.gstatic.com/webp/gallery/1.webp"
    }
    
  
  PUT http://localhost:8080/cakes (Update the cake with having the id 1)
    
  Body;
  
    { 
        "title": "A Brand new strawberry cake",
        "description": "strawberry cakes forever",
        "image": "https://www.gstatic.com/webp/gallery/1.webp"
    }
  
  DELETE http://localhost:8080/1 (Delete the cake with the id 1), you will get the response below;
  
     Cake 1 deleted !


. You may also run all the tests standalone at once by running TestRunner.java from your editor.

======================================================================================

. Application may run on Docker as well. To build a docker image go to the root folder and run the 
command below;

docker build -f Dockerfile -t cake-master-spring-boot .


. After seeing 'Successfully built' message, check if the image 'cake-master-spring-boot' is created by 
this command;

docker images

. Run your container on 8282 by the following command;

docker run -p 8282:8080 cake-master-spring-boot

. Go to the browser, http://localhost:8282 should take you to the application. (scott, tiger are the 
user and pasword)

======================================================================================

Note : I changed the CakesEntity column and table mappings as they seem to be copied from
an existing table and were keeping field names related to that table.

