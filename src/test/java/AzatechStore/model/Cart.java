package AzatechStore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // Stocker uniquement l'ID de l'utilisateur

    @ManyToMany
    @JoinTable(
        name = "cart_products",
        joinColumns = @JoinColumn(name = "cart_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>(); // Initialisation de la liste des produits

    @PositiveOrZero(message = "Total price must be positive or zero")
    @Column(nullable = false)
    private double totalPrice;

    // Constructeurs
    public Cart() {}

    public Cart(Long userId, List<Product> products) {
        this.userId = userId;
        this.products = products != null ? products : new ArrayList<>(); // Assurez-vous que la liste n'est jamais nulle
        updateTotalPrice(); // Calcul automatique du total
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products != null ? products : new ArrayList<>(); // Eviter les valeurs nulles
        updateTotalPrice(); // Recalculer le prix total si les produits changent
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // Méthode pour mettre à jour le total
    public void updateTotalPrice() {
        if (products != null && !products.isEmpty()) {
            this.totalPrice = products.stream().mapToDouble(Product::getPrice).sum();
        } else {
            this.totalPrice = 0.0;
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
