FROM maven:3.9.6-eclipse-temurin-17

RUN mkdir -p /app/

WORKDIR /app

COPY . .

RUN mvn -v && java --version

RUN mvn clean package

EXPOSE 9999

CMD ["java", "-jar", "target/RecommendationEngineServer-jar-with-dependencies.jar"]

#---- COMMAND TO RUN SERVER ----#
#docker build --no-cache -t boundaries .
#docker run -p 9899:9899 --restart=always --name=Boundaries_Server -itd boundaries