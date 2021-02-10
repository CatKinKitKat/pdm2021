package com.example.pdm;

/**
 * The type Row.
 */
public class row {

    /**
     * The Img.
     */
//Creating row class as a model class
    private int img;

    /**
     * Instantiates a new Row.
     *
     * @param img the img
     */
//constructor
    public row(int img) {
        this.img = img;
    }

    //Getters & Setters

    /**
     * Gets img.
     *
     * @return the img
     */
    public int getImg() {
        return img;
    }

    /**
     * Sets img.
     *
     * @param img the img
     */
    public void setImg(int img) {
        this.img = img;
    }


}
