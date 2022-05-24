package genericclasses;

public class User {
    int id, type;
    String username,email;
private String password;

    public User(int id, String usernamein, String emailin, int type,String password){
        this.id=id;
        this.username=usernamein;
        this.email=emailin;
        this.type = type;
        this.password=password;
    }

    public User(int id, String usernamein, String emailin, int type){
        this.id=id;
        this.username=usernamein;
        this.email=emailin;
        this.type = type;
        this.password=null;
    }
    public User(String email, String name, int type){

        this.username=name;
        this.email=email;
        this.type = type;
        this.password=null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getType() {
        return type;
    }
}
