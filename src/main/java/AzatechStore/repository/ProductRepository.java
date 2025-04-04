package AzatechStore.repository;

import AzatechStore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Trouver tous les produits d'une catégorie spécifique
    List<Product> findByCategory(String category);

    // Recherche des produits par nom (ignorer la casse)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Trouver les produits dont le prix est inférieur ou égal à une certaine valeur
    List<Product> findByPriceLessThanEqual(double price);

    // Vérifier si un produit existe par son nom
    boolean existsByName(String name);
}
