FROM maven
WORKDIR /vpn
COPY . .
RUN mvn clean install -DskipTests
CMD mvn spring-boot:run
