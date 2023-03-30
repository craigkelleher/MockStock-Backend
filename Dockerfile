FROM openjdk:11

WORKDIR /

ADD /build/libs/mockstock-0.0.1-SNAPSHOT.jar mockstock.jar

EXPOSE 8080

CMD java -jar mockstock.jar