FROM amazoncorretto:11
RUN mkdir /opt/DockerJavaTest
WORKDIR /opt/DockerJavaTest
COPY ./build/libs/DockerJavaTest-1.0-SNAPSHOT.jar ./
CMD [ "java", "-jar", "DockerJavaTest-1.0-SNAPSHOT.jar" ]