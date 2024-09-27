package ar.com.eldars.kencinas.challenge.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EX_CARD")
public class Card {
    @Data
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardId {
        @Column(name = "CARD_NUMBER")
        private Long number;

        @Enumerated(EnumType.STRING)
        @Column(name = "BRAND_NAME")
        private CardBrand brand;
    }

    @EmbeddedId
    private CardId cardId;

    @Column(name = "EXPIRATION_DT")
    private LocalDate expiration;

    @ManyToOne
    @JoinColumn(name = "HOLDER")
    private Customer holder;

    @Column(name = "CVV")
    private String cvv;

}
