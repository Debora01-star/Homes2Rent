package com.Homes2Rent.Homes2Rent.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
    public class Annulering {

        @Id
        @GeneratedValue
        private Long id;

        private LocalDate finish_date;

        private String status;

        private String type_boeking;

        private Integer price;

        private String name;

        @OneToOne
        public Woning woning;

        @OneToMany(mappedBy = "annulering")
        @JsonIgnore
        List<Boeking> boekingen;


        public Annulering(Long id, LocalDate finish_date, String status, String type_boeking, Woning woning, Integer price, String name) {
            this.id = id;
            this.finish_date = finish_date;
            this.status = status;
            this.type_boeking = type_boeking;
            this.woning = woning;
            this.price = price;
            this.name = name;

        }


        public Annulering() {
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
            return type_boeking;
        }

        public void setType_boeking(String type_boeking) {
            this.type_boeking = type_boeking;
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

        public Woning getWoning() {
            return woning;
        }

        public void setWoning(Woning woning) {
            this.woning = woning;
        }
    }

