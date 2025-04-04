package AzatechStore.controller;

import AzatechStore.model.Cart;
import AzatechStore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 📌 Récupérer le panier d'un utilisateur
    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        try {
            Cart cart = cartService.getCartByUserId(userId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Erreur : " + e.getMessage());
        }
    }

    // 📌 Ajouter un produit au panier
    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable Long userId, @PathVariable Long productId) {
        try {
            Cart updatedCart = cartService.addProductToCart(userId, productId);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Erreur : " + e.getMessage());
        }
    }

    // 📌 Supprimer un produit du panier
    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        try {
            Cart updatedCart = cartService.removeProductFromCart(userId, productId);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Erreur : " + e.getMessage());
        }
    }

    // 📌 Vider complètement le panier
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<?> clearCart(@PathVariable Long userId) {
        try {
            cartService.clearCart(userId);
            return ResponseEntity.ok("Panier vidé avec succès !");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Erreur : " + e.getMessage());
        }
    }
}
