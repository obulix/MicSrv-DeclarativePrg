package bootcamp.micsrv.decprg.product.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bootcamp.micsrv.decprg.product.exception.ProductNotFoundException;
import bootcamp.micsrv.decprg.product.model.Product;
import bootcamp.micsrv.decprg.product.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Product createProduct(@Valid Product product) {
        logger.info("Creating product: {}", product);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        logger.info("Retrieving all products");
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        logger.info("Retrieving product by ID: {}", productId);
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
    }

    @Transactional
    public Product updateProduct(Long productId, @Valid Product updatedProduct) {
        logger.info("Updating product with ID: {}", productId);

        Optional<Product> existingProductOptional = productRepository.findById(productId);

        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());

            logger.info("Product updated: {}", existingProduct);
            return existingProduct;
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }
    }

    @Transactional
    public void deleteProduct(Long productId) {
        logger.info("Deleting product with ID: {}", productId);

        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            logger.info("Product deleted with ID: {}", productId);
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }
    }
}

