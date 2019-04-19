package model;

import model.changeapi.ChangeableBase;

import java.util.Date;

public class SignupCredentials extends ChangeableBase {
    String username;
    String name;
    String email;
    String password;
    Long adhaar;
    Long BCode;
    Long accountNumber;
    String address;
    String cpassword;
    Date dob;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyListeners();
    }

    public String getCPassword() {
        return cpassword;
    }

    public void setCPassword(String cpassword) {
        this.cpassword = cpassword;
        notifyListeners();
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
        notifyListeners();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyListeners();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyListeners();
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

    public Long getAdhaar() {
        return adhaar;
    }

    public void setAdhaar(Long adhaar) {
        this.adhaar = adhaar;
        notifyListeners();
    }

    public Long getBCode() {
        return BCode;
    }

    public void setBCode(Long BCode) {
        this.BCode = BCode;
        notifyListeners();
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
        notifyListeners();
    }

}
