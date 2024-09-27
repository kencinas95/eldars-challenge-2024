package ar.com.eldars.kencinas.challenge.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EX_CUSTOMER")
public class Customer {
    @Id
    @Min(value = 1000000)
    @Column(name = "DOC_NUMBER")
    private Long documentNumber;

    @NotBlank
    @Column(name = "FIRSTNAME")
    private String firstname;

    @NotBlank
    @Column(name = "SURNAME")
    private String surname;

    @Past
    @Column(name = "DOB", nullable = false)
    private LocalDate dob;

    @Email
    @Column(name = "EMAIL")
    private String email;

    @OneToMany(mappedBy = "holder", fetch = FetchType.EAGER)
    private List<Card> cards = new ArrayList<>();
}
