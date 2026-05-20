package com.bwd.cms.service;

import com.bwd.cms.domain.Customer;
import com.bwd.cms.repository.CustomerRepository;
import com.bwd.cms.service.dto.CustomerDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Flux<CustomerDTO> getCustomers() {
        return customerRepository.findAllCustomerDTO();
    }

    public Mono<CustomerDTO> getCustomerById(Long id) {
        return customerRepository.findByIdCustomerDTO(id);
    }

    public Mono<Customer> createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Mono<Void> deleteCustomer(Long id) {
        return customerRepository.deleteById(id);
    }

    public Mono<String> getCusID(String login) {
        return customerRepository.findNameByLogin(login);
    }
}
