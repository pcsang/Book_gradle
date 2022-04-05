FROM openjdk:8-jdk-alpine
COPY build/libs/demoBook-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9090
# ENV URL=jdbc:postgresql://postgres_db_book:5432/book_db
ENTRYPOINT ["java", "-jar", "/app.jar"]