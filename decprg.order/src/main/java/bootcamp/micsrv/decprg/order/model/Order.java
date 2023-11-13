package bootcamp.micsrv.decprg.order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="cust_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="product_id")
    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @Column(name="user_id")
    @NotNull(message = "User ID cannot be null")
    private Long userId;


    public Order() {
    }

    public Order(Long productId, Long userId) {
        this.productId = productId;
        this.userId = userId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
