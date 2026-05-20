package com.bwd.cms.web.rest;

import com.bwd.cms.domain.Order;
import com.bwd.cms.service.OrderService;
import com.bwd.cms.service.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class OrderResource {

    private final Logger LOG = LoggerFactory.getLogger(OrderResource.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public Mono<ResponseEntity<Flux<OrderDTO>>> getOrders() {
        LOG.info("Rest to get all orders.");

        return Mono.just(ResponseEntity.ok().header("status", "ok").body(orderService.getOrders()));
    }

    @GetMapping("/order/cus-acronym/{code}")
    public Mono<ResponseEntity<Flux<OrderDTO>>> getCustomerOrders(@PathVariable String code) {
        LOG.info("Rest to get all customer orders.");

        return Mono.just(ResponseEntity.ok().header("status", "ok").body(orderService.getCustomerOrders(code)));
    }

    @GetMapping("/order/{id}")
    public Mono<ResponseEntity<OrderDTO>> getOrderById(@PathVariable Long id) {
        LOG.info("Rest to get Order by ID.");

        return orderService
            .getOrderById(id)
            .map(order -> ResponseEntity.ok().header("status", "ok").body(order))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/order", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<Void>> createOrder(
        @RequestPart("entityId") String entityId,
        @RequestPart("name") String name,
        @RequestPart("status") String status,
        @RequestPart("duration") String duration,
        @RequestPart(value = "circuit", required = false) FilePart circuit
    ) {
        LOG.info("Rest to create a new order.");

        return orderService
            .createOrder(entityId, name, status, Integer.parseInt(duration), circuit)
            .map(savedOrder -> ResponseEntity.status(HttpStatus.CREATED).header("status", "created").build());
    }

    @PutMapping(value = "/order", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<Void>> updateOrder(
        @RequestPart("id") String id,
        @RequestPart("entityId") String entityId,
        @RequestPart("name") String name,
        @RequestPart("status") String status,
        @RequestPart("duration") String duration,
        @RequestPart(value = "circuit", required = false) FilePart circuit
    ) {
        LOG.info("Rest to update an order.");

        return orderService
            .updateOrder(Long.parseLong(id), entityId, name, status, Integer.parseInt(duration), circuit)
            .map(savedOrder -> ResponseEntity.status(HttpStatus.CREATED).header("status", "updated").build());
    }

    @DeleteMapping("/order/{id}")
    public Mono<ResponseEntity<Void>> deleteOrder(@PathVariable Long id) {
        LOG.info("Rest to delete an existing entity.");

        return orderService.deleteOrderById(id).then(Mono.just(ResponseEntity.ok().header("status", "deleted").build()));
    }
}
