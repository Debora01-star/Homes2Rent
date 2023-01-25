package com.Homes2Rent.Homes2Rent.model;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Table(name="users")
public class User {
    @Id
    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public void addAuthority(Authority authority) {
    }

    public Collection<Object> getAuthorities() {
        return null;
    }

    public void removeAuthority(Authority authorityToRemove) {
    }

    public Boolean isEnabled() {
        return null;
    }

    public String getApikey() {
        return null;
    }

    public Object getEmail() {
        return null;
    }

    public void setEnabled(Object enabled) {
    }

    public void setApikey(Object apikey) {
    }

    public void setEmail(String email) {

    }
}
