package com.boost.voucherpool.controller;

import com.boost.voucherpool.model.Recipient;
import com.boost.voucherpool.model.SpecialOffer;
import com.boost.voucherpool.model.ValidVouchersWithName;
import com.boost.voucherpool.model.VoucherCode;
import com.boost.voucherpool.service.RecipientService;
import com.boost.voucherpool.service.SpecialOfferService;
import com.boost.voucherpool.service.VoucherCodeService;
import com.boost.voucherpool.util.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/main")
public class MainController {
    @Autowired
    private RecipientService recipientService;

    @Autowired
    private SpecialOfferService specialOfferService;

    @Autowired
    private VoucherCodeService voucherCodeService;

    // For a given Special Offer and an expiration date, generate for each Recipient a Voucher Code.
    @RequestMapping(value = "/newSpecialOffer", method = RequestMethod.POST)
    public ResponseEntity<CustomMessage> newSpecialOffer(@RequestParam("email") String email,
                                                         @RequestParam("expDate") String expDate,
                                                         @RequestBody SpecialOffer specialOffer) {
        try {
            Recipient recipient = recipientService.getRecipientByEmail(email);
            SpecialOffer newSpecialOffer = specialOfferService.newSpecialOffer(specialOffer);
            voucherCodeService.newVoucherCode(recipient, newSpecialOffer, expDate);
        } catch (Exception exception) {
            return new ResponseEntity<>
                    (new CustomMessage(exception.getMessage(), true),
                            HttpStatus.OK);
        }

        return new ResponseEntity<>
                (new CustomMessage("Special offer : " + specialOffer.getName() + " has been created!", false),
                        HttpStatus.OK);
    }

    // Provide an endpoint, reachable via HTTP, which receives a Voucher Code and Email and validates the
    // Voucher Code. In Case it is valid, return the Percentage Discount and set the date of usage.
    @RequestMapping(value = "/redeemVoucher", method = RequestMethod.GET)
    public ResponseEntity<CustomMessage> redeemVoucher(@RequestParam("voucherCode") String voucherCode,
                                                       @RequestParam("email") String email) {
        String message;
        try {
            message = voucherCodeService.validateVoucherCode(voucherCode, email);
        } catch (Exception exception) {
            return new ResponseEntity<>(new CustomMessage(exception.getMessage(), true), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CustomMessage(message, false), HttpStatus.OK);
    }

    // Extra: For a given Email, return all his valid Voucher Codes with the Name of the Special Offer.
    @RequestMapping(value = "/getAllValidVoucher", method = RequestMethod.GET)
    public ResponseEntity<CustomMessage> getAllValidVoucher(@RequestParam("email") String email) {
        List<VoucherCode> validVouchers;
        try {
            validVouchers = voucherCodeService.allValidVouchersByEmail(email);
        } catch (Exception exception) {
            return new ResponseEntity<>(new CustomMessage(exception.getMessage(), true), HttpStatus.OK);
        }

        List<ValidVouchersWithName> validVouchersName = new ArrayList<>();
        for (VoucherCode vc : validVouchers) {
            ValidVouchersWithName vvn = new ValidVouchersWithName();
            vvn.setVoucherCode(vc.getCode());
            vvn.setSpecialOffername(vc.getSpecialOffer().getName());
            validVouchersName.add(vvn);
        }
        return new ResponseEntity<>(new CustomMessage(validVouchersName), HttpStatus.OK);
    }
}
