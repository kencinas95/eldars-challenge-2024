package ar.com.eldars.kencinas.challenge.services;


import ar.com.eldars.kencinas.challenge.models.Card;
import ar.com.eldars.kencinas.challenge.models.CardBrand;
import ar.com.eldars.kencinas.challenge.models.CardId;
import ar.com.eldars.kencinas.challenge.models.Customer;
import ar.com.eldars.kencinas.challenge.repositories.CardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
    @Mock
    private CardRepository cardRepository;

    @Mock
    private PasswordEncoder encoder;

    private CardService service;

    @BeforeEach
    void setup() {
        service = new CardService(cardRepository, encoder);
    }

    @Test
    void testShouldCreateNewCardSuccess() {
        final String brand = "VISA";
        final Long number = 4000111100002222L;
        final Customer customer = new Customer();

        // This value was produced
        final String expectedCvv = "726";

        final String encodedCVV = "cvv";

        final LocalDate expectedExpiration = LocalDate.now().plusDays(90);

        final CardId expectedId = new CardId(number, CardBrand.VISA);

        final Card expected = new Card();
        expected.setCardId(expectedId);
        expected.setCvv(expectedCvv);
        expected.setExpiration(expectedExpiration);
        expected.setHolder(customer);

        Mockito.when(cardRepository.existsById(Mockito.any()))
                .thenReturn(false);

        Mockito.when(cardRepository.save(Mockito.any()))
                .thenReturn(expected);

        Mockito.when(encoder.encode(expectedCvv))
                .thenReturn(encodedCVV);

        final String cvv = service.create(brand, number, expectedExpiration, customer);

        Assertions.assertEquals(cvv, expectedCvv);

    }


}
