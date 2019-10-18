package alberto.alvarez.garcia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    private String title;
	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderItem> items = new ArrayList<>();
	
	public Order() {
	}

	public Order(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String name) {
		this.title = name;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	
	@Override
	public String toString() {
		String itemString = new String();
		for(OrderItem item : items) {
			itemString += item;
		}
		return "- " + this.id + ": " + this.title + "\n" + itemString;
	}
	
}
