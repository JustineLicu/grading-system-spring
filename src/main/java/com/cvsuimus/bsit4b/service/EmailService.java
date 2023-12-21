package com.cvsuimus.bsit4b.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.cvsuimus.bsit4b.dto.ResponseDto;
import com.cvsuimus.bsit4b.dto.email.SendEmailDto;

@Component
public class EmailService {

  @Autowired
  private JavaMailSender emailSender;

  @Value("${spring.mail.username}")
  private String sender;

  public ResponseEntity<ResponseDto<String>> sendSimpleMessage(SendEmailDto email, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(sender);
      message.setTo(email.getTo());
      message.setSubject(email.getSubject());
      message.setText(email.getText());
      emailSender.send(message);

      return new ResponseEntity<>(new ResponseDto<>("Email sent successfully!", null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }
}
