package com.boost.voucherpool.service;

import com.boost.voucherpool.model.Recipient;
import com.boost.voucherpool.model.SpecialOffer;
import com.boost.voucherpool.model.VoucherCode;
import com.boost.voucherpool.repositories.VoucherCodeRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class VoucherCodeService {
    @Autowired
    private SpecialOfferService specialOfferService;

    @Resource
    private VoucherCodeRepository voucherCodeRepository;

    // Create new Voucher Code
    public void newVoucherCode(Recipient recipient, SpecialOffer specialOffer, String expDate) throws Exception {
        VoucherCode voucherCode = new VoucherCode();
        voucherCode.setCode(this.generateNewCode());
        voucherCode.setUsed(false);
        voucherCode.setRecipient(recipient);
        voucherCode.setSpecialOffer(specialOffer);

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = format.parse(expDate);
        Calendar now = Calendar.getInstance();
        if (date.after(now.getTime())) {
            voucherCode.setExpirationDate(date);
        } else {
            throw new Exception("Expiration date is before the current time.");
        }
        voucherCodeRepository.save(voucherCode);
    }

    // Generate Voucher Code (at least 8 chars)
    public String generateNewCode() {
        String code;
        do {
            code = RandomStringUtils.randomAlphanumeric(8, 14);
        } while (!checkDuplicateCode(code));
        return code;
    }

    // Validate Voucher Code
    public String validateVoucherCode(String voucherCode, String email) throws Exception {
        VoucherCode vc = voucherCodeRepository.findVoucherCodeByCode(voucherCode);
        if (vc == null) {
            throw new Exception("Invalid voucher code");
        } else if (!vc.getRecipient().getEmail().equals(email)) {
            throw new Exception("Email does not match from dB");
        } else if (vc.getUsed()) {
            throw new Exception("Voucher has already been used");
        } else if (vc.getExpirationDate().before(Calendar.getInstance().getTime())) {
            throw new Exception("Voucher is already expired");
        } else {
            voucherCodeRepository.updateVoucherCode(vc.getVoucherCodeId(), true, Calendar.getInstance().getTime());
            return "Percentage Discount : " + vc.getSpecialOffer().getFixedPercentageDiscount() + "%";
        }
    }

    // Get all Valid Vouchers
    public List<VoucherCode> allValidVouchersByEmail(String email) throws Exception {
        List<VoucherCode> allValidVouchersList = voucherCodeRepository.getAllValidVoucherCodeWithSONameByEmail(email);
        if (!allValidVouchersList.isEmpty()) {
            return voucherCodeRepository.getAllValidVoucherCodeWithSONameByEmail(email);
        } else {
            throw new Exception("There are no valid vouchers for " + email);
        }
    }

    // To prevent duplicate generated code (Just in case)
    private Boolean checkDuplicateCode(String code) {
        return voucherCodeRepository.findDuplicateCode(code) == null;
    }

    // Get all Vouchers (Valid or Invalid)
    public List<VoucherCode> getAllVouchers() {
        return voucherCodeRepository.findAll();
    }
}
