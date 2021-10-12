FROM registry.redhat.io/rh-sso-7/sso74-openshift-rhel8:7.4-37

USER root

ENV EAP_HOME=/opt/eap
RUN sed -i "s/<resolve-parameter-values>false<\/resolve-parameter-values>/ \
<resolve-parameter-values>true<\/resolve-parameter-values>/"  \
/opt/eap//bin/jboss-cli.xml

COPY dependencies/sso-extensions.cli /opt/eap/extensions/
COPY dependencies/ojdbc8.jar /opt/eap/extensions/ojdbc8.jar

USER 1001