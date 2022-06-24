/**
 * Controller that receives the requests
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apirestcreditcard.controller;

import com.nttdata.apirestcreditcard.model.CreditCard;
import com.nttdata.apirestcreditcard.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/credit-card")
public class CreditCardController {

    @Autowired
    private CreditCardService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<CreditCard>>> list() {
        Flux<CreditCard> fxCreditCards = service.listAll();

        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fxCreditCards));
    }

    @GetMapping("/{pan}")
    public Mono<ResponseEntity<CreditCard>> getByPan(@PathVariable("pan") String pan) {
        return service.getByPan(pan)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                ); //Mono<ResponseEntity<CreditCard>>
    }

    @PostMapping
    public Mono<ResponseEntity<CreditCard>> register(@RequestBody CreditCard creditCard, final ServerHttpRequest req) {
        return service.create(creditCard)
                .map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CreditCard>> update(@PathVariable("id") String id, @RequestBody CreditCard creditCard) {

        Mono<CreditCard> monoBody = Mono.just(creditCard);
        Mono<CreditCard> monoBD = service.getById(id);

        return monoBD
                .zipWith(monoBody, (bd, cc) -> {
                    bd.setId(id);
                    bd.setCardBrand(cc.getCardBrand());
                    bd.setActive(cc.isActive());
                    bd.setCardType(cc.getCardType());
                    bd.setCustomer(cc.getCustomer());
                    bd.setCreditLimit(cc.getCreditLimit());
                    bd.setBalanceAmount(cc.getBalanceAmount());
                    bd.setChargeDay(cc.getChargeDay());
                    bd.setCvv(cc.getCvv());
                    bd.setMonthYearExp(cc.getMonthYearExp());
                    bd.setNameCard(cc.getNameCard());
                    bd.setPan(cc.getPan());
                    bd.setTotalConsumption(cc.getTotalConsumption());
                    return bd;
                })
                .flatMap(service::update) //bd->service.modificar(bd)
                .map(a -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<CreditCard>(HttpStatus.NOT_FOUND));
    }
}
