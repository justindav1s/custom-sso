#!/usr/bin/env bash

openssl x509 -inform PEM -in /Users/justin/acme/certs/openshiftlabs.net/fullchain.cer -out ca.crt
openssl x509 -inform PEM -in /Users/justin/acme/certs/openshiftlabs.net/openshiftlabs.net.cer -out openshiftlabs.net.crt
