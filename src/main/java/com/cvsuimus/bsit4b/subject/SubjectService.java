package com.cvsuimus.bsit4b.subject;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cvsuimus.bsit4b.section.*;
import com.cvsuimus.bsit4b.user.User;
import com.cvsuimus.bsit4b.utility.MainUtility;

import jakarta.transaction.Transactional;

@Service
public class SubjectService {

  @Autowired
  private SubjectRepository subjectRepository;

  @Autowired
  private SectionRepository sectionRepository;

  public ResponseEntity<List<SubjectDto.Default>> getAll() {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();

    try {
      List<SubjectDto.Default> items = new ArrayList<SubjectDto.Default>();

      subjectRepository.findByUserIdAndDeletedOn(userId, "")
          .forEach(item -> items.add(item.toDefaultDto()));

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<List<SubjectDto.Default>> getAllArchived() {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();

    try {
      List<SubjectDto.Default> items = new ArrayList<SubjectDto.Default>();

      subjectRepository.findByUserIdAndDeletedOnNot(userId, "")
          .forEach(item -> items.add(item.toDefaultDto()));

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<SubjectDto.Default> getById(Long id) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();
    Optional<Subject> existingItemOptional = subjectRepository.findByIdAndUserId(id, userId);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(existingItemOptional.get().toDefaultDto(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  public ResponseEntity<SubjectDto.Default> create(Subject item) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Boolean codeExists = subjectRepository.findByCodeAndUserIdAndDeletedOn(
        item.getCode(), user.getId(), "").isPresent();
    if (codeExists) {
      throw new IllegalStateException("Code is already taken");
    }

    Subject savedItem = subjectRepository.save(item.setUser(user));
    List<Section> sections = new ArrayList<Section>();

    item.getSections().forEach(section -> sections.add(
        section.setSubject(savedItem).setUser(user)));

    sectionRepository.saveAll(sections);
    return new ResponseEntity<>(
        savedItem.setUserId(user.getId()).toDefaultDto(), HttpStatus.CREATED);
  }

  public ResponseEntity<SubjectDto.Default> update(Long id, Subject item) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();
    Optional<Subject> existingItemOptional = subjectRepository.findByIdAndUserId(id, userId);

    if (existingItemOptional.isPresent()) {
      Subject existingItem = existingItemOptional.get();

      if (item.getCode() != null && !existingItem.getCode().equals(item.getCode())) {
        if (existingItem.getDeletedOn().isEmpty()) {
          Boolean codeExists = subjectRepository.findByCodeAndUserIdAndDeletedOn(
              item.getCode(), userId, existingItem.getDeletedOn()).isPresent();
          if (codeExists) {
            throw new IllegalStateException("Code is already taken");
          }
        }

        existingItem.setCode(item.getCode());
      }

      if (item.getDescription() != null) {
        existingItem.setDescription(item.getDescription());
      }

      return new ResponseEntity<>(
          subjectRepository.save(existingItem).toDefaultDto(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<HttpStatus> delete(Long id) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();

    try {
      subjectRepository.setDeletedOnFor(MainUtility.getCurrentDateTimeInString(), id, userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
