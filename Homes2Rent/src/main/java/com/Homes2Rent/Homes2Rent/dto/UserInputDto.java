package com.Homes2Rent.Homes2Rent.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Collection;

public class UserInputDto {


        public String firstname;

        public String lastname;

        @Email
        public String email;

        @Column(unique = true)
        public String username;

        @Size(min=6, max=20)
        public String password;

         @JsonSerialize
         public Collection<Object> authorities;


    public static Long[] roles;


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

    public static Long[] getRoles() {
        return roles;
    }

    public static void setRoles(Long[] roles) {
        UserInputDto.roles = roles;
    }

    public void setApikey(String randomString) {
    }

    public Object getEnabled() {
        return null;
    }

    public Object getApikey() {
        return null;
    }
}
