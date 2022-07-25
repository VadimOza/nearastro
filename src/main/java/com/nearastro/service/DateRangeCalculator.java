package com.nearastro.service;

import com.nearastro.model.DateRange;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

@Component
public class DateRangeCalculator {
    public Flux<DateRange> getSubrangesBetween(LocalDate start, LocalDate end) {
        List<DateRange> result = new LinkedList<>();

        LocalDate currentDate = start;

        while (currentDate.isBefore(end) || currentDate.isEqual(end)) {
            if (ChronoUnit.DAYS.between(currentDate, end) <= 6) {
                result.add(new DateRange(currentDate, end));
                return Flux.fromIterable(result);
            } else {
                result.add(new DateRange(currentDate, currentDate.plusDays(6)));
                currentDate = currentDate.plusDays(7);
            }
        }

        return Flux.fromIterable(result);
    }
}
