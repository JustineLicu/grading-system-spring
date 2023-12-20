package com.cvsuimus.bsit4b.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cvsuimus.bsit4b.dto.ResponseDto;
import com.cvsuimus.bsit4b.dto.user.*;
import com.cvsuimus.bsit4b.entity.User;
import com.cvsuimus.bsit4b.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<ResponseDto<List<User>>> getAll() {
    return userService.getAll();
  }

  @GetMapping("archived")
  public ResponseEntity<ResponseDto<List<User>>> getAllArchived() {
    return userService.getAllArchived();
  }

  @GetMapping("{id}")
  public ResponseEntity<ResponseDto<User>> getById(@PathVariable("id") Long id) {
    return userService.getById(id);
  }

  @PostMapping
  public ResponseEntity<ResponseDto<User>> create(@Valid @RequestBody CreateUserDto item,
      BindingResult bindingResult) {
    return userService.create(item, bindingResult);
  }

  @PutMapping("{id}")
  public ResponseEntity<ResponseDto<User>> update(@PathVariable("id") Long id,
      @Valid @RequestBody UpdateUserDto item,
      BindingResult bindingResult) {
    return userService.update(id, item, bindingResult);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return userService.delete(id);
  }
}
