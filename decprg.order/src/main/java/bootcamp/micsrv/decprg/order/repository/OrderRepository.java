package bootcamp.micsrv.decprg.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bootcamp.micsrv.decprg.order.model.Order;



public interface OrderRepository extends JpaRepository<Order, Long> {
}
