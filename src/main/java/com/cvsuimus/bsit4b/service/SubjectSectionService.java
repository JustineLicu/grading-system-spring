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
import com.cvsuimus.bsit4b.dto.subjectsection.*;
import com.cvsuimus.bsit4b.entity.*;
import com.cvsuimus.bsit4b.repository.*;
import com.cvsuimus.bsit4b.util.MainUtility;

import jakarta.transaction.Transactional;

@Service
public class SubjectSectionService {

  @Autowired
  private SubjectSectionRepository subjectSectionRepository;

  @Autowired
  private SubjectRepository subjectRepository;

  @Autowired
  private SectionRepository sectionRepository;

  public ResponseEntity<ResponseDto<List<SubjectSection>>> getAll(Long subjectId) {
    try {
      List<SubjectSection> items = new ArrayList<SubjectSection>();

      subjectSectionRepository.findBySubjectIdAndDeletedOn(subjectId, "").forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<List<SubjectSection>>> getAllArchived(Long subjectId) {
    try {
      List<SubjectSection> items = new ArrayList<SubjectSection>();

      subjectSectionRepository.findBySubjectIdAndDeletedOnNot(subjectId, "").forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<SubjectSection>> getById(Long id) {
    Optional<SubjectSection> existingItemOptional = subjectSectionRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(new ResponseDto<>(existingItemOptional.get(), null), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<SubjectSection>> create(Long subjectId, CreateSubjectSectionDto item,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Optional<SubjectSection> existingItemOptional = subjectSectionRepository
          .findBySubjectIdAndSectionIdAndDeletedOn(subjectId, item.getSectionId(), "");

      if (existingItemOptional.isPresent()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("sectionId", "Section is already added"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      Subject subject = subjectRepository.getReferenceById(subjectId);
      Section section = sectionRepository.getReferenceById(item.getSectionId());
      SubjectSection savedItem = subjectSectionRepository.persist(new SubjectSection(item, subject, section));
      return new ResponseEntity<>(
          new ResponseDto<>(savedItem.setSubjectId(subjectId).setSectionId(item.getSectionId()), null),
          HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<SubjectSection>> update(Long subjectId, Long id, UpdateSubjectSectionDto item,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Optional<SubjectSection> existingItemOptional = subjectSectionRepository
          .findBySubjectIdAndSectionIdAndDeletedOnAndIdNot(subjectId, item.getSectionId(), "", id);

      if (existingItemOptional.isPresent()) {
        List<ErrorDto> errors = new ArrayList<ErrorDto>();
        errors.add(new ErrorDto("sectionId", "Section is already added"));
        return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
      }

      Subject subject = subjectRepository.getReferenceById(subjectId);
      Section section = sectionRepository.getReferenceById(item.getSectionId());
      SubjectSection updatedItem = subjectSectionRepository.update(new SubjectSection(id, item, subject, section));
      return new ResponseEntity<>(
          new ResponseDto<>(updatedItem.setSubjectId(subjectId).setSectionId(item.getSectionId()), null),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  public ResponseEntity<HttpStatus> delete(Long id) {
    try {
      subjectSectionRepository.setDeletedOnFor(MainUtility.getCurrentDateTimeInString(), id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
