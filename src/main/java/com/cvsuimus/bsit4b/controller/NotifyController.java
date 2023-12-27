package com.cvsuimus.bsit4b.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cvsuimus.bsit4b.dto.ResponseDto;
import com.cvsuimus.bsit4b.dto.notify.*;
import com.cvsuimus.bsit4b.entity.Notify;
import com.cvsuimus.bsit4b.service.NotifyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/notifications")
public class NotifyController {

  @Autowired
  private NotifyService notifyService;

  @GetMapping
  public ResponseEntity<ResponseDto<List<Notify>>> getAll() {
    return notifyService.getAll();
  }

  @GetMapping("{id}")
  public ResponseEntity<ResponseDto<Notify>> getById(@PathVariable("id") Long id) {
    return notifyService.getById(id);
  }

  @PostMapping
  public ResponseEntity<ResponseDto<Notify>> create(@Valid @RequestBody CreateNotifyDto item,
      BindingResult bindingResult) {
    return notifyService.create(item, bindingResult);
  }

  @PutMapping("{id}")
  public ResponseEntity<ResponseDto<Notify>> update(@PathVariable("id") Long id,
      @Valid @RequestBody UpdateNotifyDto item,
      BindingResult bindingResult) {
    return notifyService.update(id, item, bindingResult);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return notifyService.delete(id);
  }
}
