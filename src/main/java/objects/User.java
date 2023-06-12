package objects;

public class User {
    private int id;
    private int user_role;
    private String name;
    private String surname;
    private String password;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User() {
    }

    public User(int id, int user_role, String name, String surname, String password, String username) {
        this.id = id;
        this.user_role = user_role;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
    }
}
