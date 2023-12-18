package com.cvsuimus.bsit4b.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cvsuimus.bsit4b.dto.*;
import com.cvsuimus.bsit4b.dto.auth.*;
import com.cvsuimus.bsit4b.entity.User;
import com.cvsuimus.bsit4b.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("sign-up")
  public ResponseEntity<ResponseDto<User>> create(@Valid @RequestBody SignUpUserDto item,
      BindingResult bindingResult) {
    return authService.signUp(item, bindingResult);
  }
}
