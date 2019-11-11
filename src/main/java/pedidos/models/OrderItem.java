package pedidos.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private Boolean checked;

	public OrderItem() {
	}

	public OrderItem(String nombre, Boolean checked) {
		this.name = nombre;
		this.checked = checked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		String itemString = new String();
		itemString += "    - " + this.id + ": " + this.name + " " + (this.checked ? "X" : "") + "\n";
		return itemString;
	}
}
