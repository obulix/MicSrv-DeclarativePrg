package bootcamp.micsrv.decprg.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "The product name is required.")
    private String name;
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private double price;
    
    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}