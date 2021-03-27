package com.boost.voucherpool.service;

import com.boost.voucherpool.model.Recipient;
import com.boost.voucherpool.repositories.RecipientRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecipientService {
    @Resource
    private RecipientRepository recipientRepository;

    // Create new recipient
    public Recipient newRecipient(String name, String email) {
        Recipient recipient = new Recipient();
        recipient.setName(name);
        recipient.setEmail(email);

        if (recipientRepository.getRecipientByEmail(email) != null) {
            return null;
        } else {
            recipient.setEmail(email);
        }
        return recipientRepository.save(recipient);
    }

    // Retrieve Recipient by Email
    public Recipient getRecipientByEmail(String email) {
        return recipientRepository.getRecipientByEmail(email);
    }

    // Retrieve All Recipients
    public List<Recipient> getAllRecipient() {
        return recipientRepository.findAll();
    }
}
