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
public class CustomerDTO {
    private String firstname;

    private String surname;

    private Long docNumber;

    private String email;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

}
