# Use Java 23 runtime
FROM eclipse-temurin:23-jdk

# Set working directory inside container
WORKDIR /app

# Copy your Maven project files
COPY pom.xml .
COPY src ./src

# Build the project with production profile
RUN apt-get update && apt-get install -y maven \
    && mvn -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install -Pproduction \
    && mv target/spring-boot-jwt-app-1.0.0.jar app.jar \
    && apt-get remove -y maven \
    && apt-get autoremove -y \
    && rm -rf /var/lib/apt/lists/* \
    && rm -rf target src pom.xml ~/.m2

# Create uploads directory
RUN mkdir -p /app/uploads

# Expose port 8080
EXPOSE 8080

# Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]

