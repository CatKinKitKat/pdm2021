package com.ipbeja.easymed.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ipbeja.easymed.Activities.DoctorsActivity;
import com.ipbeja.easymed.Activities.HospitalActivity;
import com.ipbeja.easymed.Activities.PharmacyActivity;
import com.ipbeja.easymed.Activities.ReminderActivity;
import com.ipbeja.easymed.Objects.Row;
import com.ipbeja.easymed.R;

import java.util.List;

/**
 * The type Recycler view adapter.
 */
public class MainRecyclerViewAdapter extends
        RecyclerView.Adapter<MainRecyclerViewAdapter.ImageViewHolder> {

    /**
     * The M context.
     */
    Context mContext;
    /**
     * The M data.
     */
    List<Row> mData;

    /**
     * Instantiates a new Recycler view adapter.
     *
     * @param mContext the m context
     * @param mData    the m data
     */
    public MainRecyclerViewAdapter(Context mContext, List<Row> mData) {

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

        View view = LayoutInflater.from(mContext).inflate(
                R.layout.recyclerview_item, parent, false);

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

        /**
         * Use the GLIDE Library
         *
         * We'll use this library so our images load faster and safer
         */
        Glide.with(mContext)
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

            try {

                Intent intent = this.setIntent(getLayoutPosition());
                mContext.startActivity(intent);
            } catch (Exception ignore) {
            }
        }

        /**
         * Sets intent.
         *
         * @param which the which
         * @return the intent
         */
        private Intent setIntent(int which) {

            switch (which) {
                case 0:
                    return new Intent(mContext, ReminderActivity.class);
                case 1:
                    return new Intent(mContext, PharmacyActivity.class);
                case 2:
                    return new Intent(mContext, HospitalActivity.class);
                case 3:
                    return new Intent(mContext, DoctorsActivity.class);
                default:
                    return null;
            }
        }
    }
}
