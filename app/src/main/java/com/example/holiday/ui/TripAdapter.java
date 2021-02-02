package com.example.holiday.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holiday.EditInfo;
import com.example.holiday.MainActivity;
import com.example.holiday.R;
import com.example.holiday.Room.entity.Trips;
import com.example.holiday.Trip;
import com.example.holiday.ui.home.HomeFragment;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripHolder> {
    private List<Trips>trips=new ArrayList<>();
    private OnTripListener monTripListener;
    private Context context;

    public TripAdapter( OnTripListener monTripListener) {

        this.monTripListener = monTripListener;
    }

    @NonNull
    @Override
    public TripHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_item,parent,false);
        return new TripHolder(itemView,monTripListener);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TripHolder holder, int position) {
        Trips  currentTrip=trips.get(position);
        holder.textViewName.setText(currentTrip.getTripName());
        holder.textViewDestination.setText(currentTrip.getDestination());
         holder.price.setText(String.valueOf(currentTrip.getPrice())+'$');
         if(currentTrip.getTripType().equals("City Break"))
             holder.img.setImageResource(R.drawable.paris);
         else
         if(currentTrip.getTripType().equals("Sea Side"))
             holder.img.setImageResource(R.drawable.talgo);
         else
             holder.img.setImageResource(R.drawable.transfagarasan);
        if(getTripAt(position).getBookmark()==true){
            holder.btn.setImageResource(R.drawable.favorite_full);

        }
        else
            holder.btn.setImageResource(R.drawable.favorite_empty);

    }

    @Override
    public int getItemCount() {
        return trips.size();
    }
     public  void SetTrips(List<Trips>trips){
        this.trips=trips;
        notifyDataSetChanged();
     }
     public Trips getTripAt(int position){
        return  trips.get(position);
     }
    public Trips getT(String name,String Destination){
       for(Trips t:trips){
           if(t.getTripName().equals(name)&&t.getDestination().equals(Destination))
               return t;
       }
       return null;
    }

    class TripHolder extends RecyclerView.ViewHolder implements View.OnClickListener   {
        private TextView textViewName;
        private TextView textViewDestination;
        private TextView price;
        private ImageButton btn;
        private ImageView img;
        OnTripListener onTripListener;
        @SuppressLint("ResourceAsColor")
        public TripHolder(@NonNull View itemView, OnTripListener onTripListener) {
            super(itemView);

            textViewName=itemView.findViewById(R.id.text_view_title);
            textViewDestination=itemView.findViewById(R.id.text_view_description);
            price=itemView.findViewById(R.id.text_view_priority);
            img=itemView.findViewById(R.id.image);
            btn=itemView.findViewById(R.id.btn);
            btn.setOnClickListener(v ->{
                Trips t=getTripAt(getAdapterPosition());
                if(!t.getBookmark())
                 t.setBookmark(true);
                else
                    t.setBookmark(false);
                 MainActivity.tripViewModel.update(t);

            });
            this.onTripListener=onTripListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(v -> {
                this.setOnLongClick(itemView);
                return true;
            });



        }



        @Override
        public void onClick(View view) {
           monTripListener.onTripClick(getAdapterPosition());
        }
        public void setOnLongClick(View view){
            monTripListener.onTripLongClick(getAdapterPosition());
        }
        public void onBookmarks(View view){
            monTripListener.onBookmark(getAdapterPosition());
        }


    }
    public interface OnTripListener{
        void onTripClick(int position);
        void onTripLongClick(int position);
        void onBookmark(int position);
    }
}
