package ar.com.eldars.kencinas.challenge.controllers;

import ar.com.eldars.kencinas.challenge.models.CardBrand;
import ar.com.eldars.kencinas.challenge.services.OperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/operation")
public class OperationController {
    private final OperationService operationService;

    @GetMapping(value = "/rate", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Double>> getOperationRate(
            @RequestParam("brand") final String brand,
            @RequestParam("amount") final Double amount
    ) {
        log.info("Getting the operation rate for: {} {}", brand.toUpperCase(), amount);
        try {
            return ResponseEntity.ok(
                    operationService.getRatesFromOperationsByCardBrandAndAmount(
                            CardBrand.valueOf(brand.toUpperCase()),
                            amount
                    ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Something went wrong!", ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}
