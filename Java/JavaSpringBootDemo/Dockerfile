FROM amazoncorretto:11
EXPOSE 8090
RUN mkdir /opt/JavaSpringBootDemo
WORKDIR /opt/JavaSpringBootDemo
COPY ./build/libs/JavaSpringBootDemo-0.0.1-SNAPSHOT.jar ./
CMD [ "java", "-jar", "JavaSpringBootDemo-0.0.1-SNAPSHOT.jar" ]