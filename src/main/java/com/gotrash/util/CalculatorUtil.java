package com.gotrash.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class CalculatorUtil {

  public static BigInteger calculateCoin(BigDecimal weight, BigInteger baseCoin) {
    BigDecimal coinPerGram = new BigDecimal(baseCoin);

    return weight.multiply(coinPerGram)
        .setScale(0, RoundingMode.HALF_UP)
        .toBigInteger()
        .max(BigInteger.ONE);
  }

  public static BigInteger calculateRating(BigDecimal weight, BigInteger baseRating) {
    BigDecimal ratingPerGram = new BigDecimal(baseRating);

    return weight.multiply(ratingPerGram)
        .setScale(0, RoundingMode.HALF_UP)
        .toBigInteger()
        .max(BigInteger.ONE);
  }
}
