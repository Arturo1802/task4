package com.arturo.task4.models;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class User {
    private final String  name;
    private final String mail;
    private final String position ;
    private final Date lastLogin ;
    private final Date registrationDate ;
    private final boolean blocked ;
    private final String pass ;
    private final boolean adminStatus;


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", position='" + position + '\'' +
                ", lastLogin=" + lastLogin +
                ", registrationDate=" + registrationDate +
                ", blocked=" + blocked +
                ", pass='" + pass + '\'' +
                '}';
    }

    public User(String name, String mail, String position, Date lastLogin, Date registrationDate, boolean status, String pass,boolean admin) {
        this.name = name;
        this.mail = mail;
        this.position = position;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        this.lastLogin =  lastLogin ;
        this.registrationDate =  registrationDate    ;
        this.blocked = status;
        this.pass = pass;
        this.adminStatus=admin;
    }
    public User( ) {
        this.name = "";
        this.mail = "";
        this.position = "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        this.lastLogin =  new Date(12,12,12) ;
        this.registrationDate =  new Date(12,12,12)    ;
        this.blocked = false;
        this.pass = "";
        this.adminStatus=false;
    }

    public String getName() {
        return name;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getMail() {
        return mail;
    }

    public String getPosition() {
        return position;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public String getPass() {
        return pass;
    }

    public boolean getAdminStatus() {
        return adminStatus;
    }
}
