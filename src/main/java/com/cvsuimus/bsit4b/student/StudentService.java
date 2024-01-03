package com.cvsuimus.bsit4b.student;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  @Autowired
  private StudentRepository studentRepository;

  public ResponseEntity<List<Student>> getAll() {
    try {
      List<Student> items = new ArrayList<Student>();

      studentRepository.findAll().forEach(items::add);

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<Student> getById(Long id) {
    Optional<Student> existingItemOptional = studentRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<Student> create(Student item) {
    Boolean studentNumberExists = studentRepository.findByStudentNumber(item.getStudentNumber())
        .isPresent();
    if (studentNumberExists) {
      throw new IllegalStateException("Student Number is already registered");
    }

    Boolean emailExists = studentRepository.findByEmail(item.getEmail()).isPresent();
    if (emailExists) {
      throw new IllegalStateException("Email is already taken");
    }

    Student savedItem = studentRepository.save(item);
    return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
  }

  public ResponseEntity<Student> update(Long id, Student item) {
    Optional<Student> existingItemOptional = studentRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      Student existingItem = existingItemOptional.get();

      if (item.getStudentNumber() != null &&
          !existingItem.getStudentNumber().equals(item.getStudentNumber())) {
        Boolean studentNumberExists = studentRepository.findByStudentNumber(item.getStudentNumber())
            .isPresent();
        if (studentNumberExists) {
          throw new IllegalStateException("Student Number is already registered");
        } else {
          existingItem.setStudentNumber(item.getStudentNumber());
        }
      }

      if (item.getEmail() != null && !existingItem.getEmail().equals(item.getEmail())) {
        Boolean emailExists = studentRepository.findByEmail(item.getEmail()).isPresent();
        if (emailExists) {
          throw new IllegalStateException("Email is already taken");
        } else {
          existingItem.setEmail(item.getEmail());
        }
      }

      if (item.getFirstName() != null) {
        existingItem.setFirstName(item.getFirstName());
      }

      if (item.getMiddleName() != null) {
        existingItem.setMiddleName(item.getMiddleName());
      }

      if (item.getLastName() != null) {
        existingItem.setLastName(item.getLastName());
      }

      if (item.getNameSuffix() != null) {
        existingItem.setNameSuffix(item.getNameSuffix());
      }

      if (item.getContactNumber() != null) {
        existingItem.setContactNumber(item.getContactNumber());
      }

      if (item.getCourse() != null) {
        existingItem.setCourse(item.getCourse());
      }

      return new ResponseEntity<>(studentRepository.save(existingItem), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<HttpStatus> delete(Long id) {
    try {
      studentRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
