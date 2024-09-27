package ar.com.eldars.kencinas.challenge.utils.rates;

import ar.com.eldars.kencinas.challenge.utils.validators.CardValidator;

import java.time.LocalDate;

public abstract class CardRate {
    public Double getRate(LocalDate date) {
        Double result = rate(date);
        return CardValidator.rate(result);
    }

    protected abstract Double rate(LocalDate date);
}
