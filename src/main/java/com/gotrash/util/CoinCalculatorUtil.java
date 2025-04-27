package com.gotrash.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class CoinCalculatorUtil {

  public static BigInteger calculate(BigDecimal weight, BigInteger baseCoin) {
    BigDecimal coinPerKg = new BigDecimal(baseCoin);

    return weight.multiply(coinPerKg)
        .setScale(0, RoundingMode.HALF_UP)
        .toBigInteger()
        .max(BigInteger.ONE);
  }
}
