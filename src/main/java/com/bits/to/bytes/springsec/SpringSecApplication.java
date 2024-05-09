package com.bits.to.bytes.springsec;

import com.twilio.Twilio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecApplication {

	public static void main(String[] args) {
		Twilio.init("******", "********");
		SpringApplication.run(SpringSecApplication.class, args);
	}

}
