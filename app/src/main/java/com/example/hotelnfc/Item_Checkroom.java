package com.example.hotelnfc;

import android.widget.ImageView;
import android.widget.TextView;

public class Item_Checkroom {

    private int check_img;
    private String check_roomclass, check_checkin, check_checkout, check_price;
    private String book_userphone, controlnum1, controlnum2, controlnum3;
    private String room_num;
    private String book_date;

    private String bookID; // book_date YY-MM-DDT시:분:초 로 이루어져 있음

    public Item_Checkroom(int check_img, String check_roomclass, String check_checkin, String check_checkout, String check_price,
                          String book_userphone, String controlnum1, String controlnum2, String controlnum3, String room_num,
                          String book_date, String bookID) {
        this.check_img = check_img;
        this.check_roomclass = check_roomclass;
        this.check_checkin = check_checkin;
        this.check_checkout = check_checkout;
        this.check_price = check_price;
        this.book_userphone = book_userphone;
        this.controlnum1 = controlnum1;
        this.controlnum2 = controlnum2;
        this.controlnum3 = controlnum3;
        this.room_num = room_num;
        this.book_date = book_date;
        this.bookID = bookID;
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

    public String getBook_userphone() {
        return book_userphone;
    }

    public void setBook_userphone(String book_userphone) {
        this.book_userphone = book_userphone;
    }

    public String getControlnum1() {
        return controlnum1;
    }

    public void setControlnum1(String controlnum1) {
        this.controlnum1 = controlnum1;
    }

    public String getControlnum2() {
        return controlnum2;
    }

    public void setControlnum2(String controlnum2) {
        this.controlnum2 = controlnum2;
    }

    public String getControlnum3() {
        return controlnum3;
    }

    public void setControlnum3(String controlnum3) {
        this.controlnum3 = controlnum3;
    }

    public String getRoom_num() {
        return room_num;
    }

    public void setRoom_num(String room_num) {
        this.room_num = room_num;
    }

    public String getBook_date() {
        return book_date;
    }

    public void setBook_date(String book_date) {
        this.book_date = book_date;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

}
