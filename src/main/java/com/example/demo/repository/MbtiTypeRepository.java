package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.MbtiType;

public interface MbtiTypeRepository extends CrudRepository<MbtiType, Long> {
	Optional<MbtiType> findByType(String type);
}