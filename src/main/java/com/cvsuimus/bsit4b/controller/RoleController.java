package com.cvsuimus.bsit4b.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cvsuimus.bsit4b.dto.ResponseDto;
import com.cvsuimus.bsit4b.dto.role.*;
import com.cvsuimus.bsit4b.entity.Role;
import com.cvsuimus.bsit4b.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/roles")
public class RoleController {

  @Autowired
  private RoleService roleService;

  @GetMapping
  public ResponseEntity<ResponseDto<List<Role>>> getAll() {
    return roleService.getAll();
  }

  @GetMapping("{id}")
  public ResponseEntity<ResponseDto<Role>> getById(@PathVariable("id") String id) {
    return roleService.getById(id);
  }

  @PostMapping
  public ResponseEntity<ResponseDto<Role>> create(@Valid @RequestBody CreateRoleDto item,
      BindingResult bindingResult) {
    return roleService.create(item, bindingResult);
  }

  @PutMapping("{id}")
  public ResponseEntity<ResponseDto<Role>> update(@PathVariable("id") String id,
      @Valid @RequestBody UpdateRoleDto item,
      BindingResult bindingResult) {
    return roleService.update(id, item, bindingResult);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
    return roleService.delete(id);
  }
}
