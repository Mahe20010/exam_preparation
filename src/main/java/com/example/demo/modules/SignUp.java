package com.example.demo.modules;

public class SignUp {
    private String fullName;
    private String password;
    private String number;
    public String getPassword(){
        return password;
    }

    public String getFullName() {
        return fullName;
    }
    public String getNumber(){
        return number;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
