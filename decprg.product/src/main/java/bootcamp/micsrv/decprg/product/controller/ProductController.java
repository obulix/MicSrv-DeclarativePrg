package bootcamp.micsrv.decprg.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bootcamp.micsrv.decprg.product.exception.ProductNotFoundException;
import bootcamp.micsrv.decprg.product.model.Product;
import bootcamp.micsrv.decprg.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/products")
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Invalid input data", HttpStatus.BAD_REQUEST);
        }
        try {
            Product createdProduct = productService.createProduct(product);
            logger.info("Product created with ID: {}", createdProduct.getId());
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating product: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving products: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieve a product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            logger.error("Product not found with ID {}: {}", productId, e.getMessage());
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error retrieving product: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a product by ID
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @Valid @RequestBody Product updatedProduct, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Invalid input data", HttpStatus.BAD_REQUEST);
        }

        try {
            Product product = productService.updateProduct(productId, updatedProduct);
            logger.info("Product updated with ID: {}", productId);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            logger.error("Product not found with ID {}: {}", productId, e.getMessage());
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error updating product: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a product by ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            logger.info("Product deleted with ID: {}", productId);
            	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ProductNotFoundException e) {
            logger.error("Product not found with ID {}: {}", productId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error deleting product: {}", e.getMessage());
            	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}