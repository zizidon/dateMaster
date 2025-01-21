package com.example.demo.repository; 


import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.DateShare; 

public interface DateShareRepository extends CrudRepository<DateShare, Long> { 

    // spot1, spot2, spot3が一致するプランを検索 

    DateShare findBySpot1AndSpot2AndSpot3(Long spot1, Long spot2, Long spot3); 

    
}
