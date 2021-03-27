package com.boost.voucherpool.repositories;

import com.boost.voucherpool.model.VoucherCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface VoucherCodeRepository extends JpaRepository<VoucherCode, Long> {
    @Query("SELECT voucher FROM VoucherCode voucher WHERE voucher.code=:voucherCode")
    VoucherCode findVoucherCodeByCode(@Param("voucherCode") String voucherCode);

    @Transactional
    @Modifying
    @Query("UPDATE VoucherCode voucher SET voucher.used=:used, voucher.dateOfUsage=:dateOfUsage " +
            "WHERE voucher.voucherCodeId=:voucherCodeId")
    void updateVoucherCode(@Param("voucherCodeId") Long voucherCodeId, @Param("used") Boolean used,
                           @Param("dateOfUsage") Date dateOfUsage);

    @Query("SELECT voucher FROM VoucherCode voucher " +
            "WHERE voucher.recipient.email=:email AND voucher.used=false AND voucher.expirationDate >= DATE(NOW())")
    List<VoucherCode> getAllValidVoucherCodeWithSONameByEmail(@Param("email") String email);

    @Query("SELECT voucher FROM VoucherCode voucher WHERE voucher.code=:code")
    VoucherCode findDuplicateCode(@Param("code") String code);
}
