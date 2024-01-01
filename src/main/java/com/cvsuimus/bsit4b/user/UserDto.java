package com.cvsuimus.bsit4b.user;

public class UserDto {

  public static record Default(
      Long id,
      String idNumber,
      String firstName,
      String middleName,
      String lastName,
      String nameSuffix,
      String email,
      String username,
      String contactNumber,
      String department,
      Boolean enabled,
      UserRole role,
      String deletedOn) {

  }
}
