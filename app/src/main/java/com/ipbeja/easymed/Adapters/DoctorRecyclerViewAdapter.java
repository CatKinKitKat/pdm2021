package com.ipbeja.easymed.Adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ipbeja.easymed.Activities.Popout.PopupDoctors;
import com.ipbeja.easymed.FireStore.Doctors;
import com.ipbeja.easymed.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * The type Doctor recycler view adapter.
 */
public class DoctorRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * The constant bitmap_transfer.
     */
    private static Bitmap bitmap_transfer;
    /**
     * The Doctors list.
     */
    private final List<Doctors> doctorsList;

    /**
     * Instantiates a new Doctor recycler view adapter.
     *
     * @param doctorsList the doctors list
     */
    public DoctorRecyclerViewAdapter(List<Doctors> doctorsList) {
        this.doctorsList = doctorsList;
    }

    /**
     * Gets bitmap transfer.
     *
     * @return the bitmap transfer
     */
    public static Bitmap getBitmap_transfer() {
        return bitmap_transfer;
    }

    /**
     * Sets bitmap transfer.
     *
     * @param bitmap_transfer_param the bitmap transfer param
     */
    public static void setBitmap_transfer(Bitmap bitmap_transfer_param) {
        bitmap_transfer = bitmap_transfer_param;
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

        final Doctors doctors = this.doctorsList.get(position);
        ((DoctorRecyclerViewAdapter.viewHolderClass) holder).name.setText(doctors.getName());
        ((DoctorRecyclerViewAdapter.viewHolderClass) holder).speciality.setText(doctors.getSpeciality());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        if (doctors.getFurl() != null) {

            StorageReference picturePath = storageRef.child(doctors.getFurl());
            picturePath.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> (
                    (viewHolderClass) holder
            ).img.setImageBitmap(
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.length)
            ));

        }


        ((DoctorRecyclerViewAdapter.viewHolderClass) holder).img.setOnClickListener(v -> {

            ((viewHolderClass) holder).img.buildDrawingCache();
            setBitmap_transfer(((viewHolderClass) holder).img.getDrawingCache());

            Intent i = new Intent(v.getContext(), PopupDoctors.class);
            i.putExtra("name", doctors.getName());
            i.putExtra("speciality", doctors.getSpeciality());
            i.putExtra("phoneNumber", doctors.getPhoneNumb());
            i.putExtra("email", doctors.getEmail());
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
        return this.doctorsList.size();
    }

    /**
     * The type View holder class.
     */
    public static class viewHolderClass extends RecyclerView.ViewHolder {

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
