FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml ./
COPY .mvn .mvn
RUN mvn dependency:go-offline -q
COPY src src
RUN mvn package -DskipTests -q

FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S app && adduser -S app -G app
WORKDIR /app
COPY --from=build /app/target/coffeeconnect-1.0.0.jar app.jar
RUN mkdir -p uploads && chown -R app:app /app
USER app
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=render
ENV JAVA_OPTS="-Xmx256m -XX:+UseG1GC"
CMD java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar
