package com.boost.voucherpool.model;

import javax.persistence.*;

@Entity
@Table(name = "special_offer")
public class SpecialOffer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "special_offer_id", updatable = false, nullable = false)
    private Long specialOfferId;

    @Column(name = "name")
    private String name;

    @Column(name = "fixed_percentage_discount")
    private int fixedPercentageDiscount;

    public Long getSpecialOfferId() {
        return specialOfferId;
    }

    public void setSpecialOfferId(Long specialOfferId) {
        this.specialOfferId = specialOfferId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFixedPercentageDiscount() {
        return fixedPercentageDiscount;
    }

    public void setFixedPercentageDiscount(int fixedPercentageDiscount) {
        this.fixedPercentageDiscount = fixedPercentageDiscount;
    }
}
