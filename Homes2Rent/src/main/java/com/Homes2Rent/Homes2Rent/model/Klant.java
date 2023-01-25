package com.Homes2Rent.Homes2Rent.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;


    @Entity
    @Table(name = "klanten")
    public class Klant {
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
        public String address;

        @NotBlank
        public String city;

        @NotBlank
        public String zipcode;

        @OneToMany(mappedBy = "klant")
        @LazyCollection(LazyCollectionOption.TRUE)
        @JsonIgnore
        private Set<Woning> woningen;

        public Klant(Long id, String email, String firstname, String lastname, String address, String city, String zipcode) {
            this.id = id;
            this.email = email;
            this.firstname = firstname;
            this.lastname = lastname;
            this.address = address;
            this.city = city;
            this.zipcode = zipcode;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public Set<Woning> getWoningen() {
            return woningen;
        }

        public void setWoningen(Set<Woning> woningen) {
            this.woningen = woningen;
        }

        public Klant() {
        }

       }