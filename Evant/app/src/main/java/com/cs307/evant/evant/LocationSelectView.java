package com.cs307.evant.evant;

import android.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.cs307.evant.evant.MainActivity.db;

/**
 * Created by Soun on 4/9/2018.
 */

public class LocationSelectView extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private SensorManager mSensorManager;
    private GoogleMap mMap;
    private static final int MY_REQUEST_INT = 177;
    private Double lat;
    private Double lng;
    boolean locCheck = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select_view);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        View locationButton = ((View) findViewById(1).getParent()).findViewById(2);
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.setMargins(0, 200, 30, 0);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
        marker.showInfoWindow();

        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enable current location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_INT);
            }

            return;
        } else {
            mMap.setMyLocationEnabled(true);
        }

   //     startLocationService();
        //testingMarkers();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(point));
                lat = point.latitude;
                lng = point.longitude;
                locCheck = true;
            }
        });

        final Button button = (Button) findViewById(R.id.doneButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("LAT", lat);
                intent.putExtra("LNG", lng);
                intent.putExtra("LOCCHECK", locCheck);
                if((lat==null) || lng == null){
                    Toast.makeText(getApplicationContext(), "Lat = " + db.getLocation(db.getUid()).latitude + " Lng = " + db.getLocation(db.getUid()).longitude, Toast.LENGTH_SHORT).show();
                }
                else{
                    LatLng newLoc = new LatLng(lat,lng);
                    db.updateLocation(db.getUid(),newLoc);
                    Toast.makeText(getApplicationContext(), "Lat = " + db.getLocation(db.getUid()).latitude + " Lng = " + db.getLocation(db.getUid()).longitude, Toast.LENGTH_SHORT).show();
                }
                setResult(Activity.RESULT_OK, intent);
                LocationSelectView.this.finish();
            }
        });

    }
}
