package com.Homes2Rent.Homes2Rent.model;

import com.Homes2Rent.Homes2Rent.security.AuthorityKey;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;




    @IdClass(AuthorityKey.class)
    @Table(name = "authorities")
    public class Authority implements Serializable {

        @Id
        @Column(nullable = false)
        private String username;

        @Id
        @Column(nullable = false)
        private String authority;

        public Authority() {}
        public Authority(String username, String authority) {
            this.username = username;
            this.authority = authority;
        }

        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getAuthority() {
            return authority;
        }
        public void setAuthority(String authority) {
            this.authority = authority;
        }

    }

