
package pedidos.controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pedidos.models.Order;
import pedidos.models.OrderItem;
import pedidos.models.OrderRepository;

@Controller
public class OrderListController {
	@Autowired
	private OrderRepository orderRepository;
		
	@PostConstruct
	void init() {
		Order order1 = new Order("List 1");
		order1.getItems().add(new OrderItem("Milk", false));
		order1.getItems().add(new OrderItem("Coffee", true));
		System.out.print(order1);
		
		Order order2 = new Order("List 2");
		order2.getItems().add(new OrderItem("Bread", false));
		order2.getItems().add(new OrderItem("Water", true));		
		order2.getItems().add(new OrderItem("Fish", true));		
		
		orderRepository.save(order1);
		orderRepository.save(order2);		
	}

	@GetMapping("/")
	public String showOrderList(Model model) {

		model.addAttribute("orders", orderRepository.findAllByOrderByTitleAsc());
		
		return "show_order_list";
	}	
}