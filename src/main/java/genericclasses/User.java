package genericclasses;

public class User {
    int id;
    String username,email;

    public User(int id, String usernamein, String emailin){
        this.id=id;
        this.username=usernamein;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
