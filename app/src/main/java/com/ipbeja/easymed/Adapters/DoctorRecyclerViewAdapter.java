package com.ipbeja.easymed.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ipbeja.easymed.FireStore.Doctors;
import com.ipbeja.easymed.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * The type Helper adapter.
 */
public class DoctorRecyclerViewAdapter extends RecyclerView.Adapter {
    /**
     * The Doctors list.
     */
    List<Doctors> doctorsList;

    /**
     * Instantiates a new Helper adapter.
     *
     * @param doctorsList the doctors list
     */
    public DoctorRecyclerViewAdapter(List<Doctors> doctorsList) {
        this.doctorsList = doctorsList;
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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fbrow, parent, false);
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

        Doctors doctors = this.doctorsList.get(position);
        ((DoctorRecyclerViewAdapter.viewHolderClass) holder).name.setText(doctors.getName());
        ((DoctorRecyclerViewAdapter.viewHolderClass) holder).speciality.setText(doctors.getSpeciality());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        if (doctors.getFurl() != null) {

            StorageReference picturePath = storageRef.child(doctors.getFurl());
            picturePath.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                ((viewHolderClass) holder).img.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            });

        }

    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return this.doctorsList.size();
    }

    /**
     * The type View holder class.
     */
    public static class viewHolderClass extends RecyclerView.ViewHolder{

        /**
         * The Name.
         */
        TextView name,
        /**
         * The Speciality.
         */
        speciality;
        /**
         * The Img.
         */
        CircleImageView img;

        /**
         * Instantiates a new View holder class.
         *
         * @param itemView the item view
         */
        public viewHolderClass(final View itemView) {

            super(itemView);
            this.name = itemView.findViewById(R.id.nameText);
            this.speciality = itemView.findViewById(R.id.specialityText);
            this.img = itemView.findViewById(R.id.img1);
        }
    }

}
