package ar.com.eldars.kencinas.challenge.services;

import ar.com.eldars.kencinas.challenge.handlers.DataInputHandler;
import ar.com.eldars.kencinas.challenge.models.CardBrand;
import ar.com.eldars.kencinas.challenge.utils.CommonConstants;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class CardBrandService {
    public void showRates() {
        while (true) {
            try {
                LocalDate date = DataInputHandler.nullableNext("Ingrese una fecha (dd-mm-aaaa):>")
                        .map(input -> LocalDate.parse(input, CommonConstants.DATE_FORMATTER))
                        .orElseGet(LocalDate::now);
                System.out.printf("Tasas de marcas de tarjetas para la fecha: %s\n", date.format(CommonConstants.DATE_FORMATTER));
                for (CardBrand brand : CardBrand.values()) {
                    System.out.printf("%s: %.2f%%\n", brand.name(), brand.getRater().getRate(date));
                }
                break;
            } catch (DateTimeParseException ex) {
                System.out.println("No se pudo validar la fecha, ingrese nuevamente.");
            }
        }
    }
}
