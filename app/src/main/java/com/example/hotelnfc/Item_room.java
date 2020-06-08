package com.example.hotelnfc;

import android.graphics.drawable.Drawable;

public class Item_room {

    private String room_class,room_price;
    private int room_picture;

    public Item_room(String room_class, String room_price, int room_picture) {
        this.room_class = room_class;
        this.room_price = room_price;
        this.room_picture = room_picture;
    }

    //Getter
    public String getRoom_class() {
        return room_class;
    }

    public String getRoom_price() {
        return room_price;
    }

    public int getRoom_picture() {
        return room_picture;
    }

    //Setter

    public void setRoom_class(String room_class) {
        this.room_class = room_class;
    }

    public void setRoom_price(String room_price) {
        this.room_price = room_price;
    }

    public void setRoom_picture(int room_picture) {
        this.room_picture = room_picture;
    }
}
