package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.MbtiType;

public interface MbtiTypeRepository extends CrudRepository<MbtiType, Long> {
	Optional<MbtiType> findByType(@Param("type") String type);
}