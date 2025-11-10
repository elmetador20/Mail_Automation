package com.tedx.mailer.service;

import com.tedx.mailer.model.Speaker;
import com.tedx.mailer.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MailScheduler {
    @Autowired
    private SpeakerRepository repo;
    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 60000)
    public void sendPendingInvitations() {
        List<Speaker> pending = repo.findByMailStatus("PENDING");
        for (Speaker s : pending) {
            try {
                emailService.sendInvitation(s.getEmail(), s.getName(), s.getTopic());
                s.setMailStatus("SENT");
            } catch (Exception e) {
                s.setMailStatus("FAILED");
            } finally {
                repo.save(s);
            }
        }
    }
}
