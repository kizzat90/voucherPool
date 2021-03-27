package com.boost.voucherpool.controller;

import com.boost.voucherpool.model.VoucherCode;
import com.boost.voucherpool.service.VoucherCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/voucherCode")
public class VoucherCodeController {
    @Autowired
    private VoucherCodeService voucherCodeService;

    // Retrieve All Special Offers
    @RequestMapping(value = "/getAllVoucherCodeList", method = RequestMethod.GET)
    public List<VoucherCode> getAllVoucherCodeList() {
        return voucherCodeService.getAllVouchers();
    }

}
