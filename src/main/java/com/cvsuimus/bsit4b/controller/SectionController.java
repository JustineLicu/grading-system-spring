package com.cvsuimus.bsit4b.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cvsuimus.bsit4b.dto.ResponseDto;
import com.cvsuimus.bsit4b.dto.section.*;
import com.cvsuimus.bsit4b.entity.Section;
import com.cvsuimus.bsit4b.service.SectionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sections")
public class SectionController {

  @Autowired
  private SectionService sectionService;

  @GetMapping
  public ResponseEntity<ResponseDto<List<Section>>> getAll() {
    return sectionService.getAll();
  }

  @GetMapping("{id}")
  public ResponseEntity<ResponseDto<Section>> getById(@PathVariable("id") Long id) {
    return sectionService.getById(id);
  }

  @PostMapping
  public ResponseEntity<ResponseDto<Section>> create(@Valid @RequestBody CreateSectionDto item,
      BindingResult bindingResult) {
    return sectionService.create(item, bindingResult);
  }

  @PutMapping("{id}")
  public ResponseEntity<ResponseDto<Section>> update(@PathVariable("id") Long id,
      @Valid @RequestBody UpdateSectionDto item,
      BindingResult bindingResult) {
    return sectionService.update(id, item, bindingResult);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return sectionService.delete(id);
  }
}
