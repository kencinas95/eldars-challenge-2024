package ar.com.eldars.kencinas.challenge.repositories;

import ar.com.eldars.kencinas.challenge.models.CardBrand;
import ar.com.eldars.kencinas.challenge.models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("SELECT op FROM Operation op WHERE op.card.cardId.brand = :brand AND op.amount = :amount")
    List<Operation> findByBrandAndAmount(@Param("brand") CardBrand brand, @Param("amount") Double amount);

}
