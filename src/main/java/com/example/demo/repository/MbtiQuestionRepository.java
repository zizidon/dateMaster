package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.MbtiQuestion;

public interface MbtiQuestionRepository extends CrudRepository<MbtiQuestion, Long> {
}
