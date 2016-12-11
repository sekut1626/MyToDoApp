package com.example.sebastian.mytodoapp.other;

/**
 * Created by sebastian on 24.10.16.
 */

public class ItemData {
    String text;
    Integer imageId;

    public ItemData(Integer imageId) {

        this.imageId = imageId;
    }


    public Integer getImageId() {
        return imageId;
    }
}
