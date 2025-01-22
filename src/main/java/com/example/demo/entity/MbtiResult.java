package com.example.demo.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MbtiResult {

	@Id
	private Long id;
	
	private String resultType;
	private String description;
	private Long userId;
	
	public MbtiResult(String resultType,Long userId,String description) {
		this.resultType = resultType;
		this.userId = userId;
		this.description = description; // 追加
	}
}