# # image
# FROM openjdk:17
# # izvrsavanje naredbe u konzoli koja se izvrsava unutar image-a
# RUN mkdir swe
# # koji resurs se kopira u image, kopira sa hosta unutar image-a
# ADD target/chocolates_back-0.0.1-SNAPSHOT.jar swe/chocolates_back-0.0.1-SNAPSHOT.jar
# # port na kome se izvrsava aplikacija
# EXPOSE 8080

# ENTRYPOINT ["java","-jar","swe/chocolates_back-0.0.1-SNAPSHOT.jar"]

FROM openjdk:17
COPY /target/chocolates_back-0.0.1-SNAPSHOT.jar chocolates_back-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","chocolates_back-0.0.1-SNAPSHOT.jar"]

