package AzatechStore.service;

import AzatechStore.model.Order;
import AzatechStore.model.Product;
import AzatechStore.repository.OrderRepository;
import AzatechStore.repository.ProductRepository;
import AzatechStore.repository.UserRepository;
import AzatechStore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import AzatechStore.model.Cart;
import AzatechStore.model.UserEntity;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // Ajouter une nouvelle commande
    public Order addOrder(Long cartId, List<Long> productIds, String status, Long userId) {
        // Vérifier si le panier existe
        if (!cartRepository.existsById(cartId)) {
            throw new IllegalArgumentException("Cart not found for ID: " + cartId);
        }

        // Vérifier si l'utilisateur existe
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found for ID: " + userId);
        }

        // Récupérer le panier
        Optional<Cart> cart = cartRepository.findById(cartId);
        Cart cartEntity = cart.orElseThrow(() -> new IllegalArgumentException("Cart not found for ID: " + cartId));

        // Récupérer les produits par leurs IDs
        List<Product> products = productRepository.findAllById(productIds);
        if (products.isEmpty()) {
            System.out.println("No products found for the given product IDs.");
            return null;
        }

        // Récupérer l'utilisateur
        Optional<UserEntity> user = userRepository.findById(userId);
        UserEntity userEntity = user.orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

        // Créer et sauvegarder la commande
        Order order = new Order(cartEntity.getId(), products, status, userEntity.getId());
        return orderRepository.save(order);
    }

    // Supprimer une commande par ID
    public boolean removeOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Modifier une commande existante
    public Order editOrder(Long id, Long cartId, List<Long> productIds, String status, Long userId) {
        // Vérifier si la commande existe
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (!existingOrder.isPresent()) {
            throw new IllegalArgumentException("Order not found for ID: " + id);
        }

        // Vérifier si le panier existe
        if (!cartRepository.existsById(cartId)) {
            throw new IllegalArgumentException("Cart not found for ID: " + cartId);
        }

        // Vérifier si l'utilisateur existe
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found for ID: " + userId);
        }

        // Récupérer le panier
        Optional<Cart> cart = cartRepository.findById(cartId);
        Cart cartEntity = cart.orElseThrow(() -> new IllegalArgumentException("Cart not found for ID: " + cartId));

        // Récupérer les produits par leurs IDs
        List<Product> products = productRepository.findAllById(productIds);
        if (products.isEmpty()) {
            System.out.println("No products found for the given product IDs.");
            return null;
        }

        // Récupérer l'utilisateur
        Optional<UserEntity> user = userRepository.findById(userId);
        UserEntity userEntity = user.orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

        // Mettre à jour la commande
        Order order = existingOrder.get();
        order.setCartId(cartEntity.getId()); // Mettre à jour uniquement l'ID du panier
        order.setProducts(products); // Mettre à jour les produits
        order.setStatus(status);
        order.setUserId(userEntity.getId()); // Mettre à jour uniquement l'ID de l'utilisateur

        return orderRepository.save(order);
    }

    // Obtenir toutes les commandes
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Trouver une commande par ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // Trouver les commandes par statut
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
}
