package com.cvsuimus.bsit4b.subject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_ADMIN')")
@RequestMapping("/api/v1/subjects")
public class SubjectController {

  @Autowired
  private SubjectService subjectService;

  @GetMapping
  public ResponseEntity<List<SubjectDto.Default>> getAll() {
    return subjectService.getAll();
  }

  @GetMapping("archived")
  public ResponseEntity<List<SubjectDto.Default>> getAllArchived() {
    return subjectService.getAllArchived();
  }

  @GetMapping("{id}")
  public ResponseEntity<SubjectDto.Default> getById(@PathVariable("id") Long id) {
    return subjectService.getById(id);
  }

  @PostMapping
  public ResponseEntity<SubjectDto.Default> create(@RequestBody Subject item) {
    return subjectService.create(item);
  }

  @PutMapping("{id}")
  public ResponseEntity<SubjectDto.Default> update(
      @PathVariable("id") Long id, @RequestBody Subject item) {
    return subjectService.update(id, item);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return subjectService.delete(id);
  }
}
