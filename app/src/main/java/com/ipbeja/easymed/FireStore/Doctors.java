package com.ipbeja.easymed.FireStore;

/**
 * The type Doctors.
 */
public class Doctors {
    /**
     * The Phone numb.
     */
    String phoneNumb, /**
     * The Email.
     */
    email, /**
     * The Name.
     */
    name, /**
     * The Furl.
     */
    furl, /**
     * The Speciality.
     */
    speciality;
    /**
     * The Fire store id.
     */
    String fireStoreId;

    /**
     * Instantiates a new Doctors.
     */
    public Doctors() {
    }

    /**
     * Instantiates a new Doctors.
     *
     * @param phoneNumb   the phone numb
     * @param email       the email
     * @param name        the name
     * @param furl        the furl
     * @param speciality  the speciality
     * @param fireStoreId the fire store id
     */
    public Doctors(String phoneNumb, String email, String name, String furl, String speciality, String fireStoreId) {
        this.phoneNumb = phoneNumb;
        this.email = email;
        this.name = name;
        this.furl = furl;
        this.speciality = speciality;
        this.fireStoreId = fireStoreId;
    }

    /**
     * Gets phone numb.
     *
     * @return the phone numb
     */
    public String getPhoneNumb() {
        return phoneNumb;
    }

    /**
     * Sets phone numb.
     *
     * @param course the course
     */
    public void setPhoneNumb(String course) {
        this.phoneNumb = course;
    }

    /**
     * Gets fire store id.
     *
     * @return the fire store id
     */
    public String getFireStoreId() {
        return fireStoreId;
    }

    /**
     * Sets fire store id.
     *
     * @param fireStoreId the fire store id
     */
    public void setFireStoreId(String fireStoreId) {
        this.fireStoreId = fireStoreId;
    }

    /**
     * Gets speciality.
     *
     * @return the speciality
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * Sets speciality.
     *
     * @param speciality the speciality
     */
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    /**
     * Phone numb string.
     *
     * @return the string
     */
    public String PhoneNumb() {
        return phoneNumb;
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
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets furl.
     *
     * @return the furl
     */
    public String getFurl() {
        return furl;
    }

    /**
     * Sets furl.
     *
     * @param furl the furl
     */
    public void setFurl(String furl) {
        this.furl = furl;
    }
}