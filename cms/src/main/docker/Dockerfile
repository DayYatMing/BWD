FROM alpine/java:17.0.12

COPY build/resources/main/config/cert.crt /usr/local/share/ca-certificates/cert.crt

ARG PROFILE=prod
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS="" \
    SPRING_PROFILES_ACTIVE=${PROFILE}

RUN keytool -import -trustcacerts -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -noprompt -alias bw -file /usr/local/share/ca-certificates/cert.crt

CMD echo "The application will start in ${JHIPSTER_SLEEP}s..." && \
    sleep ${JHIPSTER_SLEEP} && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.war

EXPOSE 9006 5702/udp

ADD build/libs/*.war /app.war
