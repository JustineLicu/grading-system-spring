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
import com.cvsuimus.bsit4b.dto.notify.*;
import com.cvsuimus.bsit4b.dto.role.UpdateRoleDto;
import com.cvsuimus.bsit4b.entity.Notify;
import com.cvsuimus.bsit4b.entity.Role;
import com.cvsuimus.bsit4b.repository.NotifyRepository;

import jakarta.transaction.Transactional;

@Service
public class NotifyService {

  @Autowired
  private NotifyRepository notifyRepository;

  public ResponseEntity<ResponseDto<List<Notify>>> getAll() {
    try {
      List<Notify> items = new ArrayList<Notify>();

      notifyRepository.findAll().forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<Notify>> getById(Long id) {
    Optional<Notify> existingItemOptional = notifyRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(new ResponseDto<>(existingItemOptional.get(), null), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Notify>> create(CreateNotifyDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Optional<Notify> existingItemOptional = notifyRepository.findById(item.getName());

      if (existingItemOptional.isPresent()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("message", "Message already exists"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      Notify savedItem = notifyRepository.persist(new Notify(item));
      return new ResponseEntity<>(new ResponseDto<>(savedItem, null), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Notify>> update(Long id, UpdateNotifyDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Notify updatedItem = notifyRepository.update(new Notify(id, item));
      return new ResponseEntity<>(new ResponseDto<>(updatedItem, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  public ResponseEntity<HttpStatus> delete(Long id) {
    try {
      notifyRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
