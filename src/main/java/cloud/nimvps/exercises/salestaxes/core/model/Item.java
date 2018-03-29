package cloud.nimvps.exercises.salestaxes.core.model;

public class Item {

	// The amount of items
	private int amount;

	// The name of the items
	private String name;

	// The price of the item
	private float price;

	// True if imported, false otherwise
	private boolean imported;

	// Sales taxes amount applied on the item
	private float salesTaxesAmount;

	// Import taxes amount applied on the stock
	private float importTaxesAmount;

	// Total price of the item including taxes amounts
	private float totalPrice;

	public Item() {
	}

	public Item(int amount, String name, float price, boolean imported, float salesTaxesAmount, float importTaxesAmount, float totalPrice) {
		this.amount = amount;
		this.name = name;
		this.price = price;
		this.imported = imported;
		this.salesTaxesAmount = salesTaxesAmount;
		this.importTaxesAmount = importTaxesAmount;
		this.totalPrice = totalPrice;
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

	public float getSalesTaxesAmount() {
		return salesTaxesAmount;
	}

	public void setSalesTaxesAmount(float salesTaxesAmount) {
		this.salesTaxesAmount = salesTaxesAmount;
	}

	public float getImportTaxesAmount() {
		return importTaxesAmount;
	}

	public void setImportTaxesAmount(float importTaxesAmount) {
		this.importTaxesAmount = importTaxesAmount;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + Float.floatToIntBits(importTaxesAmount);
		result = prime * result + (imported ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result + Float.floatToIntBits(salesTaxesAmount);
		result = prime * result + Float.floatToIntBits(totalPrice);
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
		Item other = (Item) obj;
		if (amount != other.amount)
			return false;
		if (Float.floatToIntBits(importTaxesAmount) != Float.floatToIntBits(other.importTaxesAmount))
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
		if (Float.floatToIntBits(salesTaxesAmount) != Float.floatToIntBits(other.salesTaxesAmount))
			return false;
		if (Float.floatToIntBits(totalPrice) != Float.floatToIntBits(other.totalPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [amount=" + amount + ", name=" + name + ", price=" + price + ", imported=" + imported + ", salesTaxesAmount=" + salesTaxesAmount + ", importTaxesAmount=" + importTaxesAmount + ", totalPrice=" + totalPrice + "]";
	}

}
