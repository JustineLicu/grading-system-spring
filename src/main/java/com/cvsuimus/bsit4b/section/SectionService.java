package com.cvsuimus.bsit4b.section;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cvsuimus.bsit4b.subject.*;
import com.cvsuimus.bsit4b.user.User;
import com.cvsuimus.bsit4b.utility.MainUtility;

@Service
public class SectionService {

  @Autowired
  private SectionRepository sectionRepository;

  @Autowired
  private SubjectRepository subjectRepository;

  public ResponseEntity<List<SectionDto.Default>> getAll(Long subjectId) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();

    try {
      List<SectionDto.Default> items = new ArrayList<SectionDto.Default>();

      sectionRepository.findBySubjectIdAndUserIdAndDeletedOn(subjectId, userId, "")
          .forEach(item -> items.add(item.toDefaultDto()));

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<List<SectionDto.Default>> getAllArchived(Long subjectId) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();

    try {
      List<SectionDto.Default> items = new ArrayList<SectionDto.Default>();

      sectionRepository.findBySubjectIdAndUserIdAndDeletedOnNot(subjectId, userId, "")
          .forEach(item -> items.add(item.toDefaultDto()));

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<SectionDto.Default> getById(Long id) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();
    Optional<Section> existingItemOptional = sectionRepository.findByIdAndUserId(id, userId);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(existingItemOptional.get().toDefaultDto(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<SectionDto.Default> create(Long subjectId, Section item) {
    Boolean sectionExists = sectionRepository.findByCourseAndYearAndNameAndSubjectIdAndDeletedOn(
        item.getCourse(), item.getYear(), item.getName(), subjectId, "").isPresent();
    if (sectionExists) {
      throw new IllegalStateException("Section is already added");
    }

    Subject subject = subjectRepository.getReferenceById(subjectId);
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Section savedItem = sectionRepository.save(item.setSubject(subject).setUser(user));
    return new ResponseEntity<>(
        savedItem.setSubjectId(subjectId).setUserId(user.getId()).toDefaultDto(),
        HttpStatus.CREATED);
  }

  public ResponseEntity<SectionDto.Default> update(Long id, Section item) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();
    Optional<Section> existingItemOptional = sectionRepository.findByIdAndUserId(id, userId);

    if (existingItemOptional.isPresent()) {
      Section existingItem = existingItemOptional.get();

      if (item.getCourse() != null) {
        existingItem.setCourse(item.getCourse());
      }

      if (item.getYear() != null) {
        existingItem.setYear(item.getYear());
      }

      if (item.getName() != null) {
        existingItem.setName(item.getName());
      }

      if (existingItem.getDeletedOn().isEmpty()) {
        Optional<Section> existingSectionOptional = sectionRepository
            .findByCourseAndYearAndNameAndSubjectIdAndDeletedOn(
                existingItem.getCourse(),
                existingItem.getYear(),
                existingItem.getName(),
                existingItem.getSubjectId(),
                existingItem.getDeletedOn());
        if (existingSectionOptional.isPresent() &&
            !existingSectionOptional.get().getId().equals(existingItem.getId())) {
          throw new IllegalStateException("Section is already added");
        }
      }

      if (item.getGradeColumns() != null) {
        existingItem.setGradeColumns(item.getGradeColumns());
      }

      return new ResponseEntity<>(
          sectionRepository.save(existingItem).toDefaultDto(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<HttpStatus> delete(Long id) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();

    try {
      sectionRepository.setDeletedOnFor(MainUtility.getCurrentDateTimeInString(), id, userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
