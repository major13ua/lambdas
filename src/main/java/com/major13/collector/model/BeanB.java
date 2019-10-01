package com.major13.collector.model;

import com.major13.collector.CollectorUtility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeanB {

    private String code;
    private Double val1;
    private Double val2;

    public static BiConsumer<BeanB, BeanB> calculateSum() {
        return (a, b) -> {
            a.setVal1(CollectorUtility.safeDoubleAdd(a.getVal1(), b.getVal1()));
            a.setVal2(CollectorUtility.safeDoubleAdd(a.getVal2(), b.getVal2()));
        };
    }

    public static BiConsumer<BeanB, BeanB> calculateSubstract() {
        return (a, b) -> {
            a.setVal1(CollectorUtility.safeDoubleMinus(a.getVal1(), b.getVal1()));
            a.setVal2(CollectorUtility.safeDoubleMinus(a.getVal2(), b.getVal2()));
        };
    }

}
