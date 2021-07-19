package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Order;
import org.example.domain.OrderStatus;
import org.example.domain.OrderStatusResponse;
import org.example.exception.ItemNotFoundException;
import org.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public Order register(Order order) {
        return repository.save(order)
                .orElseThrow(ItemNotFoundException::new);
    }

    public void deposit(long id, long amount) {
        Order order = getById(id);
        order.setAmount(amount);
        order.setStatus(OrderStatus.FINISHED.getId());

        update(order);
    }

    public OrderStatusResponse status(long id) {
        Order order = getById(id);
        return OrderStatusResponse.fromOrder(order);
    }

    public void reverse(long id) {
        Order order = getById(id);
        order.setDeletedAt(new Date());
        order.setStatus(OrderStatus.CANCELED.getId());

        update(order);
    }

    private Order getById(long id) {
        return repository.getById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    private void update(Order order) {
        repository.update(order)
                .orElseThrow(ItemNotFoundException::new);
    }
}
