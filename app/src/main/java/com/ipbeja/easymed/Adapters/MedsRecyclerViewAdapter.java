package com.ipbeja.easymed.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ipbeja.easymed.Activities.RvDoctors;
import com.ipbeja.easymed.Activities.RvMeds;
import com.ipbeja.easymed.FireStore.Doctors;
import com.ipbeja.easymed.FireStore.Meds;
import com.ipbeja.easymed.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MedsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Meds> medList;
    private static Bitmap bitmap_transfer;

    public static Bitmap getBitmap_transfer() {
        return bitmap_transfer;
    }

    public static void setBitmap_transfer(Bitmap bitmap_transfer_param) {
        bitmap_transfer = bitmap_transfer_param;
    }

    public MedsRecyclerViewAdapter(List<Meds> medList) {
        this.medList = medList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fbrow_meds, parent, false);
        return new viewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Meds meds = this.medList.get(position);
        ((MedsRecyclerViewAdapter.viewHolderClass) holder).name.setText(meds.getName());
        ((MedsRecyclerViewAdapter.viewHolderClass) holder).price.setText(meds.getPrice());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        if (meds.getFurl() != null) {

            StorageReference picturePath = storageRef.child(meds.getFurl());
            picturePath.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                ((MedsRecyclerViewAdapter.viewHolderClass) holder).img.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            });

        }

        ((MedsRecyclerViewAdapter.viewHolderClass) holder).img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((MedsRecyclerViewAdapter.viewHolderClass) holder).img.buildDrawingCache();
                setBitmap_transfer(((MedsRecyclerViewAdapter.viewHolderClass) holder).img.getDrawingCache());


                Intent i = new Intent(v.getContext(), RvMeds.class);
                i.putExtra("name", meds.getName());
                i.putExtra("price", meds.getPrice());
                v.getContext().startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.medList.size();
    }


    public class viewHolderClass extends RecyclerView.ViewHolder{

        /**
         * The Name.
         */
        TextView name,
        /**
         * The Speciality.
         */
        price;
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
            this.name = itemView.findViewById(R.id.medText);
            this.price = itemView.findViewById(R.id.price);
            this.img = itemView.findViewById(R.id.img1);
        }
    }

}