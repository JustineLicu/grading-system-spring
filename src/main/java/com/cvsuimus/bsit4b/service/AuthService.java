package com.cvsuimus.bsit4b.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.cvsuimus.bsit4b.dto.*;
import com.cvsuimus.bsit4b.dto.auth.*;
import com.cvsuimus.bsit4b.entity.User;
import com.cvsuimus.bsit4b.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Transactional
  public ResponseEntity<ResponseDto<User>> signUp(SignUpUserDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      List<User> existingItems = userRepository.findByIdNumberOrEmailOrUsername(item.getIdNumber(),
          item.getEmail(), item.getUsername());

      if (!existingItems.isEmpty()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("account", "Account is already registered"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      // TODO: password hashing
      User savedItem = userRepository.persist(new User(item));
      return new ResponseEntity<>(new ResponseDto<>(savedItem, null), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  public ResponseEntity<ResponseDto<User>> signIn(SignInUserDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Optional<User> existingItemOptional = userRepository.findByUsernameAndPassword(item.getUsername(),
          item.getPassword());

      if (!existingItemOptional.isPresent()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("username", "Username or Password is incorrect"));
        errors.add(new ErrorDto("password", "Username or Password is incorrect"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      return new ResponseEntity<>(new ResponseDto<>(existingItemOptional.get(), null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  public ResponseEntity<ResponseDto<String>> signOut() {
    return new ResponseEntity<>(new ResponseDto<>("Sign out successfully!", null), HttpStatus.OK);
  }
}
