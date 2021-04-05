package com.example.doordashdiscover.adapters;

import android.content.Context;
import android.text.TextUtils;
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

    private static final int RESTAURANT_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int EXHAUSTED_TYPE = 3;
    private static final String EXHAUSTED_ID = "ExhaustedId";

    private List<Restaurant> mRestaurants;
    private final OnRestaurantClickListener mOnRestaurantClickListener;
    private final Context mContext;

    public RestaurantRecyclerAdapter(OnRestaurantClickListener mOnRestaurantClickListener, Context context) {
        this.mOnRestaurantClickListener = mOnRestaurantClickListener;
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case LOADING_TYPE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_loading_list_item, parent, false);
                return new SpecialItemViewHolder(view);
            }

            case EXHAUSTED_TYPE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_query_exhausted_item, parent, false);
                return new SpecialItemViewHolder(view);
            }

            case RESTAURANT_TYPE:
            default: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.restaurant_item, parent, false);
                return new RestaurantViewHolder(view, mOnRestaurantClickListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType == RESTAURANT_TYPE) {
            RestaurantViewHolder vh = (RestaurantViewHolder) holder;
            vh.name.setText(mRestaurants.get(position).getName());
            vh.description.setText(mRestaurants.get(position).getDescription());
            vh.status.setText(TextUtils.isEmpty(mRestaurants.get(position).getDisplayStatus())
                    ? "" : getContext().getResources().getString(R.string.status_string, mRestaurants.get(position).getDisplayStatus()));
            vh.deliveryFee.setText(String.valueOf(mRestaurants.get(position).getDisplay_delivery_fee()));

            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
            Glide.with(vh.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mRestaurants.get(position).getCover_img_url())
                    .into(vh.image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position != 0
                && position == mRestaurants.size() - 1
                && !mRestaurants.get(position).getId().equals(EXHAUSTED_ID)) {
            return LOADING_TYPE;
        } else if(mRestaurants.get(position).getId().equals(EXHAUSTED_ID)) {
            return EXHAUSTED_TYPE;
        } else {
            return RESTAURANT_TYPE;
        }
    }

    public void setQueryExhausted() {
        if(!mRestaurants.get(mRestaurants.size()-1).getId().equals(EXHAUSTED_ID)) {
            Restaurant exhaustedRestaurant = new Restaurant();
            exhaustedRestaurant.setId(EXHAUSTED_ID);
            mRestaurants.add(exhaustedRestaurant);
            notifyDataSetChanged();
        }
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

    public Restaurant getSelectedRestaurant(int position) {
        if(mRestaurants != null && mRestaurants.size() > 0) {
            return mRestaurants.get(position);
        }
        return null;
    }
}
