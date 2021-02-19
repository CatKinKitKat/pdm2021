package com.ipbeja.easymed.FireStore;

/**
 * The type Meds.
 */
public class Meds {

    /**
     * The Name.
     */
    String name, /**
     * The Furl.
     */
    furl, /**
     * The Price.
     */
    price,


    url;
    /**
     * The Fire store id.
     */
    String fireStoreId;

    /**
     * Instantiates a new Meds.
     */
    public Meds() {
    }

    /**
     * Instantiates a new Meds.
     *
     * @param name        the name
     * @param furl        the furl
     * @param price       the price
     * @param fireStoreId the fire store id
     */
    public Meds(String name, String furl, String price, String fireStoreId, String url) {
        this.name = name;
        this.furl = furl;
        this.price = price;
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
     * Sets furl.
     *
     * @param furl the furl
     */
    public void setFurl(String furl) {
        this.furl = furl;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(String price) {
        this.price = price;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
