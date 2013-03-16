package cs320.model;

public interface UsersC {

	public static final String fstrUsersContent = "allusers";

	public boolean valid(String usr, String pwd);
	public UserD getUserByName(String name);
	public boolean saveUser(String usrName, String passWord, String firstName, String lastName );
}
