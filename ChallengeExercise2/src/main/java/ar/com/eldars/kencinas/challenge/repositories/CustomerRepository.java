package ar.com.eldars.kencinas.challenge.repositories;

import ar.com.eldars.kencinas.challenge.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByFirstnameAndSurname(String firstname, String surname);

}
