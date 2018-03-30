package com.saffron.tabon.contacts;

/**
 * Created by day on 28/2/18.
 */

public interface OnClickItem {
    void clickItem(int position,boolean isChecked);

    void favourite(int position,String text,boolean isChecked);
}
