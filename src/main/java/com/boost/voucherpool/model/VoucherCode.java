package com.boost.voucherpool.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "voucher_code")
public class VoucherCode {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "voucher_code_id", updatable = false, nullable = false)
    private Long voucherCodeId;

    @Column(name = "code", unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id")
    private Recipient recipient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "special_offer_id")
    private SpecialOffer specialOffer;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "used")
    private Boolean used;

    @Column(name = "date_of_usage")
    private Date dateOfUsage;

    public Long getVoucherCodeId() {
        return voucherCodeId;
    }

    public void setVoucherCodeId(Long voucherCodeId) {
        this.voucherCodeId = voucherCodeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public SpecialOffer getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(SpecialOffer specialOffer) {
        this.specialOffer = specialOffer;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Date getDateOfUsage() {
        return dateOfUsage;
    }

    public void setDateOfUsage(Date dateOfUsage) {
        this.dateOfUsage = dateOfUsage;
    }
}
