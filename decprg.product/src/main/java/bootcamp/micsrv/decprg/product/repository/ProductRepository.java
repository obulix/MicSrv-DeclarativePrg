package bootcamp.micsrv.decprg.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bootcamp.micsrv.decprg.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
