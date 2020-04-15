package com.example.securitydemo.controller;

import com.example.securitydemo.domain.Journal;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;



public interface JournalRepository extends MongoRepository<Journal, String> {
 List<Journal> findByTitleLike(String word);
 List<Journal> findBySummaryLike(String word);
  @Query("{ 'title' : { $regex: ?0 } }")
 List<Journal> findByRegex(String title);
 
}
