package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.RandomShare;

public interface RandomShareRepository extends CrudRepository<RandomShare, Long> {
	@Query("SELECT * FROM random_share WHERE partner_id = :partnerId AND user_id = :userId ORDER BY created_at DESC LIMIT 1")
	Optional<RandomShare> findByPartnerIdAndUserId(
			@Param("partnerId") Long partnerId,
			@Param("userId") Long userId);
}
