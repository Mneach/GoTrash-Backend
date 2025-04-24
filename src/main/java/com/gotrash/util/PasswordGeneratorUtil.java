package com.gotrash.util;

import java.security.SecureRandom;

public class PasswordGeneratorUtil {

  private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
  private static final String DIGITS = "0123456789";
  private static final String SYMBOLS = "!@#$%&*()-_=+";
  private static final String ALL = UPPER + LOWER + DIGITS + SYMBOLS;

  private static final SecureRandom random = new SecureRandom();

  public static String generate(int length) {
    if (length < 6) throw new IllegalArgumentException("Password length must be at least 6");

    StringBuilder password = new StringBuilder(length);

    // Ensure at least one character from each category
    password.append(getRandomChar(UPPER));
    password.append(getRandomChar(LOWER));
    password.append(getRandomChar(DIGITS));
    password.append(getRandomChar(SYMBOLS));

    // Fill the rest with random characters
    for (int i = 4; i < length; i++) {
      password.append(getRandomChar(ALL));
    }

    // Shuffle the password
    return shuffleString(password.toString());
  }

  private static char getRandomChar(String chars) {
    return chars.charAt(random.nextInt(chars.length()));
  }

  private static String shuffleString(String input) {
    char[] a = input.toCharArray();
    for (int i = a.length - 1; i > 0; i--) {
      int index = random.nextInt(i + 1);
      char tmp = a[index];
      a[index] = a[i];
      a[i] = tmp;
    }
    return new String(a);
  }
}
