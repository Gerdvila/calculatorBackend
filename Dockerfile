FROM amazoncorretto:21

# Set the working directory
WORKDIR /app

# Copy the Gradle files
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# Download and install Gradle
RUN chmod +x ./gradlew
RUN ./gradlew --version

# Copy the project files
COPY . /app

# Build the project
RUN chmod +x ./gradlew
RUN ./gradlew build

# Set the startup command
CMD ["java", "-jar", "build/libs/calculator.jar"]