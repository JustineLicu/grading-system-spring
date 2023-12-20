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
import com.cvsuimus.bsit4b.dto.student.*;
import com.cvsuimus.bsit4b.entity.Course;
import com.cvsuimus.bsit4b.entity.Student;
import com.cvsuimus.bsit4b.repository.CourseRepository;
import com.cvsuimus.bsit4b.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CourseRepository courseRepository;

  public ResponseEntity<ResponseDto<List<Student>>> getAll() {
    try {
      List<Student> items = new ArrayList<Student>();

      studentRepository.findAll().forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<Student>> getById(Long id) {
    Optional<Student> existingItemOptional = studentRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(new ResponseDto<>(existingItemOptional.get(), null), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Student>> create(CreateStudentDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      List<Student> existingItems = studentRepository.findByStudentNumberOrEmail(item.getStudentNumber(),
          item.getEmail());

      if (!existingItems.isEmpty()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("studentNumber", "Student Number or Email is already in use"));
        errors.add(new ErrorDto("email", "Email or Student Number is already in use"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      Course course = courseRepository.getReferenceById(item.getCourseId());
      Student savedItem = studentRepository.persist(new Student(item, course));
      return new ResponseEntity<>(new ResponseDto<>(savedItem.setCourseId(item.getCourseId()), null),
          HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Student>> update(Long id, UpdateStudentDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      List<Student> existingItems = studentRepository.findByStudentNumberOrEmailAndIdNot(item.getStudentNumber(),
          item.getEmail(), id);

      if (!existingItems.isEmpty()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("studentNumber", "Student Number or Email is already in use"));
        errors.add(new ErrorDto("email", "Email or Student Number is already in use"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      Course course = courseRepository.getReferenceById(item.getCourseId());
      Student updatedItem = studentRepository.update(new Student(id, item, course));
      return new ResponseEntity<>(new ResponseDto<>(updatedItem.setCourseId(item.getCourseId()), null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
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
