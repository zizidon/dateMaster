package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Coaching;

public interface CoachingRepository extends CrudRepository<Coaching, Long> {

	Iterable<Coaching> findByUserId(Long userId); // userIdに基づいてCoachingを検索

	Iterable<Coaching> findByPartnerId(Long partnerId); // partnerIdに基づいてCoachingを検索

}
