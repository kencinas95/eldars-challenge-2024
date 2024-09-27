package ar.com.eldars.kencinas.challenge.models;


import ar.com.eldars.kencinas.challenge.utils.rates.AmexRate;
import ar.com.eldars.kencinas.challenge.utils.rates.CardRate;
import ar.com.eldars.kencinas.challenge.utils.rates.NaraRate;
import ar.com.eldars.kencinas.challenge.utils.rates.VisaRate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


@Getter
@AllArgsConstructor
public enum CardBrand {
    VISA(new VisaRate()),
    NARA(new NaraRate()),
    AMEX(new AmexRate());

    private final CardRate rater;

    public static List<String> names() {
        return Arrays.stream(CardBrand.values()).map(CardBrand::name).toList();
    }

    public static boolean checkValue(final String name) {
        return CardBrand.names().stream().anyMatch(brand -> brand.equalsIgnoreCase(name));
    }

}
