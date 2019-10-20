
package alberto.alvarez.garcia;

import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShowOrderListController {
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
	
	@GetMapping("/show/{id}")
	public String showOrder(Model model, @PathVariable Long id) {
		model.addAttribute("order", orderRepository.findById(id).get());
		return "show_order";
	}

	@GetMapping("/edit/{id}")
	public String editOrder(Model model, @PathVariable Long id) {
		model.addAttribute("order", orderRepository.findById(id).get());
		return "edit_order_form";
	}

	@GetMapping("/new")
	public String newOrder(Model model) {
		return "edit_order_form";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteOrder(Model model, @PathVariable Long id) {
		Optional<Order> order = orderRepository.findById(id); 

		orderRepository.delete(order.get());
		
		return "/";
	}

	@GetMapping("/")
	public String showOrderList(Model model) {

		model.addAttribute("orders", orderRepository.findAllByOrderByTitleAsc());
		
		return "show_order_list";
	}
	
	
	@GetMapping("/create")
	public String createOrder(Model model, @RequestParam Map<String,String> allParams) {

		Order order = buildOrder(allParams);
		orderRepository.save(order);
		String id = order.getId().toString();
		return "redirect:/show/" + id;
	}
	
	@GetMapping("/update/{id}")
	public String updateOrder(Model model, @PathVariable Long id, @RequestParam Map<String,String> allParams) {
		Order newOrder = buildOrder(allParams);

		Optional<Order> oldOrder = orderRepository.findById(id);
	
		orderRepository.delete(oldOrder.get());
		orderRepository.save(newOrder);
		
		return "/show/" + newOrder.getId();
	}

	private Order buildOrder(Map<String,String> allParams) {
		Order order = new Order(allParams.get("title"));
		for(String name : allParams.keySet()) {
			if (name.startsWith("item-")) {
				order.getItems().add(new OrderItem(allParams.get(name), false));
			}
		}
		return order;
	}
}