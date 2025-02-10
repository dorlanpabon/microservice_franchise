FROM amazoncorretto:17.0.14-al2023-headless

WORKDIR /app

COPY build/libs/franchise-0.0.1-SNAPSHOT.jar app.jar

COPY src/main/resources/sql/data.sql /app/resources/sql/data.sql

EXPOSE 8080

ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysqlurl:3306/franchise_db
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=""

ENTRYPOINT ["java", "-jar", "app.jar"]
