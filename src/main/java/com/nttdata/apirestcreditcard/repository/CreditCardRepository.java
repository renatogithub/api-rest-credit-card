package com.nttdata.apirestcreditcard.repository;

import com.nttdata.apirestcreditcard.model.CreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CreditCardRepository extends ReactiveMongoRepository<CreditCard, String> {

    Mono<CreditCard> findByPan(String pan);
}
