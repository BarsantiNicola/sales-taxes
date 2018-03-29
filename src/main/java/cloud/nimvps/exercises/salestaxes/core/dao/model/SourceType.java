package cloud.nimvps.exercises.salestaxes.core.dao.model;

public class SourceType {

	// Name of the type
	private String name;

	// True if taxed, false otherwise
	private boolean taxed;

	public SourceType() {
	}

	public SourceType(String name, boolean taxed) {
		this.name = name;
		this.taxed = taxed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isTaxed() {
		return taxed;
	}

	public void setTaxed(boolean taxed) {
		this.taxed = taxed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (taxed ? 1231 : 1237);
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
		SourceType other = (SourceType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (taxed != other.taxed)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SourceType [name=" + name + ", taxed=" + taxed + "]";
	}

}
