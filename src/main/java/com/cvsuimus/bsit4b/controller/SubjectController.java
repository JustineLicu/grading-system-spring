package com.cvsuimus.bsit4b.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cvsuimus.bsit4b.dto.ResponseDto;
import com.cvsuimus.bsit4b.dto.subject.*;
import com.cvsuimus.bsit4b.entity.Subject;
import com.cvsuimus.bsit4b.service.SubjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

  @Autowired
  private SubjectService subjectService;

  @GetMapping
  public ResponseEntity<ResponseDto<List<Subject>>> getAll() {
    return subjectService.getAll();
  }

  @GetMapping("{id}")
  public ResponseEntity<ResponseDto<Subject>> getById(@PathVariable("id") Long id) {
    return subjectService.getById(id);
  }

  @PostMapping
  public ResponseEntity<ResponseDto<Subject>> create(@Valid @RequestBody CreateSubjectDto item,
      BindingResult bindingResult) {
    return subjectService.create(item, bindingResult);
  }

  @PutMapping("{id}")
  public ResponseEntity<ResponseDto<Subject>> update(@PathVariable("id") Long id,
      @Valid @RequestBody UpdateSubjectDto item,
      BindingResult bindingResult) {
    return subjectService.update(id, item, bindingResult);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return subjectService.delete(id);
  }
}
