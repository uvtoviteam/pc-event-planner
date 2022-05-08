package genericclasses;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class Session {
    User user;

    // Obiect private si static de tip Session.
    private static Session single_instance = null;

    // Constructor privat.
    private Session() {}

    // Metoda statica ce creaza o instanta a clasei singleton.
    public static Session getInstance() {
        if (single_instance == null)
            single_instance = new Session();

        return single_instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static void ButtonConfig(List<Button> buttons){
        for(Button i : buttons){
            i.getStyleClass().add("button-4");
        }
    }
}
