package com.saffron.tabon.contacts;

/**
 * Created by saffron on 3/27/2018.
 */

public class ListGetter {

    private int img;
    private String no;
    private String banner;
    private String itemlist;
    private String price;
    private String quantity;
    private String offer;
    private String tag;

    public ListGetter(int img, String banner, String no,String itemlist,String quantity,String price,String offer) {

        this.img = img;
        this.banner = banner;
        this.no = no;
        this.offer=offer;
        this.itemlist = itemlist;
        this.quantity = quantity;
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public String getBanner() {
        return banner;
    }

    public String getOffer() {
        return offer;
    }

    public String getNo() {
        return no;
    }

    public String getItemlist() {
        return itemlist;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }


}
