package com.example.holiday.Room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Update;

import com.example.holiday.Room.dao.TripsDao;
import com.example.holiday.Room.entity.Trips;

import java.util.List;

public class TripRepository {
    private TripsDao tripsDao;
    private LiveData<List<Trips>>allTrips;
    public TripRepository(Application application){
        AppDatabase appDatabase=AppDatabase.getAppDatabase(application);
        tripsDao=appDatabase.tripsDao();
        allTrips=tripsDao.getAllTrips();
    }
    public void update(Trips trips){
      new UpdateNoteAsyncTask(tripsDao).execute(trips);
    }
    public void insert(Trips trips){
      new InsertNoteAsyncTask(tripsDao).execute(trips);
    }
    public void delete(Trips trips){
        new DeleteNoteAsyncTask(tripsDao).execute(trips);
    }
 //   public void deleteAllTrips(Trips trips){

   // }

    public LiveData<List<Trips>> getAllTrips() {
        return allTrips;
    }
    private static class InsertNoteAsyncTask extends AsyncTask<Trips,Void,Void>{
          private TripsDao tripsDao;
          private InsertNoteAsyncTask(TripsDao tripsDao){
              this.tripsDao=tripsDao;
          }
        @Override
        protected Void doInBackground(Trips... trips) {
              tripsDao.insert(trips[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<Trips,Void,Void>{
        private TripsDao tripsDao;
        private UpdateNoteAsyncTask(TripsDao tripsDao){
            this.tripsDao=tripsDao;
        }
        @Override
        protected Void doInBackground(Trips... trips) {
            tripsDao.update(trips[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Trips,Void,Void>{
        private TripsDao tripsDao;
        private DeleteNoteAsyncTask(TripsDao tripsDao){
            this.tripsDao=tripsDao;
        }
        @Override
        protected Void doInBackground(Trips... trips) {
            tripsDao.delete(trips[0]);
            return null;
        }
    }

}
