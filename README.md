# Building Customised RHSSO/Keycloak images to run on Openshift

The RHSSO/Keycloak provided by default on Openshift is configured to use H2, Postgresql or MySQL as persistent backends for realm and user data. It also comes bundled with some default UI themes.

It's very often the case that these default components do not meet user requirements.

Users need to customise the base image because :

   * A different DB flavour is required. Oracle, MSSQL
   * Cusomised UI components are required.
   * Keycloak is extendable by implementing various SPI interfaces. These customised components need adding to the base image.

This repository contains example artefacts designed to customise the base Image.

## Folders
   * _build_ : contains a Dockerfile, all the resources referenced by the docker file, a Openshift BuildConfig and script to trigger a Dockerbuild on OPenshift, that will deliver a customised image.
   * _deploy_ : Scripts and config to deploy the custom image, and scripts to generate the required certificates.
   *

