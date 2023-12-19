package com.cvsuimus.bsit4b.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cvsuimus.bsit4b.dto.ResponseDto;
import com.cvsuimus.bsit4b.dto.subjectsection.*;
import com.cvsuimus.bsit4b.entity.SubjectSection;
import com.cvsuimus.bsit4b.service.SubjectSectionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subjectSections")
public class SubjectSectionController {

  @Autowired
  private SubjectSectionService subjectSectionService;

  @GetMapping
  public ResponseEntity<ResponseDto<List<SubjectSection>>> getAll() {
    return subjectSectionService.getAll();
  }

  @GetMapping("{id}")
  public ResponseEntity<ResponseDto<SubjectSection>> getById(@PathVariable("id") Long id) {
    return subjectSectionService.getById(id);
  }

  @PostMapping
  public ResponseEntity<ResponseDto<SubjectSection>> create(@Valid @RequestBody CreateSubjectSectionDto item,
      BindingResult bindingResult) {
    return subjectSectionService.create(item, bindingResult);
  }

  @PutMapping("{id}")
  public ResponseEntity<ResponseDto<SubjectSection>> update(@PathVariable("id") Long id,
      @Valid @RequestBody UpdateSubjectSectionDto item,
      BindingResult bindingResult) {
    return subjectSectionService.update(id, item, bindingResult);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return subjectSectionService.delete(id);
  }
}
