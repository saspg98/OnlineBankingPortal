package model;

import model.changeapi.ChangeableBase;

import java.util.Date;

public class SignupCredentials extends ChangeableBase {
    String username;
    String name;
    String email;
    String password;
    Long adhaar;
    String IFSC;
    Long accountNumber;
    String address;
    String cpassword;
    Date dob;
    Long phoneNumber;

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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getIFSC() {
        return IFSC;
    }

    public void setIFSC(String IFSC) {
        this.IFSC = IFSC;
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
