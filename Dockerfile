FROM openjdk:11.0.7-slim
LABEL maintainer="juangallino.jmg@gmail.com"
ARG JAR_FILE
ADD target/${JAR_FILE} ms-stock.jar
RUN echo ${JAR_FILE}
ENTRYPOINT ["java","-jar","/ms-stock.jar"]