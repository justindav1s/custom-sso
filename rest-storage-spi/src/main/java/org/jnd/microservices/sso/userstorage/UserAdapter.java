package org.jnd.microservices.sso.userstorage;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.*;
import org.keycloak.models.utils.DefaultRoles;
import org.keycloak.storage.adapter.AbstractUserAdapter;

import java.util.*;

public class UserAdapter {

    private static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(RestServiceUserStorageProvider.class);

    protected static UserModel createUserModel(RealmModel realm, KeycloakSession session, ComponentModel model, final User user) {
        log.info("UserAdapter : createUserModel : " + user);
        List<GroupModel> groups = realm.getGroups();


        final GroupModel customergroup;


        AbstractUserAdapter abstractUser = new AbstractUserAdapter(session, realm, model) {
            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public void setUsername(String username) {
                user.setUsername(username);
            }

            @Override
            public String getFirstName() {
                return user.getFirstname();
            }

            @Override
            public void setFirstName(String firstName) {
                user.setFirstname(firstName);
            }

            @Override
            public String getLastName() {
                return user.getLastname();
            }

            @Override
            public void setLastName(String lastName) {
                user.setLastname(lastName);
            }

            @Override
            public String getEmail() {
                return user.getEmail();
            }

            @Override
            public void setEmail(String email) {
                user.setEmail(email);
            }

            @Override
            public Set<GroupModel> getGroups()  {
                Set<GroupModel> groupset = new HashSet<>();
                for (GroupModel group : realm.getGroups()) {
                    for (String groupName : user.getGroups()) {
                        if (group.getName().equals("customer")) {
                            groupset.add(group);
                        }
                    }
                }
                return groupset;
            }

            @Override
            public Map<String,List<String>> getAttributes() {
                Map<String,List<String>> attributes = new HashMap<String,List<String>>();
                List<String> values = new ArrayList<>();
                Date now = new Date();
                values.add(now.toString());
                attributes.put("LAST_LOGIN", values);
                return attributes;
            }

            @Override
            public  void grantRole(RoleModel role) {
                log.info("UserAdapter : grantRole : "+role.getName());
                user.getRoles().add(role.getName());
            }

            @Override
            public  Set<RoleModel> getRoleMappings() {
                log.info("UserAdapter : getRoleMappings");
                Set<RoleModel> rmset = new HashSet<>();
                for (String userrole : user.getRoles()) {
                    for (RoleModel rm : realm.getRoles()) {
                        if (rm.getName().equals(userrole)) {
                            rmset.add(rm);
                        }
                    }
                }
                for (RoleModel role : rmset)  {
                    log.info("UserAdapter : getRoleMappings : "+role.getName());
                }
                return rmset;
            }

            @Override
            public  void deleteRoleMapping(RoleModel role) {
                log.info("UserAdapter : deleteRoleMapping : "+ role.getName());
                user.getRoles().remove(role);
            }

        };

        return abstractUser;
    }
}
