package cloud.nimvps.exercises.salestaxes.core.model;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

	// The id of the receipt
	private String id;

	// The list of items contained in the receipt
	private final List<Item> items = new ArrayList<>();

	public Receipt() {
	}

	public Receipt(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}

	public float getTotalSalesTaxes() {
		return (float) items.stream().mapToDouble(item -> item.getSalesTaxesAmount()).sum();
	}

	public float getTotalImportTaxes() {
		return (float) items.stream().mapToDouble(item -> item.getImportTaxesAmount()).sum();
	}

	public float getTotalTaxes() {
		return (float) items.stream().mapToDouble(item -> item.getImportTaxesAmount() + item.getSalesTaxesAmount()).sum();
	}

	public float getTotalPrice() {
		return (float) items.stream().mapToDouble(item -> item.getPrice() + item.getImportTaxesAmount() + item.getSalesTaxesAmount()).sum();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receipt other = (Receipt) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Receipt [id=" + id + ", items=" + items + "]";
	}

}
