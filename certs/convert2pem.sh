#!/usr/bin/env bash

openssl x509 -inform PEM -in ca.cer  -out ca.crt
openssl x509 -inform PEM -in datr.eu.cer -out datr.eu.crt
