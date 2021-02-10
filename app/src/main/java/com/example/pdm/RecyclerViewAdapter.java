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

//Creating and configuring this class (ADAPTER)
public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.ImageViewHolder> {

    //Variables
    Context mContext;
    List<row> mData;

    //Constructor
    public RecyclerViewAdapter(Context mContext, List<row> mData){

        this.mContext = mContext;
        this.mData = mData;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ImageView Holder - Binding views
        //Creating RecyclerView Item Layout
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false);
        return new ImageViewHolder(view);
    }

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

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener{

        ImageView img;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView);

            //Click Listeners
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            //Put here the code of click events on items
            if (getLayoutPosition() == 0){

                Intent intent = new Intent(mContext, reminderActivity.class);
                mContext.startActivity(intent);

            }

            if (getLayoutPosition() == 1){

                Intent intent = new Intent(mContext, pharmacyLayout.class);
                mContext.startActivity(intent);

            }

            if (getLayoutPosition() == 2){

                Intent intent = new Intent(mContext, hospitalLayout.class);
                mContext.startActivity(intent);

            }

            if (getLayoutPosition() == 3){

                Intent intent = new Intent(mContext, doctorsLayout.class);
                mContext.startActivity(intent);

            }




        }
    }
}
