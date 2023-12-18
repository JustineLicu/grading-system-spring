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
import com.cvsuimus.bsit4b.dto.department.*;
import com.cvsuimus.bsit4b.entity.Department;
import com.cvsuimus.bsit4b.repository.DepartmentRepository;

import jakarta.transaction.Transactional;

@Service
public class DepartmentService {

  @Autowired
  private DepartmentRepository departmentRepository;

  public ResponseEntity<ResponseDto<List<Department>>> getAll() {
    try {
      List<Department> items = new ArrayList<Department>();

      departmentRepository.findAll().forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<Department>> getById(Long id) {
    Optional<Department> existingItemOptional = departmentRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(new ResponseDto<>(existingItemOptional.get(), null), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Department>> create(CreateDepartmentDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Department savedItem = departmentRepository.persist(new Department(item));
      return new ResponseEntity<>(new ResponseDto<>(savedItem, null), HttpStatus.CREATED);
    } catch (Exception e) {
      List<ErrorDto> errors = new ArrayList<ErrorDto>();
      errors.add(new ErrorDto("name", "Name is already in use"));

      return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Department>> update(Long id, UpdateDepartmentDto item,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Department updatedItem = departmentRepository.update(new Department(id, item));
      return new ResponseEntity<>(new ResponseDto<>(updatedItem, null), HttpStatus.OK);
    } catch (Exception e) {
      List<ErrorDto> errors = new ArrayList<ErrorDto>();
      errors.add(new ErrorDto("name", "Name is already in use"));

      return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
    }
  }

  public ResponseEntity<HttpStatus> delete(Long id) {
    try {
      departmentRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
