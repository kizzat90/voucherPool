package com.boost.voucherpool.controller;

import com.boost.voucherpool.model.SpecialOffer;
import com.boost.voucherpool.service.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/specialOffer")
public class SpecialOfferController {
    @Autowired
    private SpecialOfferService specialOfferService;

    // Retrieve All Special Offers
    @RequestMapping(value = "/getAllSpecialOfferList", method = RequestMethod.GET)
    public List<SpecialOffer> getAllSpecialOfferList() {
        return specialOfferService.getAllSpecialOffer();
    }
}
