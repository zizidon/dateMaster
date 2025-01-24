package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.DateShare;

public interface DateShareRepository extends CrudRepository<DateShare, Long> {
	@Query("SELECT * FROM date_share WHERE " +
			"COALESCE(spot1, 0) = COALESCE(:spot1, 0) AND " +
			"COALESCE(spot2, 0) = COALESCE(:spot2, 0) AND " +
			"COALESCE(spot3, 0) = COALESCE(:spot3, 0)")
	DateShare findBySpot1AndSpot2AndSpot3(
			@Param("spot1") Long spot1,
			@Param("spot2") Long spot2,
			@Param("spot3") Long spot3);

	@Query("SELECT * FROM date_share WHERE spot1 IS NOT NULL ORDER BY count DESC LIMIT 5")
	List<DateShare> findTop5ByOrderByCountDesc();
}
