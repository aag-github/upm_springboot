
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
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/create")
	public String createOrder(Model model, @RequestParam Map<String,String> allParams) {

		Order order = buildNewOrder(allParams);
		orderRepository.save(order);
		String id = order.getId().toString();
		return "redirect:/show/" + id;
	}
	
	@GetMapping("/update/{id}")
	public String updateOrder(Model model, @PathVariable Long id, @RequestParam Map<String,String> allParams) {
		Optional<Order> oldOrder = orderRepository.findById(id);
		updateOrder(allParams, oldOrder.get());
		orderRepository.save(oldOrder.get());		
		
		return "redirect:/show/" + oldOrder.get().getId();
	}

	@GetMapping("/update_checked/{id}")
	public String updateChecked(Model model, @PathVariable Long id, @RequestParam Map<String,String> allParams) {
		Optional<Order> oldOrder = orderRepository.findById(id);
		updateChecked(allParams, oldOrder.get());
		orderRepository.save(oldOrder.get());		
		
		return "redirect:/show/" + oldOrder.get().getId();
	}
	
	@GetMapping("/delete/{id}")
	public String deleteOrder(Model model, @PathVariable Long id) {
		Optional<Order> order = orderRepository.findById(id); 

		orderRepository.delete(order.get());
		
		return "redirect:/";
	}

	private Order buildNewOrder(Map<String,String> allParams) {
		Order order = new Order(allParams.get("title"));
		for(String name : allParams.keySet()) {
			if (name.startsWith("item-")) {
				order.getItems().add(new OrderItem(allParams.get(name), false));
			}
		}
		return order;
	}
	private void updateOrder(Map<String,String> allParams, Order order) {
		order.setTitle(allParams.get("title"));
		
		order.getItems().clear();
		
		Pattern itemNamePattern = Pattern.compile("^item-([0-9])+$");
		HashMap<String, String> newItems = new HashMap<>();
		for(String name : allParams.keySet()) {
			Matcher itemMatch = itemNamePattern.matcher(name);
			if (itemMatch.matches()) {
				newItems.put(itemMatch.group(1), allParams.get(name));
			}
		}
	
		for (String newItem : newItems.keySet()) {
			String checked = allParams.get("checked-" + newItem);
			String name = newItems.get(newItem);
			OrderItem item = new OrderItem(name,
										   checked != null ? checked.equals("on") : false);
			order.getItems().add(item);
		}

	}
	
	private void updateChecked(Map<String,String> allParams, Order order) {
		for(int i = 0; i < order.getItems().size(); i++) {
			order.getItems().get(i).setChecked(allParams.containsKey("checked-" + (i + 1)));
		}
		List<OrderItem> itemsToDelete = new ArrayList<>();
		for(int i = 0; i < order.getItems().size(); i++) {
			if (allParams.get("delete-" + (i + 1)).equals("true")) {
				itemsToDelete.add(order.getItems().get(i));
			}
		}		
		order.getItems().removeAll(itemsToDelete);
	}	
	
}