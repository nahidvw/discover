package com.example.doordashdiscover.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.doordashdiscover.R;
import com.example.doordashdiscover.models.Restaurant;

import java.util.List;

public class RestaurantRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Restaurant> mRestaurants;
    private OnRestaurantClickListener mOnRestaurantClickListener;

    public RestaurantRecyclerAdapter(OnRestaurantClickListener mOnRestaurantClickListener) {
        this.mOnRestaurantClickListener = mOnRestaurantClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantViewHolder(view, mOnRestaurantClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RestaurantViewHolder vh = (RestaurantViewHolder) holder;
        vh.name.setText(mRestaurants.get(position).getName());
        vh.description.setText(mRestaurants.get(position).getDescription());
        //todo status, deliveryFee

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
        Glide.with(vh.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(mRestaurants.get(position).getCover_img_url())
                .into(vh.image);
    }

    @Override
    public int getItemCount() {
        if(mRestaurants != null) {
            return mRestaurants.size();
        }
        return 0;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        mRestaurants = restaurants;
        notifyDataSetChanged();
    }
}
