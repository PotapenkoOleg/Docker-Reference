FROM amazoncorretto:11
EXPOSE 8090
RUN mkdir /opt/KotlinSpringBootDemo
WORKDIR /opt/KotlinSpringBootDemo
COPY ./build/libs/KotlinSpringBootDemo-0.0.1-SNAPSHOT.jar ./
CMD [ "java", "-jar", "KotlinSpringBootDemo-0.0.1-SNAPSHOT.jar" ]