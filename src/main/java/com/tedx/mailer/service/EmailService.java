package com.tedx.mailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendInvitation(String to, String name, String topic) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("TEDxKIET Speaker Invitation");
        msg.setText("Dear " + name + ",\n\nWe are thrilled to invite you to speak on '" + topic + 
                    "' at TEDxKIET.\nPlease confirm your availability.\n\nWarm regards,\nTeam TEDxKIET");
        mailSender.send(msg);
    }
}
