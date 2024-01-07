package com.cvsuimus.bsit4b.outbox;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cvsuimus.bsit4b.email.EmailService;
import com.cvsuimus.bsit4b.section.*;
import com.cvsuimus.bsit4b.student.*;
import com.cvsuimus.bsit4b.user.User;

@Service
public class OutboxService {

  @Autowired
  private OutboxRepository outboxRepository;

  @Autowired
  private SectionRepository sectionRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private EmailService emailService;

  public ResponseEntity<List<OutboxDto.FetchStudent>> getAll() {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();

    try {
      List<OutboxDto.FetchStudent> items = new ArrayList<OutboxDto.FetchStudent>();

      outboxRepository.findByUserId(userId).forEach(item -> items.add(item.toFetchStudentDto()));

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<HttpStatus> createMany(OutboxDto.CreateMany items) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Boolean isSectionCreatable = sectionRepository.findByIdAndUserId(
        items.sectionId(), user.getId()).isPresent();
    if (!isSectionCreatable) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    outboxRepository.deleteBySectionId(items.sectionId());
    List<Outbox> mappedItems = new ArrayList<Outbox>();
    Section section = sectionRepository.getReferenceById(items.sectionId());

    items.outbox().forEach(item -> {
      Student student = studentRepository.getReferenceById(item.getStudentId());
      mappedItems.add(item.setSection(section).setStudent(student).setUser(user));
    });

    outboxRepository.saveAll(mappedItems);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  public ResponseEntity<HttpStatus> sendAll() {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();

    try {
      List<Long> sentIds = new ArrayList<Long>();

      outboxRepository.findByUserId(userId).forEach(item -> {
        Boolean isSuccess = emailService.send(
            item.getStudent().getEmail(), item.getEmailSubject(), item.getMessage());
        if (isSuccess) {
          sentIds.add(item.getId());
        }
      });

      outboxRepository.deleteAllByIdInBatch(sentIds);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<HttpStatus> send(Long id) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();
    Optional<Outbox> existingItemOptional = outboxRepository.findByIdAndUserId(id, userId);

    if (existingItemOptional.isPresent()) {
      Outbox existingItem = existingItemOptional.get();

      Boolean isSuccess = emailService.send(
          existingItem.getStudent().getEmail(),
          existingItem.getEmailSubject(),
          existingItem.getMessage());

      if (!isSuccess) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }

      outboxRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<HttpStatus> deleteAll() {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();

    try {
      outboxRepository.deleteByUserId(userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }

  public ResponseEntity<HttpStatus> delete(Long id) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();

    try {
      outboxRepository.deleteByIdAndUserId(id, userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
