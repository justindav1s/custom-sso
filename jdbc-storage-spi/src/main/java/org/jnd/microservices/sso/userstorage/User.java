package org.jnd.microservices.sso.userstorage;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;


public class User implements Serializable {


    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private HashSet groups = new HashSet();
    private HashSet roles= new HashSet();
    private HashMap<String, String> attributes = new HashMap<>();

    public User(String username, String password, String firstname, String lastname, String email, String id) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.id = id;
    }

    public User(String username, String password, String firstname, String lastname, String email, String id, String roles) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.id = id;

        String[] rolesArr = roles.split(",");
        for (String role : rolesArr)    {
            this.roles.add(role.trim());
        }

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
    }

    public User() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return ReflectionToStringBuilder.toString(this);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashSet<String> getGroups() {
        return groups;
    }

    public void setGroups(HashSet groups) {
        this.groups = groups;
    }

    public HashSet<String> getRoles() {
        return roles;
    }

    public void setRoles(HashSet roles) {
        this.roles = roles;
    }

    public boolean isValid()    {
        boolean isValid = false;
        if ( (this.getFirstname() != null) &&  (this.getLastname() != null) )   {
            isValid = true;
        }
        return isValid;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }
}
