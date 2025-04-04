package AzatechStore.controller;

import AzatechStore.model.Order;
import AzatechStore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Ajouter une nouvelle commande
    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestParam Long cartId, 
                                          @RequestParam List<Long> productIds, 
                                          @RequestParam String status, 
                                          @RequestParam Long userId) {
        try {
            Order order = orderService.addOrder(cartId, productIds, status, userId);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Obtenir une commande par ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        Optional<Order> order = Optional.ofNullable(orderService.getOrderById(id));
        return order.isPresent() ? new ResponseEntity<>(order.get(), HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Obtenir toutes les commandes
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Obtenir les commandes par statut
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable("status") String status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Modifier une commande
    @PutMapping("/{id}")
    public ResponseEntity<Order> editOrder(@PathVariable("id") Long id, 
                                           @RequestParam Long cartId, 
                                           @RequestParam List<Long> productIds, 
                                           @RequestParam String status, 
                                           @RequestParam Long userId) {
        try {
            Order updatedOrder = orderService.editOrder(id, cartId, productIds, status, userId);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Supprimer une commande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeOrder(@PathVariable("id") Long id) {
        if (orderService.removeOrder(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
