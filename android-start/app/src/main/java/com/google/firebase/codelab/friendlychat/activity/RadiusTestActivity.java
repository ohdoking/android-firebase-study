package com.google.firebase.codelab.friendlychat.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.codelab.friendlychat.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

public class RadiusTestActivity extends AppCompatActivity implements GeoQueryEventListener {

    private GeoFire geoFire;
    private GeoQuery geoQuery;

    private TextView count;

    private static final String GEO_FIRE_DB = "https://friendlychat-1a39a.firebaseio.com";
//    private static final String GEO_FIRE_DB = "https://publicdata-transit.firebaseio.com";
    private static final String GEO_FIRE_REF = GEO_FIRE_DB + "/_geofire";

    private static final GeoLocation INITIAL_CENTER = new GeoLocation(37.7787, -122.4015);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radius_test);

        count = (TextView) findViewById(R.id.countTv);

//        FirebaseOptions options = new FirebaseOptions.Builder().setApplicationId("geofire").setDatabaseUrl(GEO_FIRE_DB).build();
//        FirebaseApp app = FirebaseApp.initializeApp(this, options);


        // setup GeoFire
        this.geoFire = new GeoFire(FirebaseDatabase.getInstance().getReferenceFromUrl(GEO_FIRE_REF));

//        geoFire.setLocation("ohdoking-me", INITIAL_CENTER);

        // radius in km
        this.geoQuery = this.geoFire.queryAtLocation(INITIAL_CENTER, 100);

        count.setText(geoQuery.getCenter().latitude+"");

    }

    @Override
    protected void onStart() {
        super.onStart();
        // add an event listener to start updating locations again
        this.geoQuery.addGeoQueryEventListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // remove all event listeners to stop updating in the background
        this.geoQuery.removeAllListeners();
    }

    @Override
    public void onKeyEntered(String key, GeoLocation location) {
        Log.i("ohdoking-enter",String.format("Key %s entered the search area at [%f,%f]", key, location.latitude, location.longitude));
    }

    @Override
    public void onKeyExited(String key) {
        Log.i("ohdoking-exit",String.format("Key %s is no longer in the search area", key));
    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        Log.i("ohdoking-move",String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
    }

    @Override
    public void onGeoQueryReady() {
        Log.i("ohdoking-ready","All initial data has been loaded and events have been fired!");
    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        Log.i("ohdoking-error","There was an error with this query: " + error);
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("There was an unexpected error querying GeoFire: " + error.getMessage())
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
