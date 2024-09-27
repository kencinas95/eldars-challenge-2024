package ar.com.eldars.kencinas.challenge.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "EX_CARD")
public class Card {
    @EmbeddedId
    private CardId cardId;

    @Future
    @Column(name = "EXPIRATION_DT")
    private LocalDate expiration;

    @NotBlank
    @Column(name = "CVV")
    private String cvv;

    @ManyToOne
    @JoinColumn(name = "HOLDER")
    private Customer holder;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    private List<Operation> operations = new ArrayList<>();

}
