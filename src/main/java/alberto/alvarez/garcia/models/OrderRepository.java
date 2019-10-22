package alberto.alvarez.garcia.models;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
	public List<Order> findAllByOrderByTitleAsc();
}
