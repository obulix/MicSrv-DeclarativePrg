package bootcamp.micsrv.decprg.order.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bootcamp.micsrv.decprg.order.exception.OrderNotFoundException;
import bootcamp.micsrv.decprg.order.model.Order;
import bootcamp.micsrv.decprg.order.repository.OrderRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(@Valid Order order) {
        logger.info("Creating order: {}", order);

        // Additional business logic or validation if needed

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        logger.info("Retrieving all orders");
        return orderRepository.findAll();
    }

    public Order getOrderById(@NotNull Long orderId) {
        logger.info("Retrieving order by ID: {}", orderId);
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
    }

    @Transactional
    public Order updateOrder(@NotNull Long orderId, @Valid Order updatedOrder) {
        logger.info("Updating order with ID: {}", orderId);

        Order existingOrder = getOrderById(orderId);

        // Update fields based on your requirements
        existingOrder.setProductId(updatedOrder.getProductId());
        existingOrder.setUserId(updatedOrder.getUserId());

        logger.info("Order updated: {}", existingOrder);
        return existingOrder;
    }

    @Transactional
    public void deleteOrder(@NotNull Long orderId) {
        logger.info("Deleting order with ID: {}", orderId);

        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            logger.info("Order deleted with ID: {}", orderId);
        } else {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }
    }
}
