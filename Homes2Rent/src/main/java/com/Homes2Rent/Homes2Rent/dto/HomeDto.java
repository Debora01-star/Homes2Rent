package com.Homes2Rent.Homes2Rent.dto;


public class HomeDto {


    public Long id;

    private String type;
    private String name;
    private Integer price;
    private String rented;


    public HomeDto(Long id, String type, String name, Integer price, String rented) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.rented = rented;


    }
    public HomeDto() {


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
