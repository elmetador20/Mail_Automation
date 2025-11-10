package com.tedx.mailer.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tedx.mailer.model.Speaker;

public interface SpeakerRepository extends MongoRepository<Speaker , String> {
    List<Speaker> findByMailStatus(String status);

}
