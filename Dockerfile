FROM openjdk:jre-alpine
WORKDIR /workspace
COPY /target/order-service-1.0.0.jar /workspace/order-service.jar
COPY /starter.sh /workspace/starter.sh
EXPOSE 9000
RUN apk add curl
RUN apk add --update --no-cache netcat-openbsd
RUN ["chmod", "+x", "/workspace/starter.sh"]
ENTRYPOINT ["sh","/workspace/starter.sh"]