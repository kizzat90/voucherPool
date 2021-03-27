package com.boost.voucherpool.service;

import com.boost.voucherpool.model.SpecialOffer;
import com.boost.voucherpool.repositories.SpecialOfferRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SpecialOfferService {
    @Resource
    private SpecialOfferRepository specialOfferRepository;

    // Create new Special Offer
    public SpecialOffer newSpecialOffer(SpecialOffer specialOffer) {
        specialOffer.setSpecialOfferId(null);
        return specialOfferRepository.save(specialOffer);
    }

    // Retrieve All Special Offers
    public List<SpecialOffer> getAllSpecialOffer() {
        return specialOfferRepository.findAll();
    }
}
