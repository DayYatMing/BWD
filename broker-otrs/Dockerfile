FROM alpine/java:17.0.12

WORKDIR /app

COPY target/*.jar app.jar

COPY target/classes/cert.crt /usr/local/share/ca-certificates/cert.crt

RUN keytool -import -trustcacerts -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -noprompt -alias bw -file /usr/local/share/ca-certificates/cert.crt

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "app.jar"]
