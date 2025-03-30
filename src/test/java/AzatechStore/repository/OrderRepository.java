package AzatechStore.repository;

import AzatechStore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Trouver toutes les commandes passées par un utilisateur spécifique
    List<Order> findByUserId(Long userId);

    // Trouver toutes les commandes en fonction de leur statut (ex: "EN COURS", "LIVRÉ", etc.)
    List<Order> findByStatus(String status);

    // Trouver toutes les commandes d'un utilisateur avec un statut spécifique
    List<Order> findByUserIdAndStatus(Long userId, String status);
}
