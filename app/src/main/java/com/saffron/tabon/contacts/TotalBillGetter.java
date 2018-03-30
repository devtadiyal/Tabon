package com.saffron.tabon.contacts;

/**
 * Created by saffron on 3/29/2018.
 */

public class TotalBillGetter {

    private int img;
    private String no;
    private String banner;


    public TotalBillGetter(int img, String banner, String no) {

        this.img = img;
        this.banner = banner;
        this.no = no;

    }

    public int getImg() {
        return img;
    }

    public String getBanner() {
        return banner;
    }


    public String getNo() {
        return no;
    }
}
