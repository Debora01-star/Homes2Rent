package com.Homes2Rent.Homes2Rent.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BoekingWoningKey  implements Serializable {

    public Long getBoekingId() {
        return boekingId;
    }

    public void setBoekingId(Long boekingId) {
        this.boekingId = boekingId;
    }

    public Long getWoningId() {
        return woningId;
    }

    public void setWoningId(Long woningId) {
        this.woningId = woningId;
    }

    @Column(name = "boeking_id")
    private Long boekingId;

    @Column(name = "woning_id")
    private Long woningId;

    public BoekingWoningKey() {}

    public BoekingWoningKey(Long boekingId, Long woningId) {
        this.boekingId = boekingId;
        this.woningId = woningId;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        BoekingWoningKey that = (BoekingWoningKey) o;
        return boekingId.equals(that.boekingId)&& woningId.equals(that.woningId);
    }

    @Override
    public int hashCode() {return Objects.hash(boekingId, woningId);}
}


