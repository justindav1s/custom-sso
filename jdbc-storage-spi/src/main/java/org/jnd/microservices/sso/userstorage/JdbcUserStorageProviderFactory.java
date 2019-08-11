/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jnd.microservices.sso.userstorage;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.component.ComponentValidationException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.List;


public class JdbcUserStorageProviderFactory implements UserStorageProviderFactory<JdbcUserStorageProvider> {

    private static final Logger logger = Logger.getLogger(JdbcUserStorageProviderFactory.class);

    public static final String PROVIDER_NAME = "jdbc-user-storage-spi";

    protected static final List<ProviderConfigProperty> configMetadata;

    static {
        configMetadata = ProviderConfigurationBuilder.create()
                .property().name("datasource_name")
                .type(ProviderConfigProperty.STRING_TYPE)
                .label("Datsource JNDI name")
                .defaultValue("java:jboss/datasources/UserDS")
                .helpText("Datsource used to obtain user data")
                .add().build();
    }


    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configMetadata;
    }

    @Override
    public void validateConfiguration(KeycloakSession session, RealmModel realm, ComponentModel config) throws ComponentValidationException {
        String fp = config.getConfig().getFirst("datasource_name");
        if (fp == null) throw new ComponentValidationException("A Datsource JNDI name does not exist");
    }

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }

    @Override
    public JdbcUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        String datasource_name = model.getConfig().getFirst("datasource_name");

        InitialContext ctx = null;
        DataSource datasource = null;
        try {
            ctx = new InitialContext();
            datasource = (DataSource)ctx.lookup(datasource_name);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return new JdbcUserStorageProvider(session, model, datasource);
    }

}
