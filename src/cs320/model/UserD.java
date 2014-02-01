package cs320.model;

public class UserD {
    private int id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;

    public UserD(int id, String name, String password, String firstName, String lastName) {
        this.id = id;
        this.userName = name;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserD(int id, String name, String password) {
        this.id = id;
        this.userName = name;
        this.password = password;
    }

    public UserD(int id, String name) {
        this.id = id;
        this.userName = name;
        this.password = null;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object rth) {
        return (this.getName()).equals(((UserD) rth).getName());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
}
