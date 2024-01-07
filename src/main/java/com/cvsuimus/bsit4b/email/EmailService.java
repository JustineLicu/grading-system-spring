package com.cvsuimus.bsit4b.email;

import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender emailSender;

  @Value("${spring.mail.username}")
  private String fromEmail;

  public Boolean send(String toEmail, String subject, String text) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(fromEmail);
      message.setTo(toEmail);
      message.setSubject(subject);
      message.setText(text);
      emailSender.send(message);
      return true;
    } catch (MailException e) {
      return false;
    }
  }
}
