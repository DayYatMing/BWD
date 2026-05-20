package com.bwd.cms.web.rest;

import com.bwd.cms.domain.Customer;
import com.bwd.cms.service.CustomerService;
import com.bwd.cms.service.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CustomerResource {

    private final Logger LOG = LoggerFactory.getLogger(CustomerResource.class);

    @Autowired
    CustomerService customerService;

    @GetMapping("/customer")
    public Mono<ResponseEntity<Flux<CustomerDTO>>> getCustomers() {
        LOG.info("Rest to get customer page.");

        return Mono.just(ResponseEntity.ok().header("status", "ok").body(customerService.getCustomers()));
    }

    @GetMapping("/customer/{id}")
    public Mono<ResponseEntity<CustomerDTO>> getCustomers(@PathVariable Long id) {
        LOG.info("Rest to get customer id {} detail.", id);

        return customerService
            .getCustomerById(id)
            .map(customer -> ResponseEntity.ok().header("status", "ok").body(customer))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/customer")
    public Mono<ResponseEntity<Void>> createCustomer(@RequestBody Customer customer) {
        LOG.info("Rest to create customer entry.");

        return customerService
            .createCustomer(customer)
            .map(savedCustomer -> ResponseEntity.status(HttpStatus.CREATED).header("status", "created").build());
    }

    @PutMapping("/customer")
    public Mono<ResponseEntity<Void>> updateCustomer(@RequestBody Customer customer) {
        LOG.info("Rest to update customer entry.");

        return customerService
            .createCustomer(customer)
            .map(updatedCustomer -> ResponseEntity.status(HttpStatus.CREATED).header("status", "updated").build());
    }

    @DeleteMapping("/customer/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable Long id) {
        LOG.info("Rest to delete customer entry.");

        return customerService.deleteCustomer(id).then(Mono.just(ResponseEntity.ok().header("status", "deleted").build()));
    }
}
