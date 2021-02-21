package com.ipbeja.easymed.FireStore;

/**
 * The type Users.
 */
public class Users {

    /**
     * The Email.
     */
    private String email;

    /**
     * The F name.
     */
    private String fName;

    /**
     * The Phone.
     */
    private String phone;

    /**
     * The Profile image path.
     */
    private String profileImagePath;

    /**
     * The Fire store id.
     */
    private String fireStoreID;

    /**
     * The Auth id.
     */
    private String authID;

    /**
     * Instantiates a new Users.
     *
     * @param email            the email
     * @param fName            the f name
     * @param phone            the phone
     * @param profileImagePath the profile image path
     * @param authID           the auth id
     */
    public Users(String email, String fName, String phone, String profileImagePath, String authID) {
        this.email = email;
        this.fName = fName;
        this.phone = phone;
        this.profileImagePath = profileImagePath;
        this.authID = authID;
    }

    /**
     * Instantiates a new Users.
     */
    public Users() {
    }

    /**
     * Gets auth id.
     *
     * @return the auth id
     */
    public String getAuthID() {
        return authID;
    }

    /**
     * Sets auth id.
     *
     * @param authID the auth id
     */
    public void setAuthID(String authID) {
        this.authID = authID;
    }

    /**
     * Gets fire store id.
     *
     * @return the fire store id
     */
    public String getFireStoreID() {
        return fireStoreID;
    }

    /**
     * Sets fire store id.
     *
     * @param fireStoreID the fire store id
     */
    public void setFireStoreID(String fireStoreID) {
        this.fireStoreID = fireStoreID;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getfName() {
        return fName;
    }

    /**
     * Sets name.
     *
     * @param fName the f name
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets profile image path.
     *
     * @return the profile image path
     */
    public String getProfileImagePath() {
        return profileImagePath;
    }

    /**
     * Sets profile image path.
     *
     * @param profileImagePath the profile image path
     */
    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}
