package com.example.pdm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

/**
 * The type Image slider adapter.
 */
public class ImageSliderAdapter extends SliderViewAdapter<SliderViewHolder> {

    /**
     * The Context.
     */
    Context context;
    /**
     * The Image slider model list.
     */
    List<ImageSliderModel> imageSliderModelList;

    /**
     * Instantiates a new Image slider adapter.
     *
     * @param context              the context
     * @param imageSliderModelList the image slider model list
     */
    public ImageSliderAdapter(Context context, List<ImageSliderModel> imageSliderModelList) {
        this.context = context;
        this.imageSliderModelList = imageSliderModelList;
    }

    /**
     * On create view holder slider view holder.
     *
     * @param parent the parent
     * @return the slider view holder
     */
    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_layout, parent, false);
        return new SliderViewHolder(view);
    }

    /**
     * On bind view holder.
     *
     * @param viewHolder the view holder
     * @param position   the position
     */
    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        viewHolder.sliderImageView.setImageResource(imageSliderModelList.get(position).getImage());
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    @Override
    public int getCount() {
        return imageSliderModelList.size();
    }
}

/**
 * The type Slider view holder.
 */
class SliderViewHolder extends SliderViewAdapter.ViewHolder {
    /**
     * The Slider image view.
     */
    ImageView sliderImageView;

    /**
     * Instantiates a new Slider view holder.
     *
     * @param itemView the item view
     */
    public SliderViewHolder(View itemView) {
        super(itemView);
        sliderImageView = itemView.findViewById(R.id.imageView);
    }
}
