package com.example.pdm;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * The type Recycler view adapter.
 */
//Creating and configuring this class (ADAPTER)
public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.ImageViewHolder> {

    /**
     * The M context.
     */
//Variables
    Context mContext;
    /**
     * The M data.
     */
    List<row> mData;

    /**
     * Instantiates a new Recycler view adapter.
     *
     * @param mContext the m context
     * @param mData    the m data
     */
//Constructor
    public RecyclerViewAdapter(Context mContext, List<row> mData) {

        this.mContext = mContext;
        this.mData = mData;

    }

    /**
     * On create view holder image view holder.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the image view holder
     */
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ImageView Holder - Binding views
        //Creating RecyclerView Item Layout
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false);
        return new ImageViewHolder(view);
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        //Adding Glide library to load the images faster
        //Binding image here
        //USING GLIDE LIBRARY

        Glide.
                with(mContext)
                .load(mData.get(position).getImg())
                .into(holder.img);


    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * The type Image view holder.
     */
    public class ImageViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        /**
         * The Img.
         */
        ImageView img;

        /**
         * Instantiates a new Image view holder.
         *
         * @param itemView the item view
         */
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView);

            //Click Listeners
            itemView.setOnClickListener(this);
        }

        /**
         * On click.
         *
         * @param view the view
         */
        @Override
        public void onClick(View view) {

            //Put here the code of click events on items
            if (getLayoutPosition() == 0) {

                Intent intent = new Intent(mContext, reminderActivity.class);
                mContext.startActivity(intent);

            }

            if (getLayoutPosition() == 1) {

                Intent intent = new Intent(mContext, pharmacyLayout.class);
                mContext.startActivity(intent);

            }

            if (getLayoutPosition() == 2) {

                Intent intent = new Intent(mContext, hospitalLayout.class);
                mContext.startActivity(intent);

            }

            if (getLayoutPosition() == 3) {

                Intent intent = new Intent(mContext, doctorsLayout.class);
                mContext.startActivity(intent);

            }


        }
    }
}
