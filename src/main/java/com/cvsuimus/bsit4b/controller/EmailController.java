package com.cvsuimus.bsit4b.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cvsuimus.bsit4b.dto.*;
import com.cvsuimus.bsit4b.dto.email.*;
import com.cvsuimus.bsit4b.service.EmailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/email")
public class EmailController {

  @Autowired
  private EmailService emailService;

  @PostMapping
  public ResponseEntity<ResponseDto<String>> sendSimpleMessage(@Valid @RequestBody SendEmailDto item,
      BindingResult bindingResult) {
    return emailService.sendSimpleMessage(item, bindingResult);
  }
}
