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
import com.cvsuimus.bsit4b.dto.role.*;
import com.cvsuimus.bsit4b.entity.Role;
import com.cvsuimus.bsit4b.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class RoleService {

  @Autowired
  private RoleRepository roleRepository;

  public ResponseEntity<ResponseDto<List<Role>>> getAll() {
    try {
      List<Role> items = new ArrayList<Role>();

      roleRepository.findAll().forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<Role>> getById(String id) {
    Optional<Role> existingItemOptional = roleRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(new ResponseDto<>(existingItemOptional.get(), null), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Role>> create(CreateRoleDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Optional<Role> existingItemOptional = roleRepository.findById(item.getName());

      if (existingItemOptional.isPresent()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("name", "Role already exist"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      Role savedItem = roleRepository.persist(new Role(item));
      return new ResponseEntity<>(new ResponseDto<>(savedItem, null), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Role>> update(String id, UpdateRoleDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Role updatedItem = roleRepository.update(new Role(id, item));
      return new ResponseEntity<>(new ResponseDto<>(updatedItem, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  public ResponseEntity<HttpStatus> delete(String id) {
    try {
      roleRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
