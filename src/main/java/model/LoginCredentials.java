package model;

import model.changeapi.ChangeableBase;

//Simple POJO, only the setters call notifyListeners() after changing values
public class LoginCredentials extends ChangeableBase {

    private String email;
    private String password;

    public LoginCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
