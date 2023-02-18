package com.Homes2Rent.Homes2Rent.dto;

import com.Homes2Rent.Homes2Rent.model.Authority;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class UserInputDto {

    @Column
    public String firstname;
    @Column
    public String lastname;

    @Column
    public String email;

    @Id
    @Column(nullable = false, unique = true)
    public String username;

    @Column(nullable = false, length = 255)
    public String password;
    @Column(nullable = false)
    public Boolean enabled = true;
    @Column
    public String apikey;

    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    public Set<Authority> authorities = new HashSet<>();

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }


}
