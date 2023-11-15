package com.Homes2Rent.Homes2Rent.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.List;



@Entity
@Table(name = "home")
public class Home {


    @Id
    @GeneratedValue

    private Long id;

    private String type;
    private String name;
    private Integer price;
    private String rented;


    @OneToMany(mappedBy = "home")
    @JsonIgnore
    List<Booking> booking;

    public List<Booking> getBooking() {
        return booking;
    }

    public void setBooking(List<Booking> booking) {
        this.booking = booking;
    }

    public Home(Long id, String type, String name, Integer price, String rented) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.rented = rented;
    }

    public Home(Long id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public Home(String type, String name, Long id, Integer price, String rented) {
        this.type = type;
        this.name = name;
        this.id = id;
        this.price = price;
        this.rented = rented;
    }

    public Home() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRented() {
        return rented;
    }

    public void setRented(String rented) {
        this.rented = rented;
    }


}

