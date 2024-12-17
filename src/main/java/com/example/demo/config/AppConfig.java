package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.service.UserLoginService;
import com.example.demo.service.UserRegisterService;

@Configuration
public class AppConfig {

	@Bean
	public UserLoginService userLoginService() {
		return new UserLoginService();
	}
	
	@Bean
	public UserRegisterService userRegisterService() {
		return new UserRegisterService();
	}

}
