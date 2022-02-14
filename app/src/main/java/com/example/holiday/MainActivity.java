package com.example.holiday;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.holiday.Room.AppDatabase;

import com.example.holiday.Room.entity.Trips;
import com.example.holiday.ui.TripAdapter;
import com.example.holiday.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


      private AppDatabase appDatabase;
      public static TripViewModel tripViewModel;
    public static TripViewModel BookmarkViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appDatabase = appDatabase.getAppDatabase(MainActivity.this);
        tripViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance
                (this.getApplication()))
                .get(TripViewModel.class);


        BookmarkViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance
                (this.getApplication()))
                .get(TripViewModel.class);
        Intent intent = new Intent(this, AddTrip.class);
        FloatingActionButton fab = findViewById(R.id.fab);


        fab.setOnClickListener(view -> startActivityForResult(intent, 19));
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_about_us, R.id.nav_contact, R.id.nav_share, R.id.nav_favorite)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View header = navigationView.getHeaderView(0);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 19) {
            Trip t=new Trip();
            t=data.getParcelableExtra("trip");
            Trips trips=new Trips();
            trips.setTripName(t.getTripName());
            trips.setDestination(t.getDestination());
            trips.setPrice(t.getPrice());
            trips.setTripType(t.getTripType());
            trips.setStartDate(t.getStartDate());
            trips.setEndDate(t.getEndDate());
            trips.setRating(t.getRatingBar());
            trips.setBookmark(false);
            tripViewModel.insert(trips);
        }
        if (resultCode == RESULT_OK && requestCode == 16) {
            NavigationView navigationView = findViewById(R.id.nav_view);
            View header = navigationView.getHeaderView(0);
            TextView username = (TextView) header.findViewById(R.id.Name);
            username.setText(data.getStringExtra("name"));
            TextView email = (TextView) header.findViewById(R.id.email);
            email.setText(data.getStringExtra("email"));


            }
        }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_settings:
                    Intent intent=new Intent(MainActivity.this,AddUser.class);
                    startActivityForResult(intent,16);
                    break;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void Add(View view) {
        Intent intent = new Intent(this, AddTrip.class);
        startActivityForResult(intent, 19);
    }
}