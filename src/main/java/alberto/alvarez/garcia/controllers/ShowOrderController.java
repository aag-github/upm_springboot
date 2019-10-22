
package alberto.alvarez.garcia.controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import alberto.alvarez.garcia.models.OrderRepository;

@Controller
public class ShowOrderController {
	@Autowired
	private OrderRepository orderRepository;
		
	@PostConstruct
	void init() {	
	}
	
	@GetMapping("/show/{id}")
	public String showOrder(Model model, @PathVariable Long id) {
		model.addAttribute("order", orderRepository.findById(id).get());
		return "show_order";
	}	
}