package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.service.NameChangeService;
import com.example.demo.service.PasswordChangeService;
import com.example.demo.service.UserDeleteService;
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

	@Bean
	public NameChangeService nameChangeService() {
		return new NameChangeService();
	}

	@Bean
	public PasswordChangeService passwordChangeService() {
		return new PasswordChangeService();
	}

	@Bean
	public UserDeleteService userDeleteService() {
		return new UserDeleteService();
	}

}
