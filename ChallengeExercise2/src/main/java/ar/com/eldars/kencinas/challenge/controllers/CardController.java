package ar.com.eldars.kencinas.challenge.controllers;

import ar.com.eldars.kencinas.challenge.controllers.dto.CardDTO;
import ar.com.eldars.kencinas.challenge.services.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/card")
public class CardController {
    private final CardService cardService;

    @PutMapping(value = "/{brand}/{number}")
    public ResponseEntity<Void> update(
            @PathVariable("brand") final String brand,
            @PathVariable("number") final Long number,
            @RequestBody final CardDTO request) {

        log.info("Updating card {} {}: {}", brand.toUpperCase(), number, request);
        log.warn("This method is unnecessary: cards cannot be modified after the registration of them, but they could be delete.");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping(value = "/{brand}/{number}")
    public ResponseEntity<Void> delete(@PathVariable("brand") final String brand,
                                       @PathVariable("number") final Long number) {
        log.info("Deleting a card: {} {}", brand.toUpperCase(), number);

        try {
            cardService.delete(brand, number);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            log.error("Something went wrong!", ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}
