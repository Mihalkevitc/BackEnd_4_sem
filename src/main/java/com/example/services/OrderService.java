package com.example.services;


import com.example.entities.Order;
import com.example.entities.OrderItem;
import com.example.repositories.OrderItemRepository;
import com.example.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService
{
    private OrderRepository orderRepository;

    private OrderItemRepository orderItemRepository;

    public Iterable<Order> getOrdersByUserId(long userId)
    {
        return orderRepository.findAllByUserId(userId);
    }

    public void saveOrderItem(OrderItem orderItem)
    {
        orderItemRepository.save(orderItem);
    }

    public void saveOrder(Order order)
    {
        orderRepository.save(order);
    }

}
