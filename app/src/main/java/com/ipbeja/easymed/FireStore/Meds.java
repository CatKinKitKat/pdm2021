package com.ipbeja.easymed.FireStore;

public class Meds {

    String name, furl, price;
    String fireStoreId;

    public Meds() {
    }

    public Meds(String name, String furl, String price, String fireStoreId) {
        this.name = name;
        this.furl = furl;
        this.price = price;
        this.fireStoreId = fireStoreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFurl() {
        return furl;
    }

    public void setFurl(String furl) {
        this.furl = furl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFireStoreId() {
        return fireStoreId;
    }

    public void setFireStoreId(String fireStoreId) {
        this.fireStoreId = fireStoreId;
    }
}
