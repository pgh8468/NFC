package com.example.hotelnfc;

import android.widget.ImageView;
import android.widget.TextView;

public class Item_Checkroom {

    private int check_img;
    private String check_roomclass, check_checkin, check_checkout, check_price;

    public Item_Checkroom(int check_img, String check_roomclass, String check_checkin, String check_checkout, String check_price) {
        this.check_img = check_img;
        this.check_roomclass = check_roomclass;
        this.check_checkin = check_checkin;
        this.check_checkout = check_checkout;
        this.check_price = check_price;
    }

    public int getCheck_img() {
        return check_img;
    }

    public void setCheck_img(int check_img) {
        this.check_img = check_img;
    }

    public String getCheck_roomclass() {
        return check_roomclass;
    }

    public void setCheck_roomclass(String check_roomclass) {
        this.check_roomclass = check_roomclass;
    }

    public String getCheck_checkin() {
        return check_checkin;
    }

    public void setCheck_checkin(String check_checkin) {
        this.check_checkin = check_checkin;
    }

    public String getCheck_checkout() {
        return check_checkout;
    }

    public void setCheck_checkout(String check_checkout) {
        this.check_checkout = check_checkout;
    }

    public String getCheck_price() {
        return check_price;
    }

    public void setCheck_price(String check_price) {
        this.check_price = check_price;
    }
}
