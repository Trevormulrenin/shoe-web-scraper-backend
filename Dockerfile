FROM openjdk:8-jdk
ADD target/shoe-price-tracker.jar shoe-price-tracker.jar
EXPOSE 8087
ENTRYPOINT ["java","-jar","/shoe-price-tracker.jar"]

#EXPOSE 8081

#VOLUME /tmp

#ENV POSTGRES_PASSWORD=Tm5d8q7j!
#ENV POSTGRES_USER=postgres
#ENV POSTGRES_DB=shoepricetracker

#FROM openjdk:8-jdk-alpine
#ADD target/shoe-price-tracker-0.0.1-SNAPSHOT.jar shoepricetracker.jar
#ENTRYPOINT ["java", "-jar", "/shoepricetracker.jar"]