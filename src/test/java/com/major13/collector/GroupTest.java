package com.major13.collector;

import com.major13.collector.model.BeanC;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static java.util.stream.Collector.of;

public class GroupTest {

    private static BeanC get() {
        BeanC result = new BeanC();
        result.setValueOrg(0d);
        result.setValueUsd(0d);
        return result;
    }

    @Test
    public void operationWithBeanA() {

        Map <Integer, Integer> projectToCreditor = new HashMap<>();
        projectToCreditor.put(1, 1);
        projectToCreditor.put(2, 1);
        projectToCreditor.put(3, 1);
        projectToCreditor.put(4, 2);
        projectToCreditor.put(5, 2);

        List<BeanC> items = Arrays.asList(
                new BeanC(null, 1, "A", 2010, 1d, 10d),
                new BeanC(null, 2, "B", 2010, 1d, 10d),
                new BeanC(null, 3, "C", 2010, 1d, 10d),
                new BeanC(null, 4, "B", 2010, 1d, 10d),
                new BeanC(null, 1, "B", 2011, 1d, 10d),
                new BeanC(null, 4, "B", 2011, 1d, 10d),
                new BeanC(null, 5, "B", 2011, 1d, 10d)
        );

        items = items.stream().map(item -> getCreditor(projectToCreditor, item)).collect(Collectors.toList());

        HashMap<Integer, List<BeanC>> collect = items.stream().collect(() -> new HashMap<>(),
                GroupTest::accept,
                (integerBeanCHashMap, integerBeanCHashMap2) -> {integerBeanCHashMap.putAll(integerBeanCHashMap2);
        });

        System.out.println(collect.get(2010));
        System.out.println(collect.get(2011));


        Map<Integer, Map<Integer, BeanC>> data = collect.entrySet().stream().collect(Collectors.toMap(
                e -> e.getKey(),
                e -> countCreditorsInOneYear(e.getValue())
        ));

        System.out.println("---------------");

        System.out.println(data.get(2010));
        System.out.println(data.get(2011));


        System.out.println("--------------------------");
        Set<Integer> years = items.stream().map(item -> item.getPariod()).collect(Collectors.toSet());
        System.out.println(years);

        Map<Integer, List<BeanC>> byYear = new HashMap<>();
        for (Integer year: years) {
            byYear.put(year, items.stream().filter(item -> year.equals(item.getPariod())).collect(Collectors.toList()));
        }

        System.out.println(byYear);

        /*Map<Integer, Map<Integer, BeanC>> result2 = byYear.entrySet().stream().collect(
                Collectors.toMap(
                        e -> e.getKey(),
                        e -> countCreditorsInOneYear(e.getValue())
                )
        );*/

        Map<Integer, Map<Integer, Double>> result = new HashMap<>();
        for (Integer key: byYear.keySet()) {
            List<BeanC> events = byYear.get(key);
            Map<Integer, Double> map = new HashMap<>();
            for (BeanC event: events) {
                if (map.containsKey(event.getCreditorId())) {
                    Double value = map.get(event.getCreditorId());
                    map.put(event.getCreditorId(), value + event.getValueUsd());
                } else {
                    map.put(event.getCreditorId(), event.getValueUsd());
                }
            }
            result.put(key, map);
        }

        System.out.println(result);


    }


    private Map<Integer, BeanC> countCreditorsInOneYear(List<BeanC> items) {
        return items.stream().collect(Collectors.groupingBy(BeanC::getCreditorId,
                of(
                        GroupTest::get,
                        businessOperation(),
                        (BeanC a, BeanC b) -> a
                )));
    }

    private BiConsumer<BeanC, BeanC> combiner() {
        return (a, b) -> {
        };
    }

    private BiConsumer<BeanC, BeanC> businessOperation() {
        return (a, b) -> {
            a.setCreditorId(b.getCreditorId());
            a.setPariod(b.getPariod());
            a.setValueUsd(a.getValueUsd() + b.getValueUsd());
            a.setValueOrg(a.getValueOrg() + b.getValueOrg());
        };
    }

    private BeanC getCreditor (Map <Integer, Integer> creditors, BeanC item) {
        item.setCreditorId(creditors.get(item.getProjectId()));
        return item;
    }

    //return year and list of related creditors
    private static void accept(HashMap<Integer, List<BeanC>> integerBeanCHashMap, BeanC beanC) {
        List<BeanC> byYear = integerBeanCHashMap.computeIfAbsent(beanC.getPariod(), integer -> Lists.newArrayList());
        byYear.add(beanC);
    }


}