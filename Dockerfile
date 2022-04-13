ARG VERSION=11.0.14.1

FROM openjdk:${VERSION}-jdk as BUILD

COPY . /pokebaseapi
WORKDIR /pokebaseapi
RUN ./gradlew --no-daemon shadowJar

FROM openjdk:${VERSION}-jre

COPY --from=BUILD /pokebaseapi/build/libs/*.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java","-jar","run.jar"]