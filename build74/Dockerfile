FROM image-registry.openshift-image-registry.svc:5000/openshift/sso74-openshift-rhel8

USER root
ENV EAP_HOME=/opt/eap
RUN mkdir -p $EAP_HOME/bin/vault
RUN $EAP_HOME/bin/vault.sh --keystore $EAP_HOME/vault/vault.keystore --keystore-password vault22 --alias vault --vault-block vb --attribute password --sec-attr 0penS3sam3 --enc-dir $EAP_HOME/vault/ --iteration 120 --salt 1234abcd
RUN ls -ltr $EAP_HOME/bin/vault
COPY dependencies/sso-extensions.cli /opt/eap/extensions/
COPY dependencies/ojdbc8.jar /opt/eap/extensions/ojdbc8.jar

USER 1001