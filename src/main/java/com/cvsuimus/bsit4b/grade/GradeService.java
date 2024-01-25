package com.cvsuimus.bsit4b.grade;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cvsuimus.bsit4b.section.*;
import com.cvsuimus.bsit4b.student.*;
import com.cvsuimus.bsit4b.user.User;
import com.cvsuimus.bsit4b.utility.MainUtility;

@Service
public class GradeService {

  @Autowired
  private GradeRepository gradeRepository;

  @Autowired
  private SectionRepository sectionRepository;

  @Autowired
  private StudentRepository studentRepository;

  public ResponseEntity<?> getAll(Long sectionId, Long studentId) {
    if (sectionId != null && studentId == null) {
      return getAllBySectionId(sectionId);
    } else if (sectionId == null && studentId != null) {
      return getAllByStudentId(studentId);
    } else {
      throw new IllegalStateException("sectionId or studentId is invalid");
    }
  }

  public ResponseEntity<?> getAllArchived(Long sectionId, Long studentId) {
    if (sectionId != null && studentId == null) {
      return getAllArchivedBySectionId(sectionId);
    } else if (sectionId == null && studentId != null) {
      return getAllArchivedByStudentId(studentId);
    } else {
      throw new IllegalStateException("sectionId or studentId is invalid");
    }
  }

  public ResponseEntity<List<GradeDto.FetchStudent>> getAllBySectionId(Long sectionId) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();

    try {
      List<GradeDto.FetchStudent> items = new ArrayList<GradeDto.FetchStudent>();

      gradeRepository.findBySectionIdAndUserIdAndDeletedOn(sectionId, userId, "")
          .forEach(item -> items.add(item.toFetchStudentDto()));

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<List<GradeDto.FetchStudent>> getAllArchivedBySectionId(Long sectionId) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();

    try {
      List<GradeDto.FetchStudent> items = new ArrayList<GradeDto.FetchStudent>();

      gradeRepository.findBySectionIdAndUserIdAndDeletedOnNot(sectionId, userId, "")
          .forEach(item -> items.add(item.toFetchStudentDto()));

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<List<GradeDto.FetchSection>> getAllByStudentId(Long studentId) {
    try {
      List<GradeDto.FetchSection> items = new ArrayList<GradeDto.FetchSection>();

      gradeRepository.findByStudentIdAndDeletedOn(studentId, "")
          .forEach(item -> items.add(item.toFetchSectionDto()));

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<List<GradeDto.FetchSection>> getAllArchivedByStudentId(Long studentId) {
    try {
      List<GradeDto.FetchSection> items = new ArrayList<GradeDto.FetchSection>();

      gradeRepository.findByStudentIdAndDeletedOnNot(studentId, "")
          .forEach(item -> items.add(item.toFetchSectionDto()));

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<GradeDto.FetchSectionAndStudent> getById(Long id) {
    Optional<Grade> existingItemOptional = gradeRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(
          existingItemOptional.get().toFetchSectionAndStudentDto(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<GradeDto.Default> create(Grade item) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Boolean isSectionCreatable = sectionRepository.findByIdAndUserId(
        item.getSectionId(), user.getId()).isPresent();
    if (!isSectionCreatable) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    // Boolean studentExists =
    // gradeRepository.findBySectionIdAndStudentIdAndDeletedOn(
    // item.getSectionId(), item.getStudentId(), "").isPresent();
    // if (studentExists) {
    // throw new IllegalStateException("Student is already added");
    // }

    Section section = sectionRepository.getReferenceById(item.getSectionId());
    Student student = studentRepository.getReferenceById(item.getStudentId());
    Grade savedItem = gradeRepository.save(
        item.setSection(section).setStudent(student).setUser(user));
    return new ResponseEntity<>(
        savedItem.setUserId(user.getId()).toDefaultDto(), HttpStatus.CREATED);
  }

  public ResponseEntity<GradeDto.Default> update(Long id, Grade item) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();
    Optional<Grade> existingItemOptional = gradeRepository.findByIdAndUserId(id, userId);

    if (existingItemOptional.isPresent()) {
      Grade existingItem = existingItemOptional.get();

      if (item.getStudentId() != null && !existingItem.getStudentId().equals(item.getStudentId())) {
        // if (existingItem.getDeletedOn().isEmpty()) {
        // Boolean studentExists =
        // gradeRepository.findBySectionIdAndStudentIdAndDeletedOn(
        // item.getSectionId(), item.getStudentId(), "").isPresent();
        // if (studentExists) {
        // throw new IllegalStateException("Student is already added");
        // }
        // }

        Student student = studentRepository.getReferenceById(item.getStudentId());
        existingItem.setStudent(student).setStudentId(item.getStudentId());
      }

      if (item.getGradeRows() != null) {
        existingItem.setGradeRows(item.getGradeRows());
      }

      if (item.getAttendances() != null) {
        existingItem.setAttendances(item.getAttendances());
      }

      return new ResponseEntity<>(gradeRepository.save(existingItem).toDefaultDto(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<HttpStatus> updateMany(GradeDto.UpdateMany items) {
    items.grades().forEach(item -> update(item.getId(), item));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<HttpStatus> delete(Long id) {
    Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();

    try {
      gradeRepository.setDeletedOnFor(MainUtility.getCurrentDateTimeInString(), id, userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
