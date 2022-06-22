package com.nttdata.apirestcreditcard.service;

import com.nttdata.apirestcreditcard.model.CreditCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardService {

    Mono<CreditCard> create(CreditCard creditCard);

    Mono<CreditCard> update(CreditCard creditCard);

    Flux<CreditCard> listAll();

    Mono<CreditCard> getById(String id);

    Mono<CreditCard> getByPan(String pan);
}
