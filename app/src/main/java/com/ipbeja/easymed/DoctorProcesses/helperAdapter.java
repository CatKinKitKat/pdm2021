package com.ipbeja.easymed.DoctorProcesses;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ipbeja.easymed.FireStore.Doctors;
import com.ipbeja.easymed.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class helperAdapter extends RecyclerView.Adapter {
    List<Doctors> doctorsList;

    public helperAdapter(List<Doctors> doctorsList) {
        this.doctorsList = doctorsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fbrow, parent, false);
        viewHolderClass viewHolderClass = new viewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        viewHolderClass viewHolderClass = (viewHolderClass)holder;

        Doctors doctors = doctorsList.get(position);
        ((helperAdapter.viewHolderClass) holder).name.setText(doctors.getName());
        ((helperAdapter.viewHolderClass) holder).speciality.setText(doctors.getSpeciality());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        if(doctors.getFurl() != null){

            StorageReference picturePath = storageRef.child(doctors.getFurl());
            picturePath.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                ((viewHolderClass) holder).img.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            });

        }

    }

    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

    public class viewHolderClass extends RecyclerView.ViewHolder{

        TextView name, speciality;
        CircleImageView img;


        public viewHolderClass(@NonNull View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.nameText);
            speciality = itemView.findViewById(R.id.specialityText);
            img = itemView.findViewById(R.id.img1);
        }
    }

}
