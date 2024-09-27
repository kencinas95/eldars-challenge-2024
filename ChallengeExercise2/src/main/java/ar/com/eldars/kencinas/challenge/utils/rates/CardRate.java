package ar.com.eldars.kencinas.challenge.utils.rates;


import ar.com.eldars.kencinas.challenge.utils.CommonConstants;

import java.time.LocalDate;

public abstract class CardRate {
    public Double getRate(LocalDate date) {
        Double result = rate(date);
        if (result < CommonConstants.CARD_BRAND_RATE_MIN) return CommonConstants.CARD_BRAND_RATE_MIN;
        if (result > CommonConstants.CARD_BRAND_RATE_MAX) return CommonConstants.CARD_BRAND_RATE_MAX;
        return result;
    }

    protected abstract Double rate(LocalDate date);
}
