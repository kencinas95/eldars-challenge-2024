package ar.com.eldars.kencinas.challenge.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CardId {
    @Column(name = "CARD_NUMBER")
    private Long number;

    @Enumerated(EnumType.STRING)
    @Column(name = "BRAND_NAME")
    private CardBrand brand;
}
