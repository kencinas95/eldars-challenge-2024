package ar.com.eldars.kencinas.challenge.services;

import ar.com.eldars.kencinas.challenge.exceptions.FieldInputConvertionException;
import ar.com.eldars.kencinas.challenge.exceptions.FieldInputValidationException;
import ar.com.eldars.kencinas.challenge.handlers.DataInputHandler;
import ar.com.eldars.kencinas.challenge.handlers.FieldInputData;
import ar.com.eldars.kencinas.challenge.models.Card;
import ar.com.eldars.kencinas.challenge.models.CardBrand;
import ar.com.eldars.kencinas.challenge.models.Customer;
import ar.com.eldars.kencinas.challenge.repositories.CardRepository;
import ar.com.eldars.kencinas.challenge.repositories.CustomerRepository;
import ar.com.eldars.kencinas.challenge.utils.CommonConstants;
import ar.com.eldars.kencinas.challenge.utils.validators.CardValidator;
import ar.com.eldars.kencinas.challenge.utils.validators.CustomerValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private static final List<FieldInputData<Card.CardId>> CARD_ID_FIELDS = List.of(
            FieldInputData.of("Numero de Tarjeta",
                    CardValidator::number,
                    (number, cardId) -> cardId.setNumber(Long.parseLong(number))),

            FieldInputData.of("Marca (" + String.join(",", CardBrand.names()) + ")",
                    CardValidator::brand,
                    (brand, cardId) -> cardId.setBrand(CardBrand.valueOf(brand.toUpperCase())))
    );

    private static final List<FieldInputData<Card>> CARD_FIELDS = List.of(
            FieldInputData.of("Fecha de vencimiento",
                    CardValidator::expiration,
                    (exp, card) -> card.setExpiration(LocalDate.parse(exp, CommonConstants.DATE_FORMATTER)))
    );

    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

    private final DataInputHandler<Card> cardDataInputHandler;
    private final DataInputHandler<Card.CardId> cardIdDataInputHandler;

    private final PasswordEncoder bcryptEncoder;

    public void create() {
        if (customerRepository.count() == 0) {
            System.out.println("No se puede ingresar una nueva tarjeta: no hay usuarios registrados todavia!");
            return;
        }

        System.out.println("Nuevo ingreso de tarjeta:");

        Card.CardId cardId = new Card.CardId();
        cardIdDataInputHandler.handle(CARD_ID_FIELDS, cardId);

        Card card = new Card();
        card.setCardId(cardId);
        cardDataInputHandler.handle(CARD_FIELDS, card);

        while (true) {
            try {
                card.setHolder(searchCardHolder());
                break;
            } catch (NoSuchElementException ex) {
                System.out.println("Ingrese nuevamente el usuario a asociar...");
            }
        }

        String cvv = generateCVV(
                card.getCardId().getBrand().name(),
                card.getCardId().getNumber(),
                card.getExpiration()
        );
        card.setCvv(bcryptEncoder.encode(cvv));

        cardRepository.save(card);
        System.out.println("Una nueva tarjeta ha sido ingresada: " + card);
        System.out.println("El CVV de la tarjeta es: " + cvv);
    }

    public void report() {
        if (customerRepository.count() == 0 || cardRepository.count() == 0) {
            System.out.println("No hay datos para mostrar!");
            return;
        }

        while (true) {
            try {
                Long document = DataInputHandler.next(
                        "Ingrese el DNI del usuario a buscar:> ",
                        CustomerValidator::document,
                        Long::parseLong);

                Customer customer = customerRepository.findById(document).orElseThrow();
                if (customer.getCards().isEmpty()) {
                    System.out.println("Este usuario no tiene tarjetas asociadas.");
                } else {
                    System.out.println("Tarjetas:");
                    for (int i = 0; i < customer.getCards().size(); i++) {
                        Card card = customer.getCards().get(i);
                        System.out.printf("%d - %s %d %s\n",
                                i + 1,
                                card.getCardId().getBrand().name(),
                                card.getCardId().getNumber(),
                                card.getExpiration().format(CommonConstants.DATE_FORMATTER));
                    }
                }
                break;
            } catch (FieldInputValidationException | FieldInputConvertionException ex) {
                System.out.println("Hubo un error validando el dato ingresado, intente nuevamente.");
            } catch (NoSuchElementException ex) {
                System.out.println("No se pudo encontrar el usuario!");
                break;
            }
        }

    }

    private Customer searchCardHolder() throws NoSuchElementException {
        String firstname = StringUtils.capitalize(DataInputHandler.next("Ingrese Nombre de Usuario:> "));
        String surname = StringUtils.capitalize(DataInputHandler.next("Ingrese Apellido de Usuario:> "));

        List<Customer> customers = customerRepository.findByFirstnameAndSurname(firstname, surname);
        if (customers.isEmpty()) {
            System.out.println("El usuario que ingresó no existe!");
            throw new NoSuchElementException();
        }

        if (customers.size() > 1) {
            System.out.println("Se han encontrado mas de un usuario llamado: " + firstname + " " + surname);
            while (true) {
                for (int i = 0; i < customers.size(); i++) {
                    System.out.printf("%d - DNI: %d\n", i + 1, customers.get(i).getDocumentNumber());
                }
                try {
                    int option = Integer.parseInt(DataInputHandler.next("Opcion:> "));
                    if (option <= 0 || option > customers.size()) {
                        throw new RuntimeException("invalid option");
                    }
                    return customers.get(option - 1);
                } catch (RuntimeException ex) {
                    System.out.println("Hubo un error al leer la opcion, intente nuevamente.");
                }
            }
        }

        return customers.get(0);
    }

    private String generateCVV(String brand, Long number, LocalDate expiration) {
        List<Integer> hashes = Stream.of(brand.hashCode(), number.hashCode(), expiration.hashCode())
                .map(Math::abs)
                .map(hash -> hash % 10)
                .toList();
        return String.format("%d%d%d", hashes.get(0), hashes.get(1), hashes.get(2));
    }

}
