package com.bits.to.bytes.springsec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Userdata {

    @Id
    String username;
    String password;
    String role;
}
