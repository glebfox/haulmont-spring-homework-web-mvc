FROM gradle:7-hotspot AS build
WORKDIR /app
COPY . .
RUN gradle build

FROM tomcat:9-jdk8
COPY --from=build /app/build/libs/haulmont-spring-homework-web-mvc-1.0.war $CATALINA_HOME/webapps/ROOT.war
EXPOSE 8080
