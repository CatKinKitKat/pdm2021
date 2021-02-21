package com.ipbeja.easymed.FireStore;

/**
 * The type Pharmacy.
 */
public class Pharmacy {

    /**
     * The Name.
     */
    String name, /**
     * The Furl.
     */
    furl,


    /**
     * The Url.
     */
    url;
    /**
     * The Fire store id.
     */
    String fireStoreId;

    /**
     * Instantiates a new Pharmacy.
     */
    public Pharmacy() {
    }

    /**
     * Instantiates a new Pharmacy.
     *
     * @param name        the name
     * @param furl        the furl
     * @param fireStoreId the fire store id
     * @param url         the url
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

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

}
