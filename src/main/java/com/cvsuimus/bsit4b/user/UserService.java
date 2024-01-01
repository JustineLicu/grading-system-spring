package com.cvsuimus.bsit4b.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import com.cvsuimus.bsit4b.utility.MainUtility;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public ResponseEntity<List<UserDto.Default>> getAll() {
    try {
      List<UserDto.Default> items = new ArrayList<UserDto.Default>();

      userRepository.findByDeletedOn("").forEach(item -> items.add(item.toDefaultDto()));

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<List<UserDto.Default>> getAllArchived() {
    try {
      List<UserDto.Default> items = new ArrayList<UserDto.Default>();

      userRepository.findByDeletedOnNot("")
          .forEach(item -> items.add(item.toDefaultDto()));

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<UserDto.Default> getById(Long id) {
    Optional<User> existingItemOptional = userRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(existingItemOptional.get().toDefaultDto(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<HttpStatus> updateRole(Long id, User item) {
    Optional<User> existingItemOptional = userRepository.findById(id);
    if (existingItemOptional.isPresent()) {
      User existingItem = existingItemOptional.get();
      userRepository.save(existingItem.setRole(item.getRole()));

      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<HttpStatus> delete(Long id) {
    try {
      userRepository.setDeletedOnFor(MainUtility.getCurrentDateTimeInString(), id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
