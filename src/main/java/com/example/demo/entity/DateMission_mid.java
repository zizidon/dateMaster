package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("mission_middle") // テーブル名を指定

public class DateMission_mid {
	
	 @Id
	 private Long missionId;
	 private String missionText;

}
