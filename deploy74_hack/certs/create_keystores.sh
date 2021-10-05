#!/usr/bin/env bash

openssl pkcs12 \
    -export \
    -in openshiftlabs.net.crt \
    -inkey /Users/justin/acme/certs/openshiftlabs.net/openshiftlabs.net.key \
    -out openshiftlabs.net.p12 \
    -name openshiftlabs.net \
    -CAfile ca.crt

keytool -importkeystore \
    -destkeystore openshiftlabs.net.jks \
    -srckeystore openshiftlabs.net.p12 \
    -alias openshiftlabs.net

keytool -genseckey \
    -alias jgroups \
    -storetype JCEKS \
    -keystore jgroups.jceks
