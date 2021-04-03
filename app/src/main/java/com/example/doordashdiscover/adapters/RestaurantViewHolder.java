package com.example.doordashdiscover.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doordashdiscover.R;

public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView name, description, deliveryFee, status;
    AppCompatImageView image;
    OnRestaurantClickListener onRestaurantClickListener;

    public RestaurantViewHolder(@NonNull View itemView, OnRestaurantClickListener onRestaurantClickListener) {
        super(itemView);
        image = itemView.findViewById(R.id.restaurant_cover_image);
        name = itemView.findViewById(R.id.restaurant_item_name);
        description = itemView.findViewById(R.id.restaurant_item_description);
        deliveryFee = itemView.findViewById(R.id.restaurant_item_delivery_fee);
        status = itemView.findViewById(R.id.restaurant_item_status);

        this.onRestaurantClickListener = onRestaurantClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onRestaurantClickListener.onRestaurantClick(getAdapterPosition());
    }
}
