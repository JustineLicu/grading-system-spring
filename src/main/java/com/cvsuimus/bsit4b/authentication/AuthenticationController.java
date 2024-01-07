package com.cvsuimus.bsit4b.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.cvsuimus.bsit4b.user.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @GetMapping("user")
  public ResponseEntity<UserDto.Default> getUser() {
    return authenticationService.getUser();
  }

  @PostMapping("register")
  public ResponseEntity<UserDto.Default> register(@RequestBody User item) {
    return authenticationService.register(item);
  }

  @PutMapping("user")
  public ResponseEntity<UserDto.Default> updateUser(@RequestBody User item) {
    return authenticationService.updateUser(item);
  }

  @PutMapping("user/password")
  public ResponseEntity<HttpStatus> updateUserPassword(@RequestBody User item) {
    return authenticationService.updateUserPassword(item);
  }

  @DeleteMapping("user")
  public ResponseEntity<HttpStatus> deleteUser(HttpServletRequest request) {
    return authenticationService.deleteUser(request);
  }
}
