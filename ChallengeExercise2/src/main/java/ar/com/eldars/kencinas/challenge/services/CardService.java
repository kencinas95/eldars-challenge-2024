package ar.com.eldars.kencinas.challenge.services;

import ar.com.eldars.kencinas.challenge.exceptions.EntityAlreadyExistsException;
import ar.com.eldars.kencinas.challenge.models.Card;
import ar.com.eldars.kencinas.challenge.models.CardBrand;
import ar.com.eldars.kencinas.challenge.models.CardId;
import ar.com.eldars.kencinas.challenge.models.Customer;
import ar.com.eldars.kencinas.challenge.repositories.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    private final PasswordEncoder encoder;

    @SneakyThrows
    public String create(final String brand,
                         final Long number,
                         final LocalDate expiration,
                         final Customer holder) {

        final CardId cardId = new CardId();

        cardId.setNumber(number);
        if (CardBrand.checkValue(brand))
            cardId.setBrand(CardBrand.valueOf(brand.toUpperCase()));

        if (cardRepository.existsById(cardId)) {
            log.error("Cannot persist card, ({}, {}) already exists.", brand.toUpperCase(), number);
            throw new EntityAlreadyExistsException();
        }

        final Card card = new Card();
        card.setCardId(cardId);
        card.setExpiration(expiration);
        card.setHolder(holder);

        final String cvv = generateCVV(brand.toUpperCase(), number, expiration);
        card.setCvv(encoder.encode(cvv));

        cardRepository.save(card);
        log.info("A new card was persisted: {}", card.getCardId());

        return cvv;
    }

    @SneakyThrows
    public void delete(final String brand,
                       final Long number) {
        final CardId cardId = new CardId(number, CardBrand.valueOf(brand.toUpperCase()));
        if (!cardRepository.existsById(cardId)) {
            throw new NoSuchElementException();
        }
        cardRepository.deleteById(cardId);
        log.info("Card {} has been successfully delete.", cardId);
    }

    private String generateCVV(final String brand, final Long number, final LocalDate expiration) {
        List<Integer> hashes = Stream.of(brand.hashCode(), number.hashCode(), expiration.hashCode())
                .map(Math::abs)
                .map(hash -> hash % 10)
                .toList();
        return String.format("%d%d%d", hashes.get(0), hashes.get(1), hashes.get(2));
    }
}
