package ar.com.eldars.kencinas.challenge.services;

import ar.com.eldars.kencinas.challenge.handlers.DataInputHandler;
import ar.com.eldars.kencinas.challenge.handlers.FieldInputData;
import ar.com.eldars.kencinas.challenge.models.Customer;
import ar.com.eldars.kencinas.challenge.repositories.CustomerRepository;
import ar.com.eldars.kencinas.challenge.utils.CommonConstants;
import ar.com.eldars.kencinas.challenge.utils.validators.CustomerValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final static List<FieldInputData<Customer>> CUSTOMER_FIELDS = List.of(
            FieldInputData.of("Nombre",
                    CustomerValidator::anyName,
                    (firstname, customer) -> customer.setFirstname(StringUtils.capitalize(firstname))),
            FieldInputData.of("Apellido",
                    CustomerValidator::anyName,
                    (surname, customer) -> customer.setSurname(StringUtils.capitalize(surname))),
            FieldInputData.of("DNI",
                    CustomerValidator::document,
                    (document, customer) -> customer.setDocumentNumber(Long.parseLong(document))),
            FieldInputData.of("Fecha de Nacimiento (dd-mm-aaaa)",
                    CustomerValidator::dob,
                    (dob, customer) -> customer.setDob(LocalDate.parse(dob, CommonConstants.DATE_FORMATTER))),
            FieldInputData.of("Email",
                    CustomerValidator::email,
                    (email, customer) -> customer.setEmail(email.toLowerCase()))
    );

    private final CustomerRepository customerRepository;

    private final DataInputHandler<Customer> inputCustomerHandler;

    public void create() {
        System.out.println("Nuevo ingreso de usuario:");

        final Customer customer = new Customer();
        inputCustomerHandler.handle(CUSTOMER_FIELDS, customer);

        if (customerRepository.existsById(customer.getDocumentNumber()))
        {
            System.out.println("Este usuario ya existe!");
        } else {
            customerRepository.save(customer);
            System.out.println("Un nuevo usuario ha sido ingresado: " + customer);
        }
    }

}
