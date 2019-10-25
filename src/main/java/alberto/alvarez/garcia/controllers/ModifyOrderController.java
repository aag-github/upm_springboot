
package alberto.alvarez.garcia.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import alberto.alvarez.garcia.models.Order;
import alberto.alvarez.garcia.models.OrderItem;
import alberto.alvarez.garcia.models.OrderRepository;

@Controller
public class ModifyOrderController {
	@Autowired
	private OrderRepository orderRepository;
		
	@PostConstruct
	void init() {
	}
	
	@GetMapping("/new")
	public String newOrder(Model model) {
		return "edit_order_form";
	}
	
	@GetMapping("/edit/{id}")
	public String editOrder(Model model, @PathVariable Long id) {
		model.addAttribute("order", orderRepository.findById(id).get());
		return "edit_order_form";
	}

	@GetMapping("/checkoff/{id}")
	public String checkoffOrder(Model model, @PathVariable Long id) {
		model.addAttribute("order", orderRepository.findById(id).get());
		model.addAttribute("checkoff", true);		
		return "edit_order_form";
	}

	@PostMapping("/create")
	public String createOrder(Model model, @RequestParam String title, @RequestParam List<String> newitem) {

		Order order = buildNewOrder(title, newitem);
		orderRepository.save(order);
		String id = order.getId().toString();
		return "redirect:/show/" + id;
	}
	
	@PostMapping("/update/{id}")
	public String updateOrder(Model model,
								@PathVariable Long id,
								@RequestParam Map<String,String> allParams,
								@RequestParam(required=false) List<String> newitem) {
		Optional<Order> oldOrder = orderRepository.findById(id);
		updateOrder(allParams, newitem, oldOrder.get());
		orderRepository.save(oldOrder.get());		
		
		return "redirect:/show/" + oldOrder.get().getId();
	}

	@PostMapping("/update_checked/{id}")
	public String updateChecked(Model model, @PathVariable Long id, @RequestParam Map<String,String> allParams) {
		Optional<Order> oldOrder = orderRepository.findById(id);
		updateCheckedOffOrder(allParams, oldOrder.get());
		orderRepository.save(oldOrder.get());		
		
		return "redirect:/show/" + oldOrder.get().getId();
	}
	
	@GetMapping("/delete/{id}")
	public String deleteOrder(Model model, @PathVariable Long id) {
		Optional<Order> order = orderRepository.findById(id); 

		orderRepository.delete(order.get());
		
		return "redirect:/";
	}

	private Order buildNewOrder(String title, List<String>newItems) {
		Order order = new Order(title);
		addNewItems(newItems, order);
		return order;
	}

	private void updateOrder(Map<String,String> allParams, List<String> newItems, Order order) {
		order.setTitle(allParams.get("title"));	
		
		Pattern itemNamePattern = Pattern.compile("^item-([0-9])+$");
		for(String name : allParams.keySet()) {
			Matcher itemMatch = itemNamePattern.matcher(name);
			if (itemMatch.matches()) {
				order.getItems().get(Integer.parseInt(itemMatch.group(1))-1).setName(allParams.get(name));
			}
		}
		
		updateDeleted(allParams, order);
		if(newItems != null) {
			addNewItems(newItems, order);
		}
	}
	
	private void updateCheckedOffOrder(Map<String,String> allParams, Order order) {
		for(int i = 0; i < order.getItems().size(); i++) {
			order.getItems().get(i).setChecked(allParams.containsKey("checked-" + (i + 1)));
		}
		updateDeleted(allParams, order);
	}	

	
	private void updateDeleted(Map<String,String> allParams, Order order) {
		List<OrderItem> itemsToDelete = new ArrayList<>();
		for(int i = 0; i < order.getItems().size(); i++) {
			if (allParams.get("delete-" + (i + 1)).equals("true")) {
				itemsToDelete.add(order.getItems().get(i));
			}
		}		
		order.getItems().removeAll(itemsToDelete);
	}
	
	private Order addNewItems(List<String>newItems, Order order) {
		for(String name : newItems) {
			if(name.trim() != "") {
				order.getItems().add(new OrderItem(name, false));
			}
		}
		return order;
	}
	
}