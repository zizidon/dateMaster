package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.MbtiResult;

public interface MbtiResultRepository extends CrudRepository<MbtiResult, Long> {

	List<MbtiResult> findByUserId(Long id);
}
