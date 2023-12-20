package com.cvsuimus.bsit4b.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cvsuimus.bsit4b.dto.ResponseDto;
import com.cvsuimus.bsit4b.dto.student.*;
import com.cvsuimus.bsit4b.entity.Student;
import com.cvsuimus.bsit4b.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

  @Autowired
  private StudentService studentService;

  @GetMapping
  public ResponseEntity<ResponseDto<List<Student>>> getAll() {
    return studentService.getAll();
  }

  @GetMapping("{id}")
  public ResponseEntity<ResponseDto<Student>> getById(@PathVariable("id") Long id) {
    return studentService.getById(id);
  }

  @PostMapping
  public ResponseEntity<ResponseDto<Student>> create(@Valid @RequestBody CreateStudentDto item,
      BindingResult bindingResult) {
    return studentService.create(item, bindingResult);
  }

  @PutMapping("{id}")
  public ResponseEntity<ResponseDto<Student>> update(@PathVariable("id") Long id,
      @Valid @RequestBody UpdateStudentDto item,
      BindingResult bindingResult) {
    return studentService.update(id, item, bindingResult);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return studentService.delete(id);
  }
}
