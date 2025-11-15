package com.tedx.mailer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tedx.mailer.model.Speaker;

public interface SpeakerRepository extends MongoRepository<Speaker , String > {
    List<Speaker> findByMailStatus(String status);
   Speaker findFirstByEmail(String email);
   Optional<Speaker> findByEmail(String email);
   

}
