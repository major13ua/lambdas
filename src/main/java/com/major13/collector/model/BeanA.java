package com.major13.collector.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeanA {

    private String name;
    private BigDecimal valueA;
    private BigDecimal valueB;

}
