package com.major13.collector;

import com.major13.collector.model.BeanA;
import com.major13.collector.model.BeanB;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectorUtilityTest {

    @Test
    public void operationWithBeanA() {

        List<BeanA> items = Arrays.asList(
                new BeanA("X", BigDecimal.ONE, BigDecimal.TEN),
                new BeanA("Y", BigDecimal.ONE, BigDecimal.TEN),
                new BeanA("Z", BigDecimal.ONE, BigDecimal.TEN)
        );

        BiConsumer<BeanA, BeanA> combiner = (a, b) -> {
            a.setValueA(CollectorUtility.safeBigDecimalOperation(a.getValueA(), b.getValueA(), BigDecimal::add));
            a.setValueB(CollectorUtility.safeBigDecimalOperation(a.getValueB(), b.getValueB(), BigDecimal::add));
        };

        BeanA sum = CollectorUtility.operation(items, BeanA::new, combiner);
        assertThat(sum).isNotNull();
        assertThat(sum.getValueA()).isEqualTo(BigDecimal.valueOf(3));
        assertThat(sum.getValueB()).isEqualTo(BigDecimal.valueOf(30));

    }

    @Test
    public void operationWithBeanB() {

        List<BeanB> items = Arrays.asList(
                new BeanB("X", 1d, 10d),
                new BeanB("Y", 1d, 10d),
                new BeanB("Z", 1d, 10d)
        );

        BiConsumer<BeanB, BeanB> combiner = BeanB.calculateSum();

        BeanB result = CollectorUtility.operation(items, BeanB::new, combiner);
        assertThat(result).isNotNull();
        assertThat(result.getVal1()).isEqualTo(3d);
        assertThat(result.getVal2()).isEqualTo(30d);

        combiner = BeanB.calculateSubstract();

        result = CollectorUtility.operation(items, BeanB::new, combiner);
        assertThat(result).isNotNull();
        assertThat(result.getVal1()).isEqualTo(-3d);
        assertThat(result.getVal2()).isEqualTo(-30d);
    }

    @Test
    public void safeBigDecimalOperation() {
        assertThat(CollectorUtility.safeBigDecimalOperation(null, null, BigDecimal::add)).isNull();
        assertThat(CollectorUtility.safeBigDecimalOperation(BigDecimal.ONE, null, BigDecimal::add)).isEqualTo(BigDecimal.ONE);
        assertThat(CollectorUtility.safeBigDecimalOperation(null, BigDecimal.TEN, BigDecimal::add)).isEqualTo(BigDecimal.TEN);
        assertThat(CollectorUtility.safeBigDecimalOperation(BigDecimal.ONE, BigDecimal.TEN, BigDecimal::add)).isEqualTo(BigDecimal.valueOf(11L));
    }

    @Test
    public void safeDoubleAdd() {
        assertThat(CollectorUtility.safeDoubleAdd(1d, 10d)).isEqualTo(11d);
        assertThat(CollectorUtility.safeDoubleAdd(1d, null)).isEqualTo(1d);
        assertThat(CollectorUtility.safeDoubleAdd(null, 10d)).isEqualTo(10d);
        assertThat(CollectorUtility.safeDoubleAdd(null, null)).isNull();
    }

}