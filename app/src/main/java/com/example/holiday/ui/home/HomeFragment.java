package com.example.holiday.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holiday.AddTrip;
import com.example.holiday.EditInfo;
import com.example.holiday.MainActivity;
import com.example.holiday.R;
import com.example.holiday.Room.entity.Trips;
import com.example.holiday.Trip;
import com.example.holiday.TripViewModel;
import com.example.holiday.ViewTrip;
import com.example.holiday.ui.TripAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements TripAdapter.OnTripListener {

    private HomeViewModel homeViewModel;
    //public static TripViewModel tripViewModel;
    final public TripAdapter adapter=new TripAdapter(this);
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView =(RecyclerView) root.findViewById(R.id.recycleView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        recyclerView.setAdapter(adapter);
       if(MainActivity.tripViewModel!=null)
       MainActivity.tripViewModel.getAllTrips().observe((LifecycleOwner) getContext(), new Observer<List<Trips>>() {
            @Override
            public void onChanged(List<Trips> trips) {
                adapter.SetTrips(trips);


            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                MainActivity.tripViewModel.delete(adapter.getTripAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
        return root;

    }


    @Override
    public void onTripClick(int position){
        Intent intent=new Intent(getActivity(), ViewTrip.class);
        int c=position;

        intent.putExtra("trips",c);
        startActivity(intent);
    }

    @Override
    public void onTripLongClick(int position) {
        Intent intent=new Intent(getActivity(), EditInfo.class);
        intent.putExtra("trips",position);
        startActivityForResult(intent,21);


    }

    @Override
    public void onBookmark(int position) {
        MainActivity.tripViewModel.getTrip(position).setBookmark(true);
        MainActivity.tripViewModel.update(  MainActivity.tripViewModel.getTrip(position));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 21) {
            Trip t=new Trip();
            t=data.getParcelableExtra("trip");
            int position=data.getIntExtra("pos",0);
            Trips trips=new Trips();
            trips.setTripId(position+1);
            trips.setTripName(t.getTripName());
            trips.setDestination(t.getDestination());
            trips.setPrice(t.getPrice());
            trips.setTripType(t.getTripType());
            trips.setStartDate(t.getStartDate());
            trips.setEndDate(t.getEndDate());
            trips.setRating(t.getRatingBar());
            trips.setBookmark(false);
            MainActivity.tripViewModel.update(trips);

        }
    }
}