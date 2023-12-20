package com.cvsuimus.bsit4b.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.cvsuimus.bsit4b.dto.*;
import com.cvsuimus.bsit4b.dto.grade.*;
import com.cvsuimus.bsit4b.entity.*;
import com.cvsuimus.bsit4b.repository.*;
import com.cvsuimus.bsit4b.utility.MainUtility;

import jakarta.transaction.Transactional;

@Service
public class GradeService {

  @Autowired
  private GradeRepository gradeRepository;

  @Autowired
  private SubjectSectionRepository subjectSectionRepository;

  @Autowired
  private StudentRepository studentRepository;

  public ResponseEntity<ResponseDto<List<Grade>>> getAll(Long subjectSectionId) {
    try {
      List<Grade> items = new ArrayList<Grade>();

      gradeRepository.findBySubjectSectionIdAndDeletedOn(subjectSectionId, "").forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<List<Grade>>> getAllArchived(Long subjectSectionId) {
    try {
      List<Grade> items = new ArrayList<Grade>();

      gradeRepository.findBySubjectSectionIdAndDeletedOnNot(subjectSectionId, "").forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<Grade>> getById(Long id) {
    Optional<Grade> existingItemOptional = gradeRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(new ResponseDto<>(existingItemOptional.get(), null), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Grade>> create(Long subjectSectionId, CreateGradeDto item,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Optional<Grade> existingItemOptional = gradeRepository
          .findBySubjectSectionIdAndStudentIdAndDeletedOn(subjectSectionId, item.getStudentId(), "");

      if (existingItemOptional.isPresent()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("studentId", "Student is already added"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      SubjectSection subjectSection = subjectSectionRepository.getReferenceById(subjectSectionId);
      Student student = studentRepository.getReferenceById(item.getStudentId());
      Grade savedItem = gradeRepository.persist(new Grade(item, subjectSection, student));
      return new ResponseEntity<>(
          new ResponseDto<>(savedItem.setSubjectSectionId(subjectSectionId).setStudentId(item.getStudentId()), null),
          HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Grade>> update(Long subjectSectionId, Long id, UpdateGradeDto item,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Optional<Grade> existingItemOptional = gradeRepository
          .findBySubjectSectionIdAndStudentIdAndDeletedOnAndIdNot(subjectSectionId, item.getStudentId(), "", id);

      if (existingItemOptional.isPresent()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("studentId", "Student is already added"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      SubjectSection subjectSection = subjectSectionRepository.getReferenceById(subjectSectionId);
      Student student = studentRepository.getReferenceById(item.getStudentId());
      Grade updatedItem = gradeRepository.update(new Grade(id, item, subjectSection, student));
      return new ResponseEntity<>(
          new ResponseDto<>(updatedItem.setSubjectSectionId(subjectSectionId).setStudentId(item.getStudentId()), null),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  public ResponseEntity<HttpStatus> delete(Long id) {
    try {
      gradeRepository.setDeletedOnFor(MainUtility.getCurrentDateTimeInString(), id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
