package com.example.hotelnfc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemRoomAdapter extends RecyclerView.Adapter<ItemRoomAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<Item_room> arrayList;
    RecyclerView recyclerView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    MainActivity mainActivity;

    ItemClick itemClick;

    public ItemRoomAdapter(Context context, ArrayList<Item_room> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public interface ItemClick {
        void OnClick(View v, int position);
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
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
    public void onBindViewHolder(@NonNull final ItemRoomAdapter.CustomViewHolder holder, final int position) {

        holder.room_picture.setImageResource(arrayList.get(position).getRoom_picture());
        holder.room_class.setText(arrayList.get(position).getRoom_class());
        holder.room_price.setText(arrayList.get(position).getRoom_price());

        holder.itemView.setTag(position);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (itemClick != null) {
                   itemClick.OnClick(v, position);
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView room_picture;
        protected TextView room_class, room_price;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            room_picture = (ImageView) itemView.findViewById(R.id.room_picture);
            room_class = (TextView) itemView.findViewById(R.id.room_class);
            room_price = (TextView) itemView.findViewById(R.id.room_price);


        }
    }
}
