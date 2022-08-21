# <h1 align="center">EvaluationTask</h1>

![Platform](https://img.shields.io/badge/Java-17%2B-red)
![Framework](https://img.shields.io/badge/Spring%20Boot-2.7.2-green)
![Platform](https://img.shields.io/badge/Docker-blue)
![Platform](https://img.shields.io/badge/Minikube-yellowgreen)

## Repository Contains

* Spring boot application + mysql database.
* Dockerfile each for module + docker-compose.
* K8s config files.
* Sample postman collection.

## How to run application

To install application, run the following commands:

<b>Download the repository and uzip it or clone it.</b>

```bash
git clone https://github.com/dkorb/EvaluationTask
```

## First method (running in minikube)

If you want to run application in minikube, go to folder with k8s configuration then execute commands:

```bash
# launch minikube environment
minikube start
# create/update clusters in minikube from config
kubectl apply -f .
# create a route to services deployed
minikube tunnel
```

Now you can test application using endpoints in postman everything should work fine.

![image](https://user-images.githubusercontent.com/45924037/185578366-4f38153c-92b2-4f4e-bf21-47a3ec254ea0.png)

## Second method (using uploaded docker images)

If you want to run application as docker container, go to path where <b>docker-compose.yaml</b> file is located then execute the command:

```bash
docker compose up
```
<b>You dont have to build the project, because docker images have been uploaded to my dockerhub repository.</b>

```shell
# FamilyApp docker image
https://hub.docker.com/r/dkorb/familyapp
# FamilyMemberApp docker image
https://hub.docker.com/r/dkorb/familymemberapp
```

## Third method (using your own build docker images)

If you want to build project without using the images I uploaded to dockerhub change <b>docker-compose.yaml</b> file to this:

```
version: "3.8"
services:
  database:
    container_name: Familydatabase
    image: mysql:8.0
    environment:
      MYSQL_USER: ${MYSQLDB_DOCKER_USER}
      MYSQL_PASSWORD: ${MYSQLDB_DOCKER_PASSWORD}
      MYSQL_ROOT_PASSWORD: ${MYSQLDB_DOCKER_PASSWORD}
    volumes:
      - "db_data:/var/lib/mysql"
      - "./mysql-init:/docker-entrypoint-initdb.d"
    ports:
      - "${MYSQLDB_LOCAL_PORT}:${MYSQLDB_DOCKER_PORT}"
  familymemberapp:
    build: ./FamilyMemberApp
    container_name: FamilyMemberApp
    # image: dkorb/familymemberapp:latest
    env_file: .env
    ports:
      - "${SPRING_FAMILY_MEMBER_APP_PORT}:${SPRING_FAMILY_MEMBER_APP_PORT}"
    depends_on:
      - database
  familyapp:
    build: ./FamilyApp
    container_name: FamilyApp
    # image: dkorb/familyapp:latest
    env_file: .env
    ports:
      - "${SPRING_FAMILY_APP_PORT}:${SPRING_FAMILY_APP_PORT}"
    depends_on:
      - familymemberapp
volumes:
  db_data:
  ```
now execute the command in path where <b>docker-compose.yaml</b> file is located.

```bash
docker compose up --build
```

## Fourth method (running application locally)

If you want to run application locally, change <b>spring.profile.active</b> in <b>application.properties</b> to <b>dev</b> in both modules. 

```
spring.profiles.active = dev
```

## Postman

<b>postman</b> directory contain collection with sample endpoints for both modules.

![postman_endpoints](https://user-images.githubusercontent.com/45924037/185230171-a441544a-3b2d-4361-95ba-7eefea342607.png)

## Swagger

```
# in your browser for FamilyApp.
http://localhost:8020/swagger-ui/# 
# in your browser for FamilyMemberApp.
http://localhost:8010/swagger-ui/#
```
