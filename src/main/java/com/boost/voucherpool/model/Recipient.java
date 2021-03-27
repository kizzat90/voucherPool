package com.boost.voucherpool.model;

import javax.persistence.*;

@Entity
@Table(name = "recipient")
public class Recipient {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "recipient_id", updatable = false, nullable = false)
    private Long recipientId;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
