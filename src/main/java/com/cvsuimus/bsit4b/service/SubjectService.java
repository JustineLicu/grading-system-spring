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
import com.cvsuimus.bsit4b.dto.subject.*;
import com.cvsuimus.bsit4b.entity.Subject;
import com.cvsuimus.bsit4b.entity.User;
import com.cvsuimus.bsit4b.repository.SubjectRepository;
import com.cvsuimus.bsit4b.repository.UserRepository;
import com.cvsuimus.bsit4b.utility.MainUtility;

import jakarta.transaction.Transactional;

@Service
public class SubjectService {

  @Autowired
  private SubjectRepository subjectRepository;

  @Autowired
  private UserRepository userRepository;

  public ResponseEntity<ResponseDto<List<Subject>>> getAll(Long userId) {
    try {
      List<Subject> items = new ArrayList<Subject>();

      subjectRepository.findByUserIdAndDeletedOn(userId, "").forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<List<Subject>>> getAllArchived(Long userId) {
    try {
      List<Subject> items = new ArrayList<Subject>();

      subjectRepository.findByUserIdAndDeletedOnNot(userId, "").forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<Subject>> getById(Long id) {
    Optional<Subject> existingItemOptional = subjectRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(new ResponseDto<>(existingItemOptional.get(), null), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Subject>> create(Long userId, CreateSubjectDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Optional<Subject> existingItemOptional = subjectRepository.findByCodeAndUserIdAndDeletedOn(item.getCode(), userId,
          "");

      if (existingItemOptional.isPresent()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("code", "Code is already in use"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      User user = userRepository.getReferenceById(userId);
      Subject savedItem = subjectRepository.persist(new Subject(item, user));
      return new ResponseEntity<>(new ResponseDto<>(savedItem.setUserId(userId), null), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Subject>> update(Long userId, Long id, UpdateSubjectDto item,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Optional<Subject> existingItemOptional = subjectRepository.findByCodeAndUserIdAndDeletedOnAndIdNot(item.getCode(),
          userId, "", id);

      if (existingItemOptional.isPresent()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("code", "Code is already in use"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      User user = userRepository.getReferenceById(userId);
      Subject updatedItem = subjectRepository.update(new Subject(id, item, user));
      return new ResponseEntity<>(new ResponseDto<>(updatedItem.setUserId(userId), null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  public ResponseEntity<HttpStatus> delete(Long id) {
    try {
      subjectRepository.setDeletedOnFor(MainUtility.getCurrentDateTimeInString(), id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
