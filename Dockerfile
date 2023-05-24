FROM openjdk:8-jdk-alpine
COPY build/libs/demoBook-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 9090
# ENV URL=jdbc:postgresql://postgres_db_book:5432/book_db
#Use for docker compose
ENV URLHOST=postgres_db_book
ENV PASSWORD=123456
ENV USERNAME=postgres
ENTRYPOINT ["java", "-jar", "/app.jar"]