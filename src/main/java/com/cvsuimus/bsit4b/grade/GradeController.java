package com.cvsuimus.bsit4b.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_ADMIN')")
@RequestMapping("/grades")
public class GradeController {

  @Autowired
  private GradeService gradeService;

  @GetMapping
  public ResponseEntity<?> getAll(
      @RequestParam(name = "sectionId", required = false) Long sectionId,
      @RequestParam(name = "studentId", required = false) Long studentId) {
    return gradeService.getAll(sectionId, studentId);
  }

  @GetMapping("archived")
  public ResponseEntity<?> getAllArchived(
      @RequestParam(name = "sectionId", required = false) Long sectionId,
      @RequestParam(name = "studentId", required = false) Long studentId) {
    return gradeService.getAllArchived(sectionId, studentId);
  }

  @GetMapping("{id}")
  public ResponseEntity<GradeDto.FetchSectionAndStudent> getById(@PathVariable("id") Long id) {
    return gradeService.getById(id);
  }

  @PostMapping
  public ResponseEntity<GradeDto.Default> create(@RequestBody Grade item) {
    return gradeService.create(item);
  }

  @PutMapping("{id}")
  public ResponseEntity<GradeDto.Default> update(
      @PathVariable("id") Long id, @RequestBody Grade item) {
    return gradeService.update(id, item);
  }

  @PutMapping
  public ResponseEntity<HttpStatus> updateMany(@RequestBody GradeDto.UpdateMany items) {
    return gradeService.updateMany(items);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return gradeService.delete(id);
  }
}
