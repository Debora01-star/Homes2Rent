package com.Homes2Rent.Homes2Rent.dto;

import com.Homes2Rent.Homes2Rent.model.Woning;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

public class KlantInputDto {

    Long id;

    @Email
    @Column(unique = true)
    public String email;

    @NotBlank
    public String firstname;

    @NotBlank
    @Size(min=3, max=128)
    public String lastname;

    @NotBlank
    public String address;

    @NotBlank
    public String city;

    @NotBlank
    public String zipcode;

    @NotBlank
    private Set<Woning> woningen;

    public KlantInputDto(Long id, @NotBlank String email, @NotBlank String firstname, @NotBlank String lastname, @NotBlank String address, @NotBlank String city, @NotBlank String zipcode) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;

    }

    public KlantInputDto() {
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
}



