package com.tedx.mailer.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tedx.mailer.model.Speaker;
import com.tedx.mailer.repository.SpeakerRepository;
import com.tedx.mailer.service.EmailService;


@RestController
@RequestMapping("/api/mail")
@CrossOrigin(origins = "*")
public class MailerController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping("/show_speakers")
     public ResponseEntity<?> getAllSpeaker(){
         List<Speaker> all=speakerRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping("/addSpeaker")
    public Speaker addSpeaker(@RequestBody Speaker speaker) {
        speaker.setMailStatus("not_sent"); // Default value
        return speakerRepository.save(speaker);
    }
  @DeleteMapping("/delSpeaker/{email}")
public ResponseEntity<?> deleteByEmail(@RequestBody Map<String, String> request) {
    String email = request.get("email");
    if (email == null || email.isBlank()) {
        return ResponseEntity.badRequest().body("Email required");
    }

    Optional<Speaker> speaker = speakerRepository.findByEmail(email);
    if (speaker.isEmpty()) {
        return ResponseEntity.status(404).body("Speaker not found");
    }

    speakerRepository.delete(speaker.get());
    return ResponseEntity.ok("Deleted speaker with email = " + email);
}

     
    
    @PostMapping("/sendInvites")
    public String sendInvites() {
        List<Speaker> unsentSpeakers = speakerRepository.findByMailStatus("not_sent");
        int count = 0;

        for (Speaker s : unsentSpeakers) {
            emailService.sendInvitation(s.getEmail(), s.getName(), s.getTopic());
            s.setMailStatus("sent");
            speakerRepository.save(s);
            count++;
        }

        return count + " invitation(s) sent successfully!";
    }
}
