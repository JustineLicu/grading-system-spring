package com.cvsuimus.bsit4b.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cvsuimus.bsit4b.dto.ResponseDto;
import com.cvsuimus.bsit4b.dto.grade.*;
import com.cvsuimus.bsit4b.entity.Grade;
import com.cvsuimus.bsit4b.service.GradeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subject-sections/{subjectSectionId}/grades")
public class GradeController {

  @Autowired
  private GradeService gradeService;

  @GetMapping
  public ResponseEntity<ResponseDto<List<Grade>>> getAll(@PathVariable("subjectSectionId") Long subjectSectionId) {
    return gradeService.getAll(subjectSectionId);
  }

  @GetMapping("archived")
  public ResponseEntity<ResponseDto<List<Grade>>> getAllArchived(
      @PathVariable("subjectSectionId") Long subjectSectionId) {
    return gradeService.getAllArchived(subjectSectionId);
  }

  @GetMapping("{id}")
  public ResponseEntity<ResponseDto<Grade>> getById(@PathVariable("id") Long id) {
    return gradeService.getById(id);
  }

  @PostMapping
  public ResponseEntity<ResponseDto<Grade>> create(@PathVariable("subjectSectionId") Long subjectSectionId,
      @Valid @RequestBody CreateGradeDto item,
      BindingResult bindingResult) {
    return gradeService.create(subjectSectionId, item, bindingResult);
  }

  @PutMapping("{id}")
  public ResponseEntity<ResponseDto<Grade>> update(@PathVariable("subjectSectionId") Long subjectSectionId,
      @PathVariable("id") Long id,
      @Valid @RequestBody UpdateGradeDto item,
      BindingResult bindingResult) {
    return gradeService.update(subjectSectionId, id, item, bindingResult);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return gradeService.delete(id);
  }
}
