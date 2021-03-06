package com.example.rentabike.adapter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentabike.R;
import com.example.rentabike.database.entity.BikeEntity;
import java.util.ArrayList;
import java.util.List;


/**
 * Permet de gérer la liste de vélos avec les actions qui se passent sur les boutons qui sont dans chaque item bike
 *  onEditClick = éditer bike
 *  onDeleteClick = supprimer bike
 *   onItemClick = montre le bike pour permettre de faire une réservation
 */
public class BikeAdapter extends RecyclerView.Adapter<BikeAdapter.BikeHolder>{

    private OnItemClickListener listener;
    private List<BikeEntity> bikeEntities = new ArrayList<>();

    @NonNull
    @Override
    public BikeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bike_item, parent, false);

        return new BikeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BikeHolder holder, int position) {
        BikeEntity currentBike = bikeEntities.get(position);
        holder.textViewName.setText(currentBike.getName());

        Bitmap bitmap = null;

        if(currentBike.getBikePicture() !=null)
            bitmap=  BitmapFactory.decodeByteArray(currentBike.getBikePicture(), 0, currentBike.getBikePicture().length);

        holder.imageViewPicture.setImageBitmap(bitmap);
    }


    @Override
    public int getItemCount() {
        return bikeEntities.size();
    }

    public void setBikeEntities(List<BikeEntity> bikeEntities) {
        this.bikeEntities = bikeEntities;
        notifyDataSetChanged();
    }

    class BikeHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private ImageView imageViewPicture;
        private ImageButton buttonEditBike;
        private ImageButton buttonDeleteBike;

        public BikeHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageViewPicture = itemView.findViewById(R.id.imageViewList);

            buttonEditBike = itemView.findViewById(R.id.button_edit_bike);
            buttonEditBike.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (listener != null & position != RecyclerView.NO_POSITION) {
                        listener.onEditClick(bikeEntities.get(position));
                    }
                }
            });

            buttonDeleteBike = itemView.findViewById(R.id.button_delete_bike);
            buttonDeleteBike.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (listener != null & position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(bikeEntities.get(position));
                    }
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null & position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(bikeEntities.get(position));
                    }
                }
            });
        }


    }

    public interface OnItemClickListener {
        void onItemClick(BikeEntity bike);
        void onEditClick(BikeEntity bike);
        void onDeleteClick(BikeEntity bike);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
