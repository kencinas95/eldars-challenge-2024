package ar.com.eldars.kencinas.challenge.utils.rates;

import java.time.LocalDate;

public class VisaRate extends CardRate {
    @Override
    protected Double rate(LocalDate date) {
        return (date.getYear() % 100) / (double) date.getMonthValue();
    }
}
