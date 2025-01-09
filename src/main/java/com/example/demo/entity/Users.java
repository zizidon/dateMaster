package com.example.demo.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

	@Id
	private Long id; //自動採番
	private String name; //ユーザー名
	private String password; //パスワード
	private Long partner; // パートナー名
	private Long applicant; // 申請者名
}
