package com.bits.to.bytes.springsec.repository;

import com.bits.to.bytes.springsec.model.Otpdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpdetailsRepository extends JpaRepository<Otpdetails, String> {
}
