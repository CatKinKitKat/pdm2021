package com.ipbeja.easymed.FireStore;

/**
 * The type Meds.
 */
public class Pharmacy {

    /**
     * The Name.
     */
    String name, /**
     * The Furl.
     */
    furl,


    url;
    /**
     * The Fire store id.
     */
    String fireStoreId;

    /**
     * Instantiates a new Meds.
     */
    public Pharmacy() {
    }

    /**
     * Instantiates a new Meds.
     *
     * @param name        the name
     * @param furl        the furl
     * @param fireStoreId the fire store id
     */
    public Pharmacy(String name, String furl, String fireStoreId, String url) {
        this.name = name;
        this.furl = furl;
        this.fireStoreId = fireStoreId;
        this.url = url;
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
     * Sets fire store id.
     *
     * @param fireStoreId the fire store id
     */
    public void setFireStoreId(String fireStoreId) {
        this.fireStoreId = fireStoreId;
    }

    public String getUrl() {
        return url;
    }

}
