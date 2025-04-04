package AzatechStore.repository;

import AzatechStore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Trouver un panier par l'utilisateur
    Optional<Cart> findByUserId(Long userId);

    // Trouver tous les paniers contenant un produit spécifique
    List<Cart> findByProductsId(Long productId);

    // Vérifier si un produit existe déjà dans le panier d'un utilisateur
    boolean existsByUserIdAndProductsId(Long userId, Long productId);
}
