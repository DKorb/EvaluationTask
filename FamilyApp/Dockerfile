FROM maven:3.8.5-openjdk-17-slim
WORKDIR /FamilyApp
COPY . .
RUN mvn clean package -DskipTests
CMD mvn spring-boot:run