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
import com.cvsuimus.bsit4b.dto.course.*;
import com.cvsuimus.bsit4b.entity.Course;
import com.cvsuimus.bsit4b.repository.CourseRepository;

import jakarta.transaction.Transactional;

@Service
public class CourseService {

  @Autowired
  private CourseRepository courseRepository;

  public ResponseEntity<ResponseDto<List<Course>>> getAll() {
    try {
      List<Course> items = new ArrayList<Course>();

      courseRepository.findAll().forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<Course>> getById(Long id) {
    Optional<Course> existingItemOptional = courseRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(new ResponseDto<>(existingItemOptional.get(), null), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Course>> create(CreateCourseDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Course savedItem = courseRepository.persistAndFlush(new Course(item));
      return new ResponseEntity<>(new ResponseDto<>(savedItem, null), HttpStatus.CREATED);
    } catch (Exception e) {
      List<ErrorDto> errors = new ArrayList<ErrorDto>();
      errors.add(new ErrorDto("acronym", "Acronym is already in use"));

      return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Course>> update(Long id, UpdateCourseDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Course updatedItem = courseRepository.updateAndFlush(new Course(id, item));
      return new ResponseEntity<>(new ResponseDto<>(updatedItem, null), HttpStatus.OK);
    } catch (Exception e) {
      List<ErrorDto> errors = new ArrayList<ErrorDto>();
      errors.add(new ErrorDto("acronym", "Acronym is already in use"));

      return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
    }
  }

  public ResponseEntity<HttpStatus> delete(Long id) {
    try {
      courseRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
