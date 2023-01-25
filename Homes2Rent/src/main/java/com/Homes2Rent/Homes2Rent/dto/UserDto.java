package com.Homes2Rent.Homes2Rent.dto;

import com.Homes2Rent.Homes2Rent.model.Authority;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.management.relation.Role;
import javax.persistence.Id;
import java.util.Collection;
import java.util.Set;

public class UserDto {



    @Id
    public String username;
    public Object email;

    private String firstname;

    public Boolean enabled;

    public String apikey;

    private String Email;

    private String lastname;

    public String password;

    @JsonSerialize
    public Collection<Object> authorities;

    public static Collection<Role> rol;




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Collection<Role> getRol() {
        return rol;
    }

    public static void setRol(Collection<Role> rol) {
        UserDto.rol = rol;
    }


    public Set<Authority> getAuthorities() {
        return null;
    }
}





