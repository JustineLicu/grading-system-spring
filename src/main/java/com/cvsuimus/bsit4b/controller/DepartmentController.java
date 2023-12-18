package com.cvsuimus.bsit4b.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cvsuimus.bsit4b.dto.ResponseDto;
import com.cvsuimus.bsit4b.dto.department.*;
import com.cvsuimus.bsit4b.entity.Department;
import com.cvsuimus.bsit4b.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

  @Autowired
  private DepartmentService departmentService;

  @GetMapping
  public ResponseEntity<ResponseDto<List<Department>>> getAll() {
    return departmentService.getAll();
  }

  @GetMapping("{id}")
  public ResponseEntity<ResponseDto<Department>> getById(@PathVariable("id") Long id) {
    return departmentService.getById(id);
  }

  @PostMapping
  public ResponseEntity<ResponseDto<Department>> create(@Valid @RequestBody CreateDepartmentDto item,
      BindingResult bindingResult) {
    return departmentService.create(item, bindingResult);
  }

  @PutMapping("{id}")
  public ResponseEntity<ResponseDto<Department>> update(@PathVariable("id") Long id,
      @Valid @RequestBody UpdateDepartmentDto item,
      BindingResult bindingResult) {
    return departmentService.update(id, item, bindingResult);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return departmentService.delete(id);
  }
}
