package com.Homes2Rent.Homes2Rent.dto;


import com.Homes2Rent.Homes2Rent.model.User;

import javax.persistence.*;
import java.util.Collection;

public class RoleDto {

        private Long id;

    @ManyToMany(mappedBy = "roles")
        private String rolename;

        public RoleDto(Long id, String rolename) {
            this.id = id;
            this.rolename = rolename;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setRolename(String rolename) {
            this.rolename = rolename;
        }

        public Collection<User> getUsers() {
            return users;
        }

        public void setUsers(Collection<User> users) {
            this.users = users;
        }

        public RoleDto() {}

        private Collection<User> users;

        public String getRolename() {
            return rolename;
        }
    }

