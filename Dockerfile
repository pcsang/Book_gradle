#
# Package stage
#
COPY build/libs/demoBook-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.ja
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]
