package com.nearastro.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


class DateRangeCalculatorTest {

    private final DateRangeCalculator calculator = new DateRangeCalculator();

    private final LocalDate start = LocalDate.of(2020, 1, 1);

    @Test
    @DisplayName("5 days range")
    public void fiveDaysDifference() {
        LocalDate end = LocalDate.of(2020, 1, 4);
        assertThat(calculator.getSubrangesBetween(start, end).collectList().block())
                .hasSize(1)
                .first()
                .matches(dateRange -> dateRange.start().equals(start) && dateRange.end().equals(end));
    }

    @Test
    @DisplayName("One 7 days range and one 5 day range")
    public void thirteenDifference(){
        LocalDate end = LocalDate.of(2020, 1, 12);
        assertThat(calculator.getSubrangesBetween(start, end).collectList().block())
                .hasSize(2)
                .last()
                .matches(dateRange -> dateRange.start().equals(LocalDate.of(2020, 1, 8)) && dateRange.end().equals(end));
    }

    @Test
    @DisplayName("Three 7 days range and one 1 day range")
    public void twentyOneDaysDifference(){
        LocalDate end = LocalDate.of(2020, 1, 22);
        assertThat(calculator.getSubrangesBetween(start, end).collectList().block())
                .hasSize(4)
                .last()
                .matches(dateRange -> dateRange.start().equals(end) && dateRange.end().equals(end));
    }

}