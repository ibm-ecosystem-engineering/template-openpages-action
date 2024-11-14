FROM openjdk:8 as builder

WORKDIR /source

COPY . .
RUN ./gradlew jar

FROM docker.io/alpine:3.20.3

WORKDIR /op-ext-lib

COPY ./op-ext-lib/* .
COPY --from=builder /source/build/libs/*.jar .

WORKDIR /scripts

COPY scripts/copyJars.sh .

ENTRYPOINT [ "./copyJars.sh" ]
