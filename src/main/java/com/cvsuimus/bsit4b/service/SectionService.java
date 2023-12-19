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
import com.cvsuimus.bsit4b.dto.section.*;
import com.cvsuimus.bsit4b.entity.Course;
import com.cvsuimus.bsit4b.entity.Section;
import com.cvsuimus.bsit4b.repository.CourseRepository;
import com.cvsuimus.bsit4b.repository.SectionRepository;

import jakarta.transaction.Transactional;

@Service
public class SectionService {

  @Autowired
  private SectionRepository sectionRepository;

  @Autowired
  private CourseRepository courseRepository;

  public ResponseEntity<ResponseDto<List<Section>>> getAll() {
    try {
      List<Section> items = new ArrayList<Section>();

      sectionRepository.findAll().forEach(items::add);

      return new ResponseEntity<>(new ResponseDto<>(items, null), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<ResponseDto<Section>> getById(Long id) {
    Optional<Section> existingItemOptional = sectionRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(new ResponseDto<>(existingItemOptional.get(), null), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Section>> create(CreateSectionDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Course course = courseRepository.getReferenceById(item.getCourseId());
      Section savedItem = sectionRepository.persist(new Section(item, course));
      return new ResponseEntity<>(new ResponseDto<>(savedItem.setCourseId(item.getCourseId()), null),
          HttpStatus.CREATED);
    } catch (Exception e) {
      List<ErrorDto> errors = new ArrayList<ErrorDto>();
      errors.add(new ErrorDto("section", "Section is already created"));

      return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
    }
  }

  @Transactional
  public ResponseEntity<ResponseDto<Section>> update(Long id, UpdateSectionDto item, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

    try {
      Course course = courseRepository.getReferenceById(item.getCourseId());
      Section updatedItem = sectionRepository.update(new Section(id, item, course));
      return new ResponseEntity<>(new ResponseDto<>(updatedItem.setCourseId(item.getCourseId()), null), HttpStatus.OK);
    } catch (Exception e) {
      List<ErrorDto> errors = new ArrayList<ErrorDto>();
      errors.add(new ErrorDto("section", "Section is already created"));

      return new ResponseEntity<>(new ResponseDto<>(null, errors), HttpStatus.EXPECTATION_FAILED);
    }
  }

  public ResponseEntity<HttpStatus> delete(Long id) {
    try {
      sectionRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
