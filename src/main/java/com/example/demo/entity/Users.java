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
<<<<<<< HEAD
	private Long applicant; //申請元ID
=======
	private Long applicant; // 申請者名
	private Long coaching_id; // コーチングの問題
>>>>>>> branch 'master' of https://github.com/zizidon/dateMaster.git
}
