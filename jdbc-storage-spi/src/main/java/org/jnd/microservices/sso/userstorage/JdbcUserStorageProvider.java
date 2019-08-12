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

import lombok.extern.jbosslog.JBossLog;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.*;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;

import javax.sql.DataSource;
import java.util.*;

@JBossLog
public class JdbcUserStorageProvider implements
        UserStorageProvider,
        UserLookupProvider,
        CredentialInputValidator,
        CredentialInputUpdater,
        UserRegistrationProvider,
        UserQueryProvider{

    public static final String UNSET_PASSWORD="#$!-UNSET-PASSWORD";
    protected KeycloakSession session;
    protected DataSource dataSource;
    protected ComponentModel model;
    private DatabaseProxy databaseProxy;

    public JdbcUserStorageProvider(KeycloakSession session, ComponentModel model, DataSource dataSource) {
        log.info(this + " : JdbcUserStorageProvider Constructor.");
        this.session = session;
        this.model = model;
        this.dataSource = dataSource;
        databaseProxy = new DatabaseProxy(dataSource);
    }


    // UserLookupProvider methods
    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
        log.info(this + " : UserLookupProvider : getUserByUsername : " + username);
        User user = new User();
        user.setUsername(username);
        user = databaseProxy.getUserByUsername(user);

        return new FederatedUserAdapter(this.session, realm, this.model, user);
    }

    @Override
    public UserModel getUserById(String id, RealmModel realm) {
        log.info("UserLookupProvider : getUserById id : "+id);
        StorageId storageId = new StorageId(id);
        String providerid = storageId.getProviderId();
        String externalid = storageId.getExternalId();

        log.info("UserLookupProvider : getUserById from external id : "+externalid);
        User extuser = new User();
        extuser.setId(externalid);
        extuser = databaseProxy.getUserById(extuser);
        log.info("UserLookupProvider : getUserById from external user : "+extuser);

        return new FederatedUserAdapter(this.session, realm, this.model, extuser);
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm)
    {
        log.info("UserLookupProvider : getUserByEmail");
        return null;
    }

    // UserQueryProvider methods
    @Override
    public int getUsersCount(RealmModel realm) {

        log.info("UserQueryProvider : getUsersCount");
        int count = databaseProxy.getUsersCount();
        System.out.println("UserQueryProvider : getUsersCount : " + count);
        return count;
    }



    @Override
    public List<UserModel> getUsers(RealmModel realm) {
        log.info(this + " : UserQueryProvider : getUsers(RealmModel realm)");
        return getUsers(realm, 0, Integer.MAX_VALUE);
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults) {
        log.info(this + " : UserQueryProvider : getUsers(RealmModel realm, int firstResult : "+firstResult+" int maxResults : "+maxResults+")");
        User[] users = databaseProxy.getAllUsers();
        List<UserModel> usermodels = new ArrayList<UserModel>();

        if (users.length < maxResults)
            maxResults =users.length;

        users = Arrays.copyOfRange(users, firstResult, (firstResult+maxResults));
        for (User user : users) {
            UserModel usermodel = new FederatedUserAdapter(this.session, realm, this.model, user);
            usermodels.add(usermodel);
        }

        return usermodels;
    }

    // UserQueryProvider method implementations

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm) {
        log.info("UserQueryProvider : searchForUser(String search, RealmModel realm)");
        return searchForUser(search, realm, 0, Integer.MAX_VALUE);
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {
        log.info("UserQueryProvider : searchForUser(String search, RealmModel realm, int firstResult, int maxResults)");
        List<UserModel> usermodels = new LinkedList<>();
        List<UserModel> users = session.users().getUsers(realm, false);
        for (UserModel user : users) {
            if (user.getUsername().contains(search)) {
                usermodels.add(user);
            }
        }
        return usermodels;
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {
        log.info("UserQueryProvider : searchForUser(Map<String, String> params, RealmModel realm)");
        log.info("UserQueryProvider : params : "+ params);
        return searchForUser(params, realm, 0, Integer.MAX_VALUE);
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult, int maxResults) {
        log.info("UserQueryProvider : searchForUser(Map<String, String> params, RealmModel realm, int firstResult, int maxResults)");
        log.info("UserQueryProvider : params : "+ params);

        // only support searching by username
        String usernameSearchString = params.get("username");
        if (usernameSearchString == null) return Collections.EMPTY_LIST;
        return searchForUser(usernameSearchString, realm, firstResult, maxResults);
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {
        // runtime automatically handles querying UserFederatedStorage
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
        // runtime automatically handles querying UserFederatedStorage
        return Collections.EMPTY_LIST;
    }
    
    @Override
    public List<UserModel> getRoleMembers(RealmModel realm, RoleModel role, int firstResult, int maxResults) {
        // Not supported in federated storage
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<UserModel> getRoleMembers(RealmModel realm, RoleModel role) {
        // Not supported in federated storage
        return Collections.EMPTY_LIST;
    }


    @Override
    public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {
        // runtime automatically handles querying UserFederatedStorage
        return Collections.EMPTY_LIST;
    }


    // UserRegistrationProvider method implementations

    @Override
    public UserModel addUser(RealmModel realm, String username) {

        log.info("JdbcUserStorageProvider : addUser : "+ username);

        User user = new User();
        user.setUsername(username);

        return new FederatedUserAdapter(this.session, realm, this.model, user);
    }

    @Override
    public boolean removeUser(RealmModel realm, UserModel user) {
        // call service to remove user
        log.info("JdbcUserStorageProvider : removeUser : "+ user.getUsername());
        return true;
    }





    // CredentialInputValidator methods

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        //System.out.println("CredentialInputValidator : isConfiguredFor : " + credentialType);
        boolean configuredFor = false;

        if (credentialType.equals(CredentialModel.PASSWORD))
            configuredFor = true;

        return configuredFor;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        //System.out.println("CredentialInputValidator : supportsCredentialType : " + credentialType);
        return credentialType.equals(CredentialModel.PASSWORD);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel usermodel, CredentialInput input) {
        log.info("CredentialInputValidator : isValid");
        if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) return false;

        boolean isValid = false;
        UserCredentialModel cred = (UserCredentialModel)input;

        String password = cred.getValue();
        String username = usermodel.getUsername();
        User user = new User(username, password);
        User loggedInUser = databaseProxy.loginUser(user);

        if ((loggedInUser != null) && loggedInUser.isValid())
            isValid = true;
        return isValid;
    }

    // CredentialInputUpdater methods

    @Override
    public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
        if (!(input instanceof UserCredentialModel)) return false;
        if (!input.getType().equals(CredentialModel.PASSWORD)) return false;
        UserCredentialModel cred = (UserCredentialModel)input;
        // call update service with
        //user.getUsername(), cred.getValue());
        return true;
    }

    @Override
    public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
        if (!credentialType.equals(CredentialModel.PASSWORD)) return;

    }

    private static final Set<String> disableableTypes = new HashSet<>();

    static {
        disableableTypes.add(CredentialModel.PASSWORD);
    }

    @Override
    public Set<String> getDisableableCredentialTypes(RealmModel realm, UserModel user) {

        return disableableTypes;
    }
    @Override
    public void close() {

    }

}
