FROM openjdk:8
ADD /target/springboot-crud-cake-master-0.0.1-SNAPSHOT.jar springboot-crud-cake-master.jar
EXPOSE 8282
ENTRYPOINT ["java","-jar","springboot-crud-cake-master.jar"]
