package cloud.nimvps.exercises.salestaxes.core.dao.model;

public class SourceGood {

	// The id of the good
	private Integer id;

	// The name of the good
	private String name;

	// The type of good
	private String type;

	// True if good is taxed, false otherwise (based on type)
	private boolean taxed;

	public SourceGood() {
	}

	public SourceGood(Integer id, String name, String type, boolean taxed) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.taxed = taxed;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		SourceGood other = (SourceGood) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (taxed != other.taxed)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DaoGood [name=" + name + ", type=" + type + ", taxed=" + taxed + "]";
	}

}
