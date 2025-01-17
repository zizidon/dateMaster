package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Negative;

public interface NegativeRepository extends CrudRepository<Negative, Integer> {

}
