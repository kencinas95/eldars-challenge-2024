package ar.com.eldars.kencinas.challenge.utils.rates;

import java.time.LocalDate;

public class NaraRate extends CardRate {
    @Override
    protected Double rate(LocalDate date) {
        return date.getDayOfMonth() * 0.5;
    }
}
