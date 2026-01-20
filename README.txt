I have created a sample Spring boot CRUD application.

to build .jar file do - mvn clean install 

now to test from locally run below command to run docker file

docker build -t docker-demo-app .
docker run -p 8080:8080 docker-demo-app


to test docker-hub repo images run attached docker-compoase file

docker-compose up --build

