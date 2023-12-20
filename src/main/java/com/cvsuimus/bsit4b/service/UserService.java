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
import com.cvsuimus.bsit4b.dto.user.*;
import com.cvsuimus.bsit4b.entity.Department;
import com.cvsuimus.bsit4b.entity.User;
import com.cvsuimus.bsit4b.repository.DepartmentRepository;
import com.cvsuimus.bsit4b.repository.UserRepository;
import com.cvsuimus.bsit4b.utility.MainUtility;

import jakarta.transaction.Transactional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private DepartmentRepository departmentRepository;

  public ResponseEntity<ResponseDto<List<User>>> getAll() {
    try {
      List<User> items = new ArrayList<User>();

      userRepository.findByDeletedOn("").forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<List<User>>> getAllArchived() {
    try {
      List<User> items = new ArrayList<User>();

      userRepository.findByDeletedOnNot("").forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<User>> getById(Long id) {
    Optional<User> existingItemOptional = userRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(new ResponseDto<>(existingItemOptional.get(), null), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<User>> create(CreateUserDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      List<User> existingItemOptional = userRepository.findByIdNumberOrEmailOrUsername(item.getIdNumber(),
          item.getEmail(), item.getUsername());

      if (!existingItemOptional.isEmpty()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("account", "Account is already registered"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      Department department = departmentRepository.getReferenceById(item.getDepartmentId());
      User savedItem = userRepository.persist(new User(item, department));
      return new ResponseEntity<>(new ResponseDto<>(savedItem.setDepartmentId(item.getDepartmentId()), null),
          HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<User>> update(Long id, UpdateUserDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      List<User> existingItemOptional = userRepository.findByIdNumberOrEmailOrUsernameAndIdNot(item.getIdNumber(),
          item.getEmail(), item.getUsername(), id);

      if (!existingItemOptional.isEmpty()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("account", "Account is already registered"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      Department department = null; // departmentRepository.getReferenceById(item.getDepartmentId());
      User existingItem = userRepository.findById(id).get();
      User updatedItem = userRepository.update(new User(id, item, department).setPassword(existingItem.getPassword()));
      return new ResponseEntity<>(new ResponseDto<>(updatedItem.setDepartmentId(item.getDepartmentId()), null),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
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
