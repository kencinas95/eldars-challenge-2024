package ar.com.eldars.kencinas.challenge.utils.rates;

import java.time.LocalDate;

public class AmexRate extends CardRate {
    @Override
    protected Double rate(LocalDate date) {
        return date.getMonthValue() * 0.1;
    }
}
