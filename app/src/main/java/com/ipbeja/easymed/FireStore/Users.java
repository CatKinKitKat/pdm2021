package com.ipbeja.easymed.FireStore;

public class Users {

    private String email;
    private String fName;
    private String phone;
    private String profileImagePath;
    private String fireStoreID;
    private String authID;

    public Users(String email, String fName, String phone, String profileImagePath, String authID) {
        this.email = email;
        this.fName = fName;
        this.phone = phone;
        this.profileImagePath = profileImagePath;
        this.authID = authID;
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }

    public Users() {
    }

    public String getFireStoreID() {
        return fireStoreID;
    }

    public void setFireStoreID(String fireStoreID) {
        this.fireStoreID = fireStoreID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}
