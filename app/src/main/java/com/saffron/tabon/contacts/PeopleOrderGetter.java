package com.saffron.tabon.contacts;

/**
 * Created by saffron on 3/27/2018.
 */

public class PeopleOrderGetter {

    private int img;
    private String heading;
    private String banner;
    private String size;
    private String color;
    private String quantity;
    private String tag;

    public PeopleOrderGetter(int img, String banner) {

        this.img = img;
        this.banner = banner;
    }

    public int getImg() {
        return img;
    }

    public String getBanner() {
        return banner;
    }

}

