package com.ipbeja.easymed.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
 * The type Main recycler view adapter.
 */
public class MainRecyclerViewAdapter extends
        RecyclerView.Adapter<MainRecyclerViewAdapter.ImageViewHolder> {

    /**
     * The M context.
     */
    Context mContext;
    /**
     * The M imgs.
     */
    List<Row> mImgs;
    /**
     * The M txts.
     */
    List<String> mTxts;

    /**
     * Instantiates a new Main recycler view adapter.
     *
     * @param mContext the m context
     * @param mImgs    the m imgs
     * @param mTxts    the m txts
     */
    public MainRecyclerViewAdapter(Context mContext, List<Row> mImgs, List<String> mTxts) {

        this.mContext = mContext;
        this.mImgs = mImgs;
        this.mTxts = mTxts;
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

        View view = LayoutInflater.from(this.mContext).inflate(
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
        holder.textView.setText(this.mTxts.get(position));

        Glide.with(this.mContext)
                .load(this.mImgs.get(position).getImg())
                .into(holder.imageView);
    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return mImgs.size();
    }


    /**
     * The type Image view holder.
     */
    public class ImageViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        /**
         * The Img.
         */
        ImageView imageView;
        TextView textView;

        /**
         * Instantiates a new Image view holder.
         *
         * @param itemView the item view
         */
        public ImageViewHolder(@NonNull View itemView) {

            super(itemView);
            this.textView = itemView.findViewById(R.id.textImgView);
            this.imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }

        /**
         * On click.
         *
         * @param view the view
         */
        @Override
        public void onClick(View view) {

            Intent intent = this.setIntent(getLayoutPosition());
            mContext.startActivity(intent);
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
