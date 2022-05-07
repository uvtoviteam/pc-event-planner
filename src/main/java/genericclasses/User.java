package genericclasses;

public class User {
    int id;
    String username,password,email;

    public User(int id, String usernamein, String passwordin, String emailin){
        this.id=id;
        this.username=usernamein;
        this.password=passwordin;
        this.email=emailin;
    }

    public User(String username){
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
