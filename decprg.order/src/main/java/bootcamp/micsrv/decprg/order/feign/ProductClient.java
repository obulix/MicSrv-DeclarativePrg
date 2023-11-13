package bootcamp.micsrv.decprg.order.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import bootcamp.micsrv.decprg.order.model.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@FeignClient(name = "product-service", url ="${productBaseUrl}")
public interface ProductClient {
	
    @GetMapping 
    List<Product> getAllProducts();

    @GetMapping("/{productId}")
    Product getProductById(@PathVariable @NotNull Long productId);

    @PostMapping
    ResponseEntity<?> createProduct(@Valid @RequestBody Product product);

    @PutMapping("/{productId}")
    ResponseEntity<?> updateProduct(@PathVariable @NotNull Long productId, @Valid @RequestBody Product updatedProduct);

    @DeleteMapping("/{productId}")
    ResponseEntity<?> deleteProduct(@PathVariable @NotNull Long productId);
}