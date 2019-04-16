package model;

import model.changeapi.ChangeableBase;

//Simple POJO, only the setters call notifyListeners() after changing values
public class LoginCredentials extends ChangeableBase {

    private String username;
    private String password;

    public LoginCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyListeners();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyListeners();
    }
}
