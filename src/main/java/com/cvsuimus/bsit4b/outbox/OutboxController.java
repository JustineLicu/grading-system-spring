package com.cvsuimus.bsit4b.outbox;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_ADMIN')")
@RequestMapping("/api/v1/outbox")
public class OutboxController {

  @Autowired
  private OutboxService outboxService;

  @GetMapping
  public ResponseEntity<List<OutboxDto.FetchStudent>> getAll() {
    return outboxService.getAll();
  }

  @PostMapping
  public ResponseEntity<HttpStatus> createMany(@RequestBody OutboxDto.CreateMany items) {
    return outboxService.createMany(items);
  }

  @PostMapping("send")
  public ResponseEntity<HttpStatus> sendAll() {
    return outboxService.sendAll();
  }

  @PostMapping("send/{id}")
  public ResponseEntity<HttpStatus> send(@PathVariable("id") Long id) {
    return outboxService.send(id);
  }

  @DeleteMapping
  public ResponseEntity<HttpStatus> deleteAll() {
    return outboxService.deleteAll();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return outboxService.delete(id);
  }
}
