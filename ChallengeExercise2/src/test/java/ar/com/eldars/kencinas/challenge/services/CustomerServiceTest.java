package ar.com.eldars.kencinas.challenge.services;

import ar.com.eldars.kencinas.challenge.models.Customer;
import ar.com.eldars.kencinas.challenge.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CardService cardService;

    @Mock
    private OperationService operationService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private PasswordEncoder encoder;

    private CustomerService service;

    @BeforeEach
    void setup() {
        service = new CustomerService(customerRepository, cardService, operationService, mailSender, encoder);
    }

    @Test
    void testShouldAddCardReturnSuccess() {
        final Long document = 100000L;
        final String brand = "AMEX";
        final Long number = 5621000039281103L;
        final LocalDate expiration = LocalDate.now().plusDays(90);
        final String cvv = "031";

        final Customer customer = new Customer();
        customer.setFirstname("John");
        customer.setSurname("Smith");
        customer.setEmail("john.smith@mail.com");

        final String message = "Dear John Smith, here's your CVV of your new card AMEX ************1103: 031";

        final SimpleMailMessage expectedMail = new SimpleMailMessage();
        expectedMail.setSubject("[Notification] New Card created");
        expectedMail.setTo(customer.getEmail());
        expectedMail.setText(message);

        Mockito.when(customerRepository.findById(document))
                .thenReturn(Optional.of(customer));

        Mockito.when(cardService.create(brand, number, expiration, customer))
                .thenReturn(cvv);

        Mockito.doNothing().when(mailSender).send(Mockito.any(SimpleMailMessage.class));

        service.addCard(document, brand, number, expiration);

        Mockito.verify(mailSender, Mockito.atLeastOnce()).send(expectedMail);
    }

}
