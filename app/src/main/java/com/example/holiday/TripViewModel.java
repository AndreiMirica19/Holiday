package com.example.holiday;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.holiday.Room.TripRepository;
import com.example.holiday.Room.entity.Trips;

import java.util.ArrayList;
import java.util.List;

public class TripViewModel extends AndroidViewModel {
    private TripRepository repository;
    private LiveData<List<Trips>>allTrips;
    public TripViewModel(@NonNull Application application) {
        super(application);
        repository=new TripRepository(application);
        allTrips=repository.getAllTrips();
    }
    public void insert(Trips trips){
        repository.insert(trips);
    }
    public void update(Trips trips){
        repository.update(trips);
    }
    public void delete(Trips trips){
        repository.delete(trips);
    }

    public LiveData<List<Trips>> getAllTrips() {

        return allTrips;
    }
    public LiveData<List<Trips>> getBookmarkedTrips(){
        LiveData<List<Trips>>bookmarked=allTrips;
        for(int i=0;i<allTrips.getValue().size();i++){
            if(!allTrips.getValue().get(i).getBookmark()){
                bookmarked.getValue().remove(bookmarked.getValue().get(i));
            }
        }
        return bookmarked;
    }


    public Trips getTrip(int position){
        return  allTrips.getValue().get(position);
    }
}
