package com.example.hotelnfc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemRoomAdapter extends RecyclerView.Adapter<ItemRoomAdapter.CustomViewHolder> {

    private ArrayList<Item_room> arrayList;

    public ItemRoomAdapter(ArrayList<Item_room> arrayList) {
        this.arrayList = arrayList;
    }

    //리스트뷰가 생성될때의 생명주기
    @NonNull
    @Override
    public ItemRoomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);

        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    //실제 추가될때의 생명주기
    @Override
    public void onBindViewHolder(@NonNull final ItemRoomAdapter.CustomViewHolder holder, int position) {
        holder.room_picture.setImageResource(arrayList.get(position).getRoom_picture());
        holder.room_class.setText(arrayList.get(position).getRoom_class());
        holder.room_price.setText(arrayList.get(position).getRoom_price());

        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curPrice = holder.room_price.getText().toString();
                Toast.makeText(v.getContext(), curPrice, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView room_picture;
        protected TextView room_class, room_price;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.room_picture = (ImageView) itemView.findViewById(R.id.room_picture);
            this.room_class = (TextView) itemView.findViewById(R.id.room_class);
            this.room_price = (TextView) itemView.findViewById(R.id.room_price);
        }
    }
}
