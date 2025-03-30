package AzatechStore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cart_id")
    private Long cartId; // Stocker uniquement l'ID du panier

    @ManyToMany
    @JoinTable(
        name = "order_products",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate = new Date();

    @NotNull(message = "Status is mandatory")
    private String status;

    @Column(name = "user_id")
    private Long userId; // Stocker uniquement l'ID de l'utilisateur

    // Constructeurs
    public Order() {
    }

    public Order(Long cartId, List<Product> products, String status, Long userId) {
        this.cartId = cartId;
        this.products = products;
        this.status = status;
        this.userId = userId;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "Order{id=" + id + ", cartId=" + cartId + ", orderDate=" + orderDate + ", status=" + status + ", userId=" + userId + "}";
    }
}
