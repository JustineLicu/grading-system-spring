package com.cvsuimus.bsit4b.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cvsuimus.bsit4b.user.User;
import com.cvsuimus.bsit4b.utility.MainUtility;

import jakarta.servlet.http.HttpServletRequest;

import com.cvsuimus.bsit4b.user.*;

@Service
public class AuthenticationService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsernameAndDeletedOn(username, "").orElseThrow(
        () -> new UsernameNotFoundException("Username or password is incorrect"));
  }

  public ResponseEntity<UserDto.Default> getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
      return new ResponseEntity<>(
          ((User) authentication.getPrincipal()).toDefaultDto(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  public ResponseEntity<UserDto.Default> register(User item) {
    Boolean idNumberExists = userRepository.findByIdNumberAndDeletedOn(
        item.getIdNumber(), "").isPresent();
    if (idNumberExists) {
      throw new IllegalStateException("ID Number is already registered");
    }

    Boolean emailExists = userRepository.findByEmailAndDeletedOn(item.getEmail(), "")
        .isPresent();
    if (emailExists) {
      throw new IllegalStateException("Email is already taken");
    }

    Boolean usernameExists = userRepository.findByUsernameAndDeletedOn(
        item.getUsername(), "").isPresent();
    if (usernameExists) {
      throw new IllegalStateException("Username is already taken");
    }

    String encodedPassword = bCryptPasswordEncoder.encode(item.getPassword());
    User savedItem = userRepository.save(item.setPassword(encodedPassword));
    return new ResponseEntity<>(savedItem.toDefaultDto(), HttpStatus.CREATED);
  }

  public ResponseEntity<UserDto.Default> updateUser(User item) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
      User existingItem = (User) authentication.getPrincipal();

      if (item.getIdNumber() != null && !existingItem.getIdNumber().equals(item.getIdNumber())) {
        Boolean idNumberExists = userRepository.findByIdNumberAndDeletedOn(
            item.getIdNumber(), "").isPresent();
        if (idNumberExists) {
          throw new IllegalStateException("ID Number is already registered");
        } else {
          existingItem.setIdNumber(item.getIdNumber());
        }
      }

      if (item.getEmail() != null && !existingItem.getEmail().equals(item.getEmail())) {
        Boolean emailExists = userRepository.findByEmailAndDeletedOn(item.getEmail(), "")
            .isPresent();
        if (emailExists) {
          throw new IllegalStateException("Email is already taken");
        } else {
          existingItem.setEmail(item.getEmail());
        }
      }

      if (item.getUsername() != null && !existingItem.getUsername().equals(item.getUsername())) {
        Boolean usernameExists = userRepository.findByUsernameAndDeletedOn(
            item.getUsername(), "").isPresent();
        if (usernameExists) {
          throw new IllegalStateException("Username is already taken");
        } else {
          existingItem.setUsername(item.getUsername());
        }
      }

      if (item.getFirstName() != null) {
        existingItem.setFirstName(item.getFirstName());
      }

      if (item.getMiddleName() != null) {
        existingItem.setMiddleName(item.getMiddleName());
      }

      if (item.getLastName() != null) {
        existingItem.setLastName(item.getLastName());
      }

      if (item.getNameSuffix() != null) {
        existingItem.setNameSuffix(item.getNameSuffix());
      }

      if (item.getContactNumber() != null) {
        existingItem.setContactNumber(item.getContactNumber());
      }

      if (item.getDepartment() != null) {
        existingItem.setDepartment(item.getDepartment());
      }

      return new ResponseEntity<>(userRepository.save(existingItem).toDefaultDto(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  public ResponseEntity<HttpStatus> updateUserPassword(User item) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
      User existingItem = (User) authentication.getPrincipal();
      String encodedPassword = bCryptPasswordEncoder.encode(item.getPassword());
      userRepository.save(existingItem.setPassword(encodedPassword));

      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  public ResponseEntity<HttpStatus> deleteUser(HttpServletRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
      User existingItem = (User) authentication.getPrincipal();

      try {
        userRepository.setDeletedOnFor(
            MainUtility.getCurrentDateTimeInString(), existingItem.getId());
        request.logout();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
      }
    } else {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }
}
