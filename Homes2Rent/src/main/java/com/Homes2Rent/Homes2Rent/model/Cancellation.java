package com.Homes2Rent.Homes2Rent.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "cancellation")
    public class Cancellation {

        @Id
        @GeneratedValue
        private Long id;

        private LocalDate finish_date;

        private String status;

        private String type_booking;

        private Integer price;

        private String name;

        @OneToOne
        public Home home;

        @OneToMany(mappedBy = "cancellation")
        @JsonIgnore
        List<Booking> booking;

    public List<Booking> getBooking() {
        return booking;
    }

    public void setBooking(List<Booking> booking) {
        this.booking = booking;
    }


        public Cancellation(Long id, LocalDate finish_date, String status, String type_booking, Home home, Integer price, String name) {
            this.id = id;
            this.finish_date = finish_date;
            this.status = status;
            this.type_booking = type_booking;
            this.home = home;
            this.price = price;
            this.name = name;

        }


        public Cancellation() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public LocalDate getFinish_date() {
            return finish_date;
        }

        public void setFinish_date(LocalDate finish_date) {
            this.finish_date = finish_date;
        }


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType_boeking() {
            return type_booking;
        }

        public void setType_booking(String type_booking) {
            this.type_booking = type_booking;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Home getHome() {
            return home;
        }

        public void setHome(Home home) {
            this.home = home;
        }
    }

