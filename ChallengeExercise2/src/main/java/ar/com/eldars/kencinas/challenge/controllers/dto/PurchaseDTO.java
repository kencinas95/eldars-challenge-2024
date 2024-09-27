package ar.com.eldars.kencinas.challenge.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
    private Double amount;

    private String description;

    private String brand;

    private Long number;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiration;

    private String cvv;

}
