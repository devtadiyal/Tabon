package com.saffron.tabon.orders;

/**
 * Created by saffron on 3/23/2018.
 */

public class OrderHistoryGetter {

    private String heading;
    private String banner;
    private String time;
    private String amount;
    private int pic, profilepic;


    public OrderHistoryGetter(int pic, int profilepic, String heading, String banner, String time, String amount) {
        this.heading = heading;
        this.banner = banner;
        this.time = time;
        this.amount = amount;
        this.pic = pic;
        this.profilepic = profilepic;
    }

    public String getHeading() {
        return heading;
    }

    public int getProfilePic() {
        return profilepic;
    }

    public int getPic() {
        return pic;
    }

    public String getAmount() {
        return amount;
    }

    public String getBanner() {
        return banner;
    }

    public String getTime() {
        return time;
    }

}