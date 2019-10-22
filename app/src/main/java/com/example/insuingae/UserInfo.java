package com.example.insuingae;

public class UserInfo {
 private String name;
 private String sosok;
 private String phoneNumber;
 private String gunbun;
 private String jikcheck;

    public String getGunbun() {
        return gunbun;
    }

    public void setGunbun(String gunbun) {
        this.gunbun = gunbun;
    }

    public UserInfo(String name, String sosok, String phoneNumber, String gunbun, String jikcheck) {
        this.name = name;
        this.sosok = sosok;
        this.phoneNumber = phoneNumber;
        this.gunbun = gunbun;
        this.jikcheck = jikcheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSosok() {
        return sosok;
    }

    public void setSosok(String sosok) {
        this.sosok = sosok;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getjikcheck() {
        return jikcheck;
    }

    public void setjikcheck(String jikcheck) {
        this.jikcheck = jikcheck;
    }
}
