package cs320.model;

public class UserD {
	private int id;
	private String name;
	private String password;
	
	public UserD(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
	public UserD(int id, String name) {
		this.id = id;
		this.name = name;
		this.password = null;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object rth) {
		return (this.getName()).equals(((UserD)rth).getName());
	}

	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}	
}