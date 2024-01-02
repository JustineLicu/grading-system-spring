package com.cvsuimus.bsit4b.notify;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/notifications")
public class NotifyController {

  @Autowired
  private NotifyService notifyService;

  @GetMapping
  public ResponseEntity<List<NotifyDto.Default>> getAll(@PathVariable("userId") Long userId) {
    return notifyService.getAll(userId);
  }

  @GetMapping("{id}")
  public ResponseEntity<NotifyDto.Default> getById(@PathVariable("id") Long id) {
    return notifyService.getById(id);
  }

  @PostMapping
  public ResponseEntity<NotifyDto.Default> create(@PathVariable("userId") Long userId, @RequestBody Notify item) {
    return notifyService.create(userId, item);
  }

  @PutMapping("{id}")
  public ResponseEntity<NotifyDto.Default> update(
      @PathVariable("id") Long id, @RequestBody Notify item) {
    return notifyService.update(id, item);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return notifyService.delete(id);
  }
}
