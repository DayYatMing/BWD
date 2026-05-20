package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.Card;
import com.bwd.nms.orientdbdomain.RoomLocation;
import com.bwd.nms.orientdbrepository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CardService {

    private final Logger log = LoggerFactory.getLogger(CardService.class);

    @Autowired
    public CardRepository cardRepository;

    public Flux<Card> getCards() {
        return cardRepository.findAll();
    }


    public Mono<Void> update(Card card) {
        return cardRepository.update(card);
    }

    public Mono<Void> save(Card card) {
        return cardRepository.save(card);
    }
}
