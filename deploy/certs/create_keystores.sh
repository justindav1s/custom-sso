#!/usr/bin/env bash

openssl pkcs12 \
    -export \
    -in datr.eu.crt \
    -inkey datr.eu.key \
    -out datr.eu.p12 \
    -name datr.eu \
    -CAfile ca.crt

keytool -importkeystore \
    -destkeystore datr.eu.jks \
    -srckeystore datr.eu.p12 \
    -alias datr.eu

keytool -genseckey \
    -alias jgroups \
    -storetype JCEKS \
    -keystore jgroups.jceks
