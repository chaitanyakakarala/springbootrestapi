FROM anapsix/docker-oracle-java8
MAINTAINER krishna <chaitanya.kakarala@gmail.com>
RUN mkdir apps
ADD EurekaServer-0.0.1-SNAPSHOT.jar apps/
WORKDIR apps
ENTRYPOINT ["java","-jar","EurekaServer-0.0.1-SNAPSHOT.jar"]