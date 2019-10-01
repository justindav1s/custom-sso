package org.jnd.microservices.sso.userstorage;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.*;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import java.util.*;

public class FederatedUserAdapter extends AbstractUserAdapterFederatedStorage {

    private final User user;
    private final String keycloakId;

    private static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(JdbcUserStorageProvider.class);

    public FederatedUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, User user) {
        super(session, realm, model);
        this.user = user;

        log.info("FederatedUserAdapter : constructor user : "+ this.user);
        log.info("FederatedUserAdapter : constructor user id : "+ this.user.getId());

        this.keycloakId = StorageId.keycloakId(model, user.getId());
    }

    @Override
    public String getId() {
        return keycloakId;
    }

    @Override
    public String getUsername() {
        log.info("FederatedUserAdapter : getUsername : "+ user.getUsername());
        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {
        log.info("FederatedUserAdapter : setUsername : "+ username);
        user.setUsername(username);
    }

    @Override
    public String getEmail() {
        log.info("FederatedUserAdapter : getEmail");
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        log.info("FederatedUserAdapter : setEmail");
        user.setEmail(email);
    }

    @Override
    public String getFirstName() {
        log.info("FederatedUserAdapter : getFirstName");
        return user.getFirstname();
    }

    @Override
    public void setFirstName(String firstName) {
        log.info("FederatedUserAdapter : setFirstName");
        user.setFirstname(firstName);
    }

    @Override
    public String getLastName() {
        log.info("FederatedUserAdapter : getLastName");
        return user.getLastname();
    }

    @Override
    public void setLastName(String lastName) {
        log.info("FederatedUserAdapter : setLastName");
        user.setLastname(lastName);
    }

    @Override
    protected Set<RoleModel> getFederatedRoleMappings() {
        log.info("FederatedUserAdapter : getFederatedRoleMappings");
        return getFederatedStorage().getRoleMappings(realm, this.getId());
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        log.info("FederatedUserAdapter : getAttributes");
        Map<String,List<String>> attributes = new HashMap<String,List<String>>();
        for (String key : user.getAttributes().keySet())    {
            List<String> values = new ArrayList<>();
            values.add(user.getAttributes().get(key));
            attributes.put(key, values);
        }
        return attributes;
    }

    @Override
    public Set<GroupModel> getGroups()  {
        log.info("FederatedUserAdapter : getGroups");
        Set<GroupModel> groupset = new HashSet<>();
        for (GroupModel group : realm.getGroups()) {
            log.info("Realm group : "+group.getName());
            for (String groupName : user.getGroups()) {
                log.info("Remote User group : "+groupName);
                if (group.getName().equals(groupName)) {
                    log.info("Realm User group : "+groupName+" setting up in KC");
                    groupset.add(group);
                }
            }
        }
        return groupset;
    }

    @Override
    public  Set<RoleModel> getRoleMappings() {
        log.info("FederatedUserAdapter : getRoleMappings");
        Set<RoleModel> rmset = new HashSet<>();
        for (String userrole : user.getRoles()) {
            log.info("Remote User role : "+userrole);
            for (RoleModel rm : realm.getRoles()) {
                log.info("Realm role : "+userrole);
                if (rm.getName().equals(userrole)) {
                    log.info("Realm role : "+userrole+" setting up in KC");
                    rmset.add(rm);
                }
            }

        }
        for (RoleModel role : rmset)  {
            log.info("FederatedUserAdapter : getRoleMappings role : "+role.getName());
        }

        for (ClientModel cm : realm.getClients()) {
            log.info("Realm client : "+cm.getName()+" "+cm.getClientId()+" "+cm.getId());
            for (RoleModel rm : cm.getRoles()) {
                log.info("Realm client role : "+cm.getClientId()+" "+rm.getName());
            }
        }
        return rmset;
    }

}
