FROM openjdk:8-jre

ENV WIREMOCK_HOME /opt/bsc-wiremock
ENV MOUNTPOINT "$WIREMOCK_HOME/mount"
ENV LOGS "$MOUNTPOINT/logs"
ENV CONF "$MOUNTPOINT/conf"

RUN mkdir -p "$WIREMOCK_HOME/lib"
RUN mkdir -p "$CONF"
RUN mkdir -p "$LOGS"

WORKDIR $WIREMOCK_HOME

COPY target/*.jar "$WIREMOCK_HOME/app.jar"
COPY "jms.jar" "$WIREMOCK_HOME/lib"
COPY "com.ibm.mq.allclient.jar" "$WIREMOCK_HOME/lib"

COPY "properties.yml" "$CONF/"
COPY "run.sh" "$WIREMOCK_HOME/"
COPY "application.properties" "$CONF/"

VOLUME ["$MOUNTPOINT","$WIREMOCK_HOME/mappings"]

EXPOSE 1398

#RUN chmod +x "$WIREMOCK_HOME/run.sh"

CMD ["/bin/bash","run.sh", "run"]
