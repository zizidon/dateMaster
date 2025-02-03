package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.RandomRanking;

public interface RandomDateShareRepository extends CrudRepository<RandomRanking, Long> {

}