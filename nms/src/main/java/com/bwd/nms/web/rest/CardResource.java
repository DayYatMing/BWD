package com.bwd.nms.web.rest;

import com.bwd.nms.orientdbdomain.Card;
import com.bwd.nms.orientdbdomain.RoomLocation;
import com.bwd.nms.service.CardService;
import com.bwd.nms.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CardResource {

    private final Logger log = LoggerFactory.getLogger(CardResource.class);

    private CardService cardService;

    public CardResource(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/cards")
    public Flux<Card> getAllCards() {
        log.debug("REST request to get PortService");
        return cardService.getCards();
    }

    @PostMapping("/cards")
    public Mono<Void> createCard(@Valid @RequestBody Card card) {
        log.debug("REST request to save Card : {}", card);
        if (card.getId() != null) {
            throw new BadRequestAlertException("A new card cannot already have an ID", "CARD", "id exists");
        }
        return cardService.save(card);
    }

    @PutMapping("/cards")
    public Mono<Void> updateCard(@Valid @RequestBody Card card) {
        log.debug("REST request to update card : {}", card);

        return cardService.update(card);
    }


}
