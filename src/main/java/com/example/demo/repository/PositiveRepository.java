package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Positive;

public interface PositiveRepository extends CrudRepository<Positive, Integer> {

}
