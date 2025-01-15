package com.example.demo.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Positive {

	@Id
	private int typeId; // 自動採番

	private String type; // タイプ
	private String text; // 説明文
}
