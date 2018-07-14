FROM openjdk:8
ADD target/playlist-jwt-security-0.0.1-SNAPSHOT.jar playlist
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "playlist"]