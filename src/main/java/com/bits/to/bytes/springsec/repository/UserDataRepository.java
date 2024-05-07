package com.bits.to.bytes.springsec.repository;

import com.bits.to.bytes.springsec.model.Userdata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<Userdata, String> {
}
