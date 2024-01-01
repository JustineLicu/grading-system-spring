package com.cvsuimus.bsit4b.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/v1/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<List<UserDto.Default>> getAll() {
    return userService.getAll();
  }

  @GetMapping("archived")
  public ResponseEntity<List<UserDto.Default>> getAllArchived() {
    return userService.getAllArchived();
  }

  @GetMapping("{id}")
  public ResponseEntity<UserDto.Default> getById(@PathVariable("id") Long id) {
    return userService.getById(id);
  }

  @PutMapping("{id}/role")
  public ResponseEntity<HttpStatus> updateRole(
      @PathVariable("id") Long id, @RequestBody User item) {
    return userService.updateRole(id, item);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return userService.delete(id);
  }
}
