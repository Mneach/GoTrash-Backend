package com.gotrash.util;

import java.time.LocalDate;
import java.time.ZoneId;

public class DateUtil {

  public static LocalDate getCurrentDate() {
    return LocalDate.now(ZoneId.of("Asia/Jakarta"));
  }
}
