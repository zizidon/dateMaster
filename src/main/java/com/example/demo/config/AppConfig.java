package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.service.UserLoginService;

@Configuration
public class AppConfig {

	@Bean
	public UserLoginService userLoginService() {
		return new UserLoginService();
	}

}
