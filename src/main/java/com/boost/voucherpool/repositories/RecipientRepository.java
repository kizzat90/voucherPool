package com.boost.voucherpool.repositories;

import com.boost.voucherpool.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    @Query("SELECT recipient FROM Recipient recipient WHERE recipient.email=:email")
    Recipient getRecipientByEmail(@Param("email") String email);
}
