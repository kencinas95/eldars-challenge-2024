package ar.com.eldars.kencinas.challenge.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    @Column(name = "DOC_NUMBER")
    private Long documentNumber;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "DOB")
    private LocalDate dob;

    @Column(name = "EMAIL")
    private String email;

    @OneToMany(mappedBy = "holder", fetch = FetchType.EAGER)
    private List<Card> cards = new ArrayList<>();
}
