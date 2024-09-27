package ar.com.eldars.kencinas.challenge.services;

import ar.com.eldars.kencinas.challenge.models.Card;
import ar.com.eldars.kencinas.challenge.models.CardBrand;
import ar.com.eldars.kencinas.challenge.models.Operation;
import ar.com.eldars.kencinas.challenge.repositories.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;

    @Value("${operation.amount.max-allowed: 10000.0}")
    private Double maxAllowedAmountInOperation;

    public List<Double> getRatesFromOperationsByCardBrandAndAmount(final CardBrand brand, final Double amount) {
        return operationRepository.findByBrandAndAmount(brand, amount)
                .stream()
                .map(op -> brand.getRater().getRate(op.getExecutionDate()))
                .toList();
    }

    @SneakyThrows
    public void create(final Double amount, final String description, final Card card) {
        if (amount > maxAllowedAmountInOperation) {
            log.error("Operation amount is exceeded, cannot continue: {}", amount);
            throw new RuntimeException("amount exceeded");
        }

        final Operation operation = new Operation();
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setCard(card);

        operationRepository.save(operation);

    }

}
