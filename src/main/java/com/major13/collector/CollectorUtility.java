package com.major13.collector;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

@UtilityClass
public class CollectorUtility {

    /**
     * Option1. Make operation with external combiner
     * @param items
     * @param supplier
     * @param accumulator
     * @param <T>
     * @return
     */
    public static <T> T operation(List<T> items, Supplier<T> supplier, BiConsumer<T, T> accumulator) {
        return items.stream().collect(
                supplier,
                (t, t2) -> accumulator.accept(t, t2),
                (t, t2) -> {});
    }

    static BigDecimal safeBigDecimalOperation(BigDecimal a, BigDecimal b, BinaryOperator<BigDecimal> operator) {
        return a == null ? //if A is null
                (b == null ? null : operator.apply(b, BigDecimal.ZERO)) : // then check B cases with Both NULL and A NULL
                (b == null ? operator.apply(a,BigDecimal.ZERO): operator.apply(a, b)); // then check B cases with B null and All OK
    }

    public static Double safeDoubleAdd(Double a, Double b) {
        if ((a == null) && (b == null)) return null;
        return a == null ? //if A is null
                (b) : // then check B cases with Both NULL and A NULL
                (b == null ? a : a + b); // then check B cases with B null and All OK
    }

    public static Double safeDoubleMinus(Double a, Double b) {
        if ((a == null) && (b == null)) return null;
        return a == null ? //if A is null
                (-b) : // then check B cases with Both NULL and A NULL
                (b == null ? a : a - b); // then check B cases with B null and All OK
    }

}
