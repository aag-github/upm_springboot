
package alberto.alvarez.garcia;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
	
	@GetMapping("/order/{id}")
	public String showOrder(Model model, @PathVariable Long id) {
		System.out.println(id);
		Optional<Order> order = orderRepository.findById(id); 
		System.out.println(order);

		model.addAttribute("order", orderRepository.findById(id).get());
		return "show_order";
	}
	
	@GetMapping("/")
	public String showOrderList(Model model) {

		model.addAttribute("orders", orderRepository.findAll());
		
		return "show_order_list";
	}
	
	/*@PostMapping("/new_order")
	public String crearAnuncion(Model model, String nombre, String asunto, String comentario) {

		OrderItem anuncio = new OrderItem(nombre, false);
		model.addAttribute("anuncio", anuncio);
		anuncioRepository.save(anuncio);
		
		return "anuncio";
	}*/
}