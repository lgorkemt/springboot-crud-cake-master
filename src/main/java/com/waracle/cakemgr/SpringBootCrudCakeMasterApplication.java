package com.waracle.cakemgr;

import com.waracle.cakemgr.dao.CakeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCrudCakeMasterApplication {

	private Logger LOG = LoggerFactory.getLogger("Application");

	private final CakeRepository cakeRepository;

	@Autowired
	public SpringBootCrudCakeMasterApplication(CakeRepository cakeRepository) {
		this.cakeRepository = cakeRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudCakeMasterApplication.class, args);
	}
}

