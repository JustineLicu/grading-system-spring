package com.cvsuimus.bsit4b.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_ADMIN')")
@RequestMapping("/students")
public class StudentController {

  @Autowired
  private StudentService studentService;

  @GetMapping
  public ResponseEntity<List<Student>> getAll() {
    return studentService.getAll();
  }

  @GetMapping("{id}")
  public ResponseEntity<Student> getById(@PathVariable("id") Long id) {
    return studentService.getById(id);
  }

  @PostMapping
  public ResponseEntity<Student> create(@RequestBody Student item) {
    return studentService.create(item);
  }

  @PutMapping("{id}")
  public ResponseEntity<Student> update(
      @PathVariable("id") Long id, @RequestBody Student item) {
    return studentService.update(id, item);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return studentService.delete(id);
  }
}
