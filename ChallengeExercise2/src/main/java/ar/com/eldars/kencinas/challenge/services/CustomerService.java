package ar.com.eldars.kencinas.challenge.services;

import ar.com.eldars.kencinas.challenge.exceptions.EntityAlreadyExistsException;
import ar.com.eldars.kencinas.challenge.models.Card;
import ar.com.eldars.kencinas.challenge.models.CardBrand;
import ar.com.eldars.kencinas.challenge.models.CardId;
import ar.com.eldars.kencinas.challenge.models.Customer;
import ar.com.eldars.kencinas.challenge.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CardService cardService;

    private final OperationService operationService;

    private final JavaMailSender mailSender;

    private final PasswordEncoder encoder;

    @SneakyThrows
    public void create(final Long document,
                       final String firstname,
                       final String surname,
                       final LocalDate dob,
                       final String email) {

        if (customerRepository.existsById(document)) {
            log.warn("A customer with ID {} is already registered, cannot register new one!", document);
            throw new EntityAlreadyExistsException();
        }

        try {
            final Customer customer = new Customer();

            customer.setDocumentNumber(document);

            if (StringUtils.isNotBlank(firstname) && StringUtils.isAlphaSpace(firstname))
                customer.setFirstname(StringUtils.capitalize(firstname));

            if (StringUtils.isNotBlank(surname) && StringUtils.isAlphaSpace(surname))
                customer.setSurname(StringUtils.capitalize(surname));

            if (StringUtils.isNotBlank(email))
                customer.setEmail(email.toLowerCase());

            if (Objects.nonNull(dob))
                customer.setDob(dob);

            customerRepository.save(customer);

            log.info("A new customer was created: {}", customer);

        } catch (Exception ex) {
            log.error("Unable to persist new customer!", ex);
            throw ex;
        }
    }

    @SneakyThrows
    public void update(final Long document,
                       final String firstname,
                       final String surname,
                       final LocalDate dob,
                       final String email) {

        Optional<Customer> optionalCustomer = customerRepository.findById(document);
        if (optionalCustomer.isEmpty()) {
            log.error("Customer {} cannot be found!", document);
            throw new NoSuchElementException();
        }

        try {
            final Customer customer = optionalCustomer.get();

            if (!customer.getFirstname().equalsIgnoreCase(firstname)
                    && StringUtils.isNotBlank(firstname)
                    && StringUtils.isAlphaSpace(firstname))
                customer.setFirstname(StringUtils.capitalize(firstname));

            if (!customer.getSurname().equalsIgnoreCase(surname)
                    && StringUtils.isNotBlank(surname)
                    && StringUtils.isAlphaSpace(surname))
                customer.setSurname(StringUtils.capitalize(surname));

            if (!customer.getDob().equals(dob) && Objects.nonNull(dob))
                customer.setDob(dob);

            if (!customer.getEmail().equalsIgnoreCase(email) && StringUtils.isNotBlank(email))
                customer.setEmail(email.toLowerCase());

            customerRepository.save(customer);

            log.info("Customer {} was successfully updated!", document);

        } catch (Exception ex) {
            log.error("Unable to update customer: {}", document, ex);
            throw ex;
        }
    }

    @SneakyThrows
    public void delete(final Long document) {
        if (!customerRepository.existsById(document)) {
            log.error("Customer {} cannot be found!", document);
            throw new NoSuchElementException();
        }

        try {
            customerRepository.deleteById(document);
            log.info("Customer {} was successfully deleted!", document);
        } catch (Exception ex) {
            log.error("Unable to delete customer: {}", document, ex);
            throw ex;
        }
    }

    @SneakyThrows
    public void addCard(final Long document,
                        final String brand,
                        final Long number,
                        final LocalDate expiration) {
        Optional<Customer> optionalCustomer = customerRepository.findById(document);
        if (optionalCustomer.isEmpty()) {
            log.error("Customer {} cannot be found!", document);
            throw new NoSuchElementException();
        }

        try {
            final Customer holder = optionalCustomer.get();

            final String cvv = cardService.create(brand, number, expiration, holder);
            final String message = String.format(
                    "Dear %s %s, here's your CVV of your new card %s %s: %s",
                    holder.getFirstname(),
                    holder.getSurname(),
                    brand.toUpperCase(),
                    StringUtils.overlay(number.toString(), "************", 0, 12),
                    cvv
            );
            sendMail(holder.getEmail(), "[Notification] New Card created", message);
        } catch (Exception ex) {
            log.error("Unable to register card to customer {}", document, ex);
            throw ex;
        }

    }

    @SneakyThrows
    public void purchase(final Long document,
                         final Double amount,
                         final String description,
                         final String brand,
                         final Long number,
                         final LocalDate expiration,
                         final String cvv) {

        try {
            final Customer customer = customerRepository.findById(document).orElseThrow();

            final CardId cardId = new CardId(number, CardBrand.valueOf(brand.toUpperCase()));

            final Card card = customer.getCards().stream()
                    .filter(crd -> crd.getCardId().equals(cardId) && crd.getExpiration().equals(expiration)) // validates same card
                    .filter(crd -> encoder.matches(cvv, crd.getCvv()))  // validates card's cvv
                    .filter(crd -> crd.getExpiration().isAfter(LocalDate.now())) // validates card's expiration
                    .findFirst()
                    .orElseThrow();


            operationService.create(amount, description, card);

            sendMail(
                    customer.getEmail(),
                    "[Notification] Purchase completed",
                    String.format("A purchase has been completed successfully.\nAmount: $%.2f\nDescription: %s", amount, description)
            );

        } catch (Throwable ex) {
            log.error("Unable to purchase!", ex);
            throw ex;
        }
    }


    private void sendMail(final String email, final String subject, final String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setTo(email);
        message.setText(body);

        mailSender.send(message);
    }
}
