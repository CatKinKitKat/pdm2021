package com.ipbeja.easymed.Adapters;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ipbeja.easymed.Activities.Popout.PopupMeds;
import com.ipbeja.easymed.FireStore.Pharmacy;
import com.ipbeja.easymed.R;

import java.util.List;

/**
 * The type Meds recycler view adapter.
 */
public class PharmacyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * The Med list.
     */
    private final List<Pharmacy> medList;

    /**
     * Instantiates a new Meds recycler view adapter.
     *
     * @param medList the med list
     */
    public PharmacyRecyclerViewAdapter(List<Pharmacy> medList) {
        this.medList = medList;
    }


    /**
     * On create view holder recycler view . view holder.
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return the recycler view . view holder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fbrow_pharmacy, parent, false);
        return new viewHolderClass(view);
    }

    /**
     * On bind view holder.
     *
     * @param holder   the holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Pharmacy pharmacy = this.medList.get(position);
        ((PharmacyRecyclerViewAdapter.viewHolderClass) holder).name.setText(pharmacy.getName());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        if (pharmacy.getFurl() != null) {

            StorageReference picturePath = storageRef.child(pharmacy.getFurl());
            picturePath.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                ((PharmacyRecyclerViewAdapter.viewHolderClass) holder).img.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            });

        }

        ((PharmacyRecyclerViewAdapter.viewHolderClass) holder).img.setOnClickListener(v -> {

            Intent i = new Intent(v.getContext(), PopupMeds.class);
            i.putExtra("url", pharmacy.getUrl());
            v.getContext().startActivity(i);

        });

    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return this.medList.size();
    }


    /**
     * The type View holder class.
     */
    public class viewHolderClass extends RecyclerView.ViewHolder {

        /**
         * The Name.
         */
        TextView name;
        /**
         * The Img.
         */
        ImageView img;

        /**
         * Instantiates a new View holder class.
         *
         * @param itemView the item view
         */
        public viewHolderClass(final View itemView) {

            super(itemView);
            this.name = itemView.findViewById(R.id.medText);
            this.img = itemView.findViewById(R.id.img1);
        }
    }

}