package com.ipbeja.easymed;

/**
 * The type Image slider model.
 */
public class ImageSliderModel {

    /**
     * The Image.
     */
    int Image;

    /**
     * Instantiates a new Image slider model.
     */
    public ImageSliderModel() {
    }

    /**
     * Instantiates a new Image slider model.
     *
     * @param image the image
     */
    public ImageSliderModel(int image) {
        Image = image;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public int getImage() {
        return Image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(int image) {
        Image = image;
    }
}
