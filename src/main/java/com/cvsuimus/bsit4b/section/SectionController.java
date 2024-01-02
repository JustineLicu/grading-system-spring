package com.cvsuimus.bsit4b.section;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_ADMIN')")
@RequestMapping("/api/v1/subjects/{subjectId}/sections")
public class SectionController {

  @Autowired
  private SectionService sectionService;

  @GetMapping
  public ResponseEntity<List<SectionDto.Default>> getAll(
      @PathVariable("subjectId") Long subjectId) {
    return sectionService.getAll(subjectId);
  }

  @GetMapping("archived")
  public ResponseEntity<List<SectionDto.Default>> getAllArchived(
      @PathVariable("subjectId") Long subjectId) {
    return sectionService.getAllArchived(subjectId);
  }

  @GetMapping("{id}")
  public ResponseEntity<SectionDto.Default> getById(@PathVariable("id") Long id) {
    return sectionService.getById(id);
  }

  @PostMapping
  public ResponseEntity<SectionDto.Default> create(
      @PathVariable("subjectId") Long subjectId, @RequestBody Section item) {
    return sectionService.create(subjectId, item);
  }

  @PutMapping("{id}")
  public ResponseEntity<SectionDto.Default> update(
      @PathVariable("id") Long id, @RequestBody Section item) {
    return sectionService.update(id, item);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return sectionService.delete(id);
  }
}
