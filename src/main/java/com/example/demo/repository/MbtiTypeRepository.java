package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.MbtiType;

public interface MbtiTypeRepository extends CrudRepository<MbtiType, Long> {
	List<MbtiType> findByType(String type);
}
