package AzatechStore.service;

import AzatechStore.model.Cart;
import AzatechStore.model.Product;
import AzatechStore.repository.CartRepository;
import AzatechStore.repository.ProductRepository;
import AzatechStore.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Vérifier si l'utilisateur existe
    private void validateUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    // Récupérer le panier d'un utilisateur
    public Cart getCartByUserId(Long userId) {
        validateUser(userId);
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userId));
    }

    // Ajouter un produit au panier
    @Transactional
    public Cart addProductToCart(Long userId, Long productId) {
        validateUser(userId);

        // Vérifie si un panier existe, sinon crée un nouveau panier vide
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart(userId, new ArrayList<>());  // Assurez-vous d'initialiser la liste des produits
            return cartRepository.save(newCart);
        });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cart.getProducts().add(product);  // Ajout du produit au panier
        cart.updateTotalPrice(); // Mise à jour du prix total
        return cartRepository.save(cart); // Sauvegarde du panier mis à jour
    }

    // Supprimer un produit du panier
    @Transactional
    public Cart removeProductFromCart(Long userId, Long productId) {
        validateUser(userId);

        Cart cart = getCartByUserId(userId);

        // Retirer le produit et vérifier s'il existe dans le panier
        boolean removed = cart.getProducts().removeIf(product -> product.getId().equals(productId));
        if (!removed) {
            throw new RuntimeException("Product not found in cart");
        }

        cart.updateTotalPrice(); // Mise à jour du prix total après la suppression
        return cartRepository.save(cart);
    }

    // Vider le panier
    @Transactional
    public void clearCart(Long userId) {
        validateUser(userId);

        Cart cart = getCartByUserId(userId);
        cart.getProducts().clear(); // Suppression de tous les produits
        cart.updateTotalPrice(); // Mise à jour du prix total
        cartRepository.save(cart);
    }
}
