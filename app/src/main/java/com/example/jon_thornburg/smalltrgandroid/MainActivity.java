package com.example.jon_thornburg.smalltrgandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.jon_thornburg.smalltrgandroid.Fragment.EmployeeDetailFragment;
import com.example.jon_thornburg.smalltrgandroid.Fragment.NavigationListener;
import com.example.jon_thornburg.smalltrgandroid.Fragment.PeopleFragment;
import com.example.jon_thornburg.smalltrgandroid.Fragment.PlacesFragment;
import com.example.jon_thornburg.smalltrgandroid.Fragment.RoomDetailFragment;
import com.example.jon_thornburg.smalltrgandroid.Models.Employee;
import com.example.jon_thornburg.smalltrgandroid.Models.RichardsRoom;
import com.example.jon_thornburg.smalltrgandroid.Utils.Iso8601DateMaker;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements NavigationListener {

    private BottomNavigationView mNavigationView;
    public final static String TAG = MainActivity.class.getSimpleName();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            String isoDate = Iso8601DateMaker.getISODate();
            Log.d(TAG, "Here is the isoDate: " + isoDate);
            String fromDate = Iso8601DateMaker.iso8601FromFilter();
            Log.d(TAG, "Here is fromDate: " + fromDate);
            String toDate = Iso8601DateMaker.iso8601ToFilter();
            Log.d(TAG, "Here is the toDate: " + toDate);
            Iso8601DateMaker.robinFromFilter();
            Iso8601DateMaker.robinToFilter();
            mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
            mNavigationView.inflateMenu(R.menu.bottom_nav_items);
            mNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {

                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.menu_employees:
                                    Log.d(TAG, "Clicked on employees");
                                    Fragment fragment = PeopleFragment.createNew();
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.main_content, fragment, PeopleFragment.TAG);
                                    ft.addToBackStack("loaded emp fragment");
                                    ft.commit();
                                    break;
                                case R.id.menu_places:
                                    Fragment fragment1 = PlacesFragment.createNew();
                                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                                    ft1.replace(R.id.main_content, fragment1, PlacesFragment.TAG);
                                    ft1.addToBackStack(PlacesFragment.TAG);
                                    ft1.commit();

                                    break;
                            }
                            return true;
                        }
                    }
            );

            if (savedInstanceState == null) {
                Fragment fragment = PeopleFragment.createNew();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.main_content, fragment, PeopleFragment.TAG);
                ft.commit();
            }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
        }
        return true;
    }

    @Override
    public void itemBubbler(Serializable item) {
        if (item instanceof RichardsRoom) {
            Log.d(TAG, "From Activity: " + ((RichardsRoom) item).getName());
            Fragment frag = RoomDetailFragment.newInstance((RichardsRoom) item);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_content, frag, RoomDetailFragment.TAG);
            ft.addToBackStack(RoomDetailFragment.TAG);
            ft.commit();
        } else if (item instanceof Employee) {
            Log.d(TAG, "From Activity: " + ((Employee) item).getFullName());
            Fragment frag = EmployeeDetailFragment.newInstance((Employee) item);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_content, frag, RoomDetailFragment.TAG);
            ft.addToBackStack(RoomDetailFragment.TAG);
            ft.commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
