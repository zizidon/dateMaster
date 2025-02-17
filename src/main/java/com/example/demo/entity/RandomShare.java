package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("random_share")
public class RandomShare {
	@Id
	@Column("share_id")
	private Long shareId;

	@Column("spot1")
	private Long spot1;

	@Column("spot2")
	private Long spot2;

	@Column("spot3")
	private Long spot3;

	@Column("count")
	private int count;

	@Column("user_id")
	private Long userId;

	@Column("partner_id")
	private Long partnerId;

	@Column("created_at")
	private LocalDateTime createdAt;
}
