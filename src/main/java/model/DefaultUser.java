package model;

import java.util.Date;

public class DefaultUser implements User {
    private long uid;
    private String name;
    private String address;
    private Date dob;
    private String sex;
    private String username;
    private String password;
    private String email;

    public DefaultUser(long uid, String name, String address, Date dob, String sex, String username, String password, String email) {
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.sex = sex;
        this.username = username;
        this.password = password;
        this.email = email;
    }



    @Override
    public Long uid() {
        return uid;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String Address() {
        return address;
    }

    @Override
    public Date DOB() {
        return dob;
    }

    @Override
    public String Sex() {
        return sex;
    }

    @Override
    public String Username() {
        return username;
    }

    @Override
    public String Password() {
        return password;
    }

    @Override
    public String Email() {
        return email;
    }
}
