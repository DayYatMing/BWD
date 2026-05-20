package com.bwd.cms.service;

import com.bwd.cms.domain.Order;
import com.bwd.cms.repository.OrderRepository;
import com.bwd.cms.service.dto.OrderDTO;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Flux<OrderDTO> getOrders() {
        return orderRepository.findAllOrderDTO();
    }

    public Flux<OrderDTO> getCustomerOrders(String code) {
        return orderRepository.findAllCustomerOrderDTO(code);
    }

    public Mono<OrderDTO> getOrderById(Long id) {
        return orderRepository.findByIdOrderDTO(id);
    }

    public Mono<Void> deleteOrderById(Long id) {
        return orderRepository.deleteById(id);
    }

    public Mono<Order> createOrder(String entityId, String name, String status, int duration, FilePart circuitFile) {
        Order order = new Order();
        order.setEntityId(entityId);
        order.setName(name);
        order.setStatus(status);
        order.setDuration(duration);

        if (circuitFile != null) {
            return circuitFile
                .content()
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    return bytes;
                })
                .reduce((a, b) -> {
                    byte[] combined = new byte[a.length + b.length];
                    System.arraycopy(a, 0, combined, 0, a.length);
                    System.arraycopy(b, 0, combined, a.length, b.length);
                    return combined;
                })
                .flatMap(bytes -> {
                    order.setCircuit(bytes);
                    return orderRepository.save(order);
                });
        }

        return orderRepository.save(order);
    }

    public Mono<Order> updateOrder(Long id, String entityId, String name, String status, int duration, FilePart circuitFile) {
        return orderRepository
            .findById(id)
            .flatMap(order -> {
                order.setEntityId(entityId);
                order.setName(name);
                order.setStatus(status);
                order.setDuration(duration);

                if (circuitFile == null) {
                    return orderRepository.save(order);
                }

                return circuitFile
                    .content()
                    .map(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        return bytes;
                    })
                    .reduce((a, b) -> {
                        byte[] combined = new byte[a.length + b.length];
                        System.arraycopy(a, 0, combined, 0, a.length);
                        System.arraycopy(b, 0, combined, a.length, b.length);
                        return combined;
                    })
                    .flatMap(bytes -> {
                        order.setCircuit(bytes);
                        return orderRepository.save(order);
                    });
            });
    }
}
