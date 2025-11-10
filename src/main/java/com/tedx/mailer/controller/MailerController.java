package com.tedx.mailer.controller;

import com.tedx.mailer.model.Speaker;

import com.tedx.mailer.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.tedx.mailer.repository.SpeakerRepository;

@RestController
@RequestMapping("/api/mail")
public class MailerController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SpeakerRepository speakerRepository;

    @PostMapping("/addSpeaker")
    public Speaker addSpeaker(@RequestBody Speaker speaker) {
        speaker.setMailStatus("not_sent"); // Default value
        return speakerRepository.save(speaker);
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
