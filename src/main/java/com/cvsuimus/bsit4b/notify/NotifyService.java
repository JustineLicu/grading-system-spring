package com.cvsuimus.bsit4b.notify;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
public class NotifyService {

  @Autowired
  private NotifyRepository notifyRepository;

  public ResponseEntity<List<NotifyDto.Default>> getAll(Long userId) {
    try {
      List<NotifyDto.Default> items = new ArrayList<NotifyDto.Default>();

      notifyRepository.findByUserId(userId).forEach(item -> items.add(item.toDefaultDto()));

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<NotifyDto.Default> getById(Long id) {
    Optional<Notify> existingItemOptional = notifyRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(existingItemOptional.get().toDefaultDto(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<NotifyDto.Default> create(Long userId, Notify item) {
    try {
      Notify savedItem = notifyRepository.save(item.setUserId(userId));
      return new ResponseEntity<>(savedItem.toDefaultDto(), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  public ResponseEntity<NotifyDto.Default> update(Long id, Notify item) {
    Optional<Notify> existingItemOptional = notifyRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      Notify existingItem = existingItemOptional.get();

      if (item.getSubjectCode() != null) {
        existingItem.setSubjectCode(item.getSubjectCode());
      }

      if (item.getMessage() != null) {
        existingItem.setMessage(item.getMessage());
      }

      if (item.getFullName() != null) {
        existingItem.setFullName(item.getFullName());
      }

      if (item.getEmail() != null) {
        existingItem.setEmail(item.getEmail());
      }

      if (item.getStudentId() != null) {
        existingItem.setStudentId(item.getStudentId());
      }

      return new ResponseEntity<>(
          notifyRepository.save(existingItem).toDefaultDto(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
