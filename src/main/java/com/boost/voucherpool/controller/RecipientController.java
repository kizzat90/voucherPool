package com.boost.voucherpool.controller;

import com.boost.voucherpool.model.Recipient;
import com.boost.voucherpool.service.RecipientService;
import com.boost.voucherpool.util.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recipient")
public class RecipientController {
    @Autowired
    private RecipientService recipientService;

    // Create new Recipient
    @RequestMapping(value = "/newRecipient", method = RequestMethod.POST)
    public ResponseEntity<CustomMessage> newRecipient(@RequestParam("name") String name,
                                                      @RequestParam("email") String email) {
        try {
            Recipient recipient = recipientService.newRecipient(name, email);
            return new ResponseEntity<>(new CustomMessage("Created user : " + recipient.getName() +"!", false),
                    HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new CustomMessage(exception.getMessage(), true), HttpStatus.OK);
        }
    }

    // Retrieve All Recipients
    @RequestMapping(value = "/getAllRecipient", method = RequestMethod.GET)
    public List<Recipient> getAllRecipient() {
        return recipientService.getAllRecipient();
    }
}
