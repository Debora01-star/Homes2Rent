package com.Homes2Rent.Homes2Rent.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;



    @Entity
    @Table(name = "customers")
    public class Customer {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Email
        @Column(unique = true)
        public String email;

        @NotBlank
        public String firstname;

        @NotBlank
        public String lastname;

        @NotBlank
        public String streetname;

        @NotBlank
        public String town;

        @NotBlank
        public String zipcode;


        public Customer(Long id, String email, String firstname, String lastname, String streetname, String town, String zipcode) {
            this.id = id;
            this.email = email;
            this.firstname = firstname;
            this.lastname = lastname;
            this.streetname = streetname;
            this.town = town;
            this.zipcode = zipcode;
        }

        public Customer() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getStreetname() {
            return streetname;
        }

        public void setStreetname(String streetname) {
            this.streetname = streetname;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }
    }
