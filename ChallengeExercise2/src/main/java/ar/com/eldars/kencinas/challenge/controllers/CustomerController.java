package ar.com.eldars.kencinas.challenge.controllers;

import ar.com.eldars.kencinas.challenge.controllers.dto.CardDTO;
import ar.com.eldars.kencinas.challenge.controllers.dto.CustomerDTO;
import ar.com.eldars.kencinas.challenge.controllers.dto.PurchaseDTO;
import ar.com.eldars.kencinas.challenge.exceptions.EntityAlreadyExistsException;
import ar.com.eldars.kencinas.challenge.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody final CustomerDTO request) {
        log.info("A new customer is being created: {}", request);
        try {
            customerService.create(
                    request.getDocNumber(),
                    request.getFirstname(),
                    request.getSurname(),
                    request.getDob(),
                    request.getEmail()
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EntityAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{document}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable(name = "document") final Long document,
                                       @RequestBody final CustomerDTO request) {
        log.info("Updating customer: {}", document);
        try {
            customerService.update(
                    document,
                    request.getFirstname(),
                    request.getSurname(),
                    request.getDob(),
                    request.getEmail()
            );
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{document}")
    public ResponseEntity<Void> delete(@PathVariable(name = "document") final Long document) {
        log.info("Deleting customer: {}", document);
        try {
            customerService.delete(document);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/{document}/card")
    public ResponseEntity<Void> addCard(
            @PathVariable(name = "document") final Long document,
            @RequestBody final CardDTO request) {
        log.info("Adding a new card to customer: {} {}", document, request);
        try {
            customerService.addCard(
                    document,
                    request.getBrand(),
                    request.getNumber(),
                    request.getExpiration());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/{document}/purchase")
    public ResponseEntity<Void> purchase(
            @PathVariable(name = "document") final Long document,
            @RequestBody final PurchaseDTO request
    ) {
        log.info("Doing a purchase for customer: {} {}", document, request);
        try {
            customerService.purchase(
                    document,
                    request.getAmount(),
                    request.getDescription(),
                    request.getBrand(),
                    request.getNumber(),
                    request.getExpiration(),
                    request.getCvv());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
