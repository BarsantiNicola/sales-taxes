package cloud.nimvps.exercises.salestaxes.core.dao.model;

public class SourceItem {

	// The amount of items
	private int amount;

	// The name of the item (without 'import' keyword)
	private String name;

	// The price of the item
	private float price;

	// True if item imported, false otherwise
	private boolean imported;

	public SourceItem() {
	}

	public SourceItem(int amount, String name, float price, boolean imported) {
		this.amount = amount;
		this.name = name;
		this.price = price;
		this.imported = imported;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean isImported() {
		return imported;
	}

	public void setImported(boolean imported) {
		this.imported = imported;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + (imported ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(price);
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
		SourceItem other = (SourceItem) obj;
		if (amount != other.amount)
			return false;
		if (imported != other.imported)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SourceItem [amount=" + amount + ", name=" + name + ", price=" + price + ", imported=" + imported + "]";
	}

}
