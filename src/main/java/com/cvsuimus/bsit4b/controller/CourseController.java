package com.cvsuimus.bsit4b.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cvsuimus.bsit4b.dto.ResponseDto;
import com.cvsuimus.bsit4b.dto.course.*;
import com.cvsuimus.bsit4b.entity.Course;
import com.cvsuimus.bsit4b.service.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/courses")
public class CourseController {

  @Autowired
  private CourseService courseService;

  @GetMapping
  public ResponseEntity<ResponseDto<List<Course>>> getAll() {
    return courseService.getAll();
  }

  @GetMapping("{id}")
  public ResponseEntity<ResponseDto<Course>> getById(@PathVariable("id") Long id) {
    return courseService.getById(id);
  }

  @PostMapping
  public ResponseEntity<ResponseDto<Course>> create(@Valid @RequestBody CreateCourseDto item,
      BindingResult bindingResult) {
    return courseService.create(item, bindingResult);
  }

  @PutMapping("{id}")
  public ResponseEntity<ResponseDto<Course>> update(@PathVariable("id") Long id,
      @Valid @RequestBody UpdateCourseDto item,
      BindingResult bindingResult) {
    return courseService.update(id, item, bindingResult);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return courseService.delete(id);
  }
}
