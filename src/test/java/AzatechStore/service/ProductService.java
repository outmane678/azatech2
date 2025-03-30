package AzatechStore.service;

import AzatechStore.model.Product;
import AzatechStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Ajouter un nouveau produit
    public Product addProduct(Product product) {
        // Vérifier si l'ID est spécifié manuellement
        if (product.getId() != null && productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Un produit avec cet ID existe déjà.");
        }

        // Ignorer l'ID fourni dans la requête pour éviter les conflits
        product.setId(null);

        // Sauvegarder le produit en base de données
        return productRepository.save(product);
    }

    // Supprimer un produit par ID
    public boolean removeProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalArgumentException("Produit non trouvé avec l'ID : " + id);
        }
    }

    // Modifier un produit existant
    public Product editProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();

            // Mettre à jour les propriétés
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setImageUrl(updatedProduct.getImageUrl());
            product.setStock(updatedProduct.getStock());
            product.setCategory(updatedProduct.getCategory());

            try {
                return productRepository.save(product);  // Sauvegarder les modifications
            } catch (ObjectOptimisticLockingFailureException e) {
                throw new IllegalStateException("Conflit de version : le produit a été modifié par une autre transaction. Veuillez réessayer.");
            }
        } else {
            throw new IllegalArgumentException("Produit non trouvé avec l'ID : " + id);
        }
    }

    // Obtenir tous les produits
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Trouver un produit par ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Trouver des produits par catégorie
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
}