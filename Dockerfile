# Use a specific version of the OpenJDK image
FROM openjdk:8-jdk-alpine

# Set the working directory to /app and switch to non-root user
WORKDIR /app
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
RUN chown -R appuser:appgroup /app
USER appuser

# Copy the application jar to the working directory
COPY target/shoe-price-tracker.jar .

# Expose port 8087 for the application
EXPOSE 8087

# Set the startup command to run the application
CMD ["java", "-jar", "shoe-price-tracker.jar"]


#EXPOSE 8081

#VOLUME /tmp

#ENV POSTGRES_PASSWORD=Tm5d8q7j!
#ENV POSTGRES_USER=postgres
#ENV POSTGRES_DB=shoepricetracker

#FROM openjdk:8-jdk-alpine
#ADD target/shoe-price-tracker-0.0.1-SNAPSHOT.jar shoepricetracker.jar
#ENTRYPOINT ["java", "-jar", "/shoepricetracker.jar"]