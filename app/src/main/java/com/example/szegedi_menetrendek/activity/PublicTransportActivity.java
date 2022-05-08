package com.example.szegedi_menetrendek.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.szegedi_menetrendek.model.PublicTransport;
import com.example.szegedi_menetrendek.adapter.PublicTransportAdapter;
import com.example.szegedi_menetrendek.R;
import com.example.szegedi_menetrendek.notification.NotificationHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class PublicTransportActivity extends AppCompatActivity {
    private static final String LOG_TAG = PublicTransportActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private FirebaseUser user;

    private RecyclerView mRecyclerView;
    private ArrayList<PublicTransport> mPublicTransportData;
    private PublicTransportAdapter mAdapter;
    private NotificationHelper notificationHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_transport);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            Log.d(LOG_TAG, "Authenticated user!");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
            finish();
        }

        // recycle view
        mRecyclerView = findViewById(R.id.recyclerView);
        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // Initialize the ArrayList that will contain the data.
        mPublicTransportData = new ArrayList<>();
        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new PublicTransportAdapter(this, mPublicTransportData);
        mRecyclerView.setAdapter(mAdapter);
        // Get the data.
        initializeData();

        notificationHelper = new NotificationHelper(this);
        notificationHelper.send("Köszöntelek az alkalmazásomban! :)");
    }

    private void initializeData() {
        // Get the resources from the XML file.
        String[] publicTransportNumbers = getResources()
                .getStringArray(R.array.public_transport_item_numbers);
        TypedArray publicTransportImages =
                getResources().obtainTypedArray(R.array.public_transport_item_images);

        // Clear the existing data (to avoid duplication).
        mPublicTransportData.clear();

        // Create the ArrayList of Sports objects with the titles and
        // information about each sport.
        for (int i = 0; i < publicTransportNumbers.length; i++) {
            mPublicTransportData.add(new PublicTransport(publicTransportNumbers[i], publicTransportImages.getResourceId(i, 0)));
        }

        // Recycle the typed array.
        publicTransportImages.recycle();

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.public_transport_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG, s);
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out_button:
                Log.d(LOG_TAG, "Logout clicked!");
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openSchedule(View view) {
        Intent intent = new Intent(this, SheduleActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}