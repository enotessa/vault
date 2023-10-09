package com.hashicorp.test;

import com.hashicorp.test.vault.VaultConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class TestApplication {

	@Autowired
	VaultConfig vaultConfig;

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
}
