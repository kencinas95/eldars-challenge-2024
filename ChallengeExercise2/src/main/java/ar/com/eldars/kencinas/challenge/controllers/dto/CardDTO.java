package ar.com.eldars.kencinas.challenge.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private String brand;

    private Long number;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiration;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private CustomerDTO holder;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String cvv;

}
