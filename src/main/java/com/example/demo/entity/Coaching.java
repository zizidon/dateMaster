package com.example.demo.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coaching {

	@Id
	private Long id; // 問題のID（自動採番）

	private String question; // 問題のテキスト

	private String option1; // 選択肢1

	private String option2; // 選択肢2

	private String option3; // 選択肢3

	private String option4; // 選択肢4

	private int correctOption; // 正解の選択肢番号（1, 2, 3, 4）

	private Long userId; //ユーザーID
	
	private Long partnerId; //パートナーID
}