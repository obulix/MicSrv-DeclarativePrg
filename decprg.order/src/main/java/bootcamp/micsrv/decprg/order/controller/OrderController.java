package bootcamp.micsrv.decprg.order.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bootcamp.micsrv.decprg.order.exception.FeignClientExceptionHandler;
import bootcamp.micsrv.decprg.order.exception.OrderNotFoundException;
import bootcamp.micsrv.decprg.order.feign.ProductClient;
import bootcamp.micsrv.decprg.order.feign.UserClient;
import bootcamp.micsrv.decprg.order.model.Order;
import bootcamp.micsrv.decprg.order.model.Product;
import bootcamp.micsrv.decprg.order.model.User;
import bootcamp.micsrv.decprg.order.service.OrderService;
import bootcamp.micsrv.decprg.order.util.Constants;
import feign.FeignException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;
    
    // Create a new order
    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody Order order, BindingResult result) {
        logger.info("Received request to create order: {}", order);
        
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
        	Product product = productClient.getProductById(order.getProductId());
        	User user = userClient.getUserById(order.getUserId());
        	
            Order createdOrder = orderService.createOrder(order);
            logger.info("Order created with ID: {}", createdOrder.getId());
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        }
        catch(FeignException ex)
        {
        	return FeignClientExceptionHandler.handleFeignException(ex);
        }
        catch (Exception e) {

            logger.error("Error creating order: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        logger.info("Received request to retrieve all orders");

        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable @NotNull Long orderId) {
        logger.info("Received request to retrieve order by ID: {}", orderId);

        try {
            Order order = orderService.getOrderById(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            logger.error("Order not found with ID: {}", orderId);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable @NotNull Long orderId, @Valid @RequestBody Order updatedOrder,
                                        BindingResult result) {
        logger.info("Received request to update order with ID: {}", orderId);

        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
        	Product product = productClient.getProductById(updatedOrder.getProductId());
        	User user = userClient.getUserById(updatedOrder.getUserId());
            Order order = orderService.updateOrder(orderId, updatedOrder);
            logger.info("Order updated with ID: {}", orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        catch(FeignException ex)
        {
        	return FeignClientExceptionHandler.handleFeignException(ex);
        }
        catch (OrderNotFoundException e) {
            logger.error("Order not found with ID: {}", orderId);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {

            logger.error("Error creating order: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable @NotNull Long orderId) {
        logger.info("Received request to delete order with ID: {}", orderId);

        try {
            orderService.deleteOrder(orderId);
            logger.info("Order deleted with ID: {}", orderId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (OrderNotFoundException e) {
            logger.error("Order not found with ID: {}", orderId);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}