package com.cvsuimus.bsit4b.utility;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MainUtility {

  public static String getCurrentDateTimeInString() {
    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Manila"));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    return now.format(formatter);
  }
}
