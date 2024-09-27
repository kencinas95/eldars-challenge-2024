package ar.com.eldars.kencinas.challenge.repositories;

import ar.com.eldars.kencinas.challenge.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Card.CardId> {

}
