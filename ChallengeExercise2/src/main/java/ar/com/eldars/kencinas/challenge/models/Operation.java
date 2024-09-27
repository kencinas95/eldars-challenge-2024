package ar.com.eldars.kencinas.challenge.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EX_OPERATION")
public class Operation {
    @Id
    @GeneratedValue
    @Column(name = "OP_ID")
    private Long identifier;

    @Column(name = "OP_AMOUNT")
    private Double amount;

    @CreationTimestamp
    @Column(name = "EXECUTION_DATE")
    private LocalDate executionDate;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "CARD_BRAND", referencedColumnName = "BRAND_NAME", insertable = false, updatable = false),
            @JoinColumn(name = "CARD_NUMBER", referencedColumnName = "CARD_NUMBER", insertable = false, updatable = false)
    })
    private Card card;

}
