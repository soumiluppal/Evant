package com.cs307.evant.evant;

import android.Manifest;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.os.Build;
//import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;


public class MapView extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    class infoWindowAdapter implements GoogleMap.InfoWindowAdapter{
        private final View contentsView;
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        infoWindowAdapter() {
            contentsView = inflater.inflate(R.layout.map_info_window,null);
        }

        @Override
        public View getInfoWindow(Marker marker) {

            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            TextView tvTitle = ((TextView)contentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView)contentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());

            return contentsView;
        }
    }


    private static final int MY_REQUEST_INT = 177;
    private GoogleMap mMap;
    private SensorManager mSensorManager;
    private CircleOptions circleOptions;
    private Circle circle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        View locationButton = ((View) findViewById(1).getParent()).findViewById(2);

        // and next place it, for exemple, on bottom right (as Google Maps app)
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.setMargins(0, 200, 30, 0);

        Button button = findViewById(R.id.homebutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MapView.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button listButton = (Button) findViewById(R.id.listbutton);

        listButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MapView.this, catList.class);
                startActivity(intent);
            }
        });

        Button profileButton = (Button) findViewById(R.id.profilebutton);

        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MapView.this, Profile.class);
                startActivity(intent);
            }
        });

        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        final FloatingActionsMenu fam = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        //fam.addButton(actionC);
        FloatingActionButton create = (FloatingActionButton) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MapView.this, "Clicked pink Floating Action Button", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MapView.this, NewEventActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton attend = (FloatingActionButton) findViewById(R.id.attend);
        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MapView.this, "Clicked pink Floating Action Button", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MapView.this, ListView.class);
                startActivity(intent);
            }
        });

    }


    protected void onPause() {
        super.onPause();
    }

    protected void onResume(){
        super.onResume();

        startLocationService();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enable current location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_INT);
            }

            return;
        } else {
            mMap.setMyLocationEnabled(true);
        }

        startLocationService();
        testingMarkers();
        mMap.setInfoWindowAdapter(new infoWindowAdapter());

    }

    private void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        long minTime = 10000;
        float minDistance = 0;
        GPSListener gpsListener = new GPSListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                gpsListener
        );
        Toast.makeText(getApplicationContext(), "location searching", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
        marker.showInfoWindow();


        return true;
    }

    private class GPSListener implements LocationListener{
        public void onLocationChanged(Location location){
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            showCurrentLocation(latitude, longitude);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

        private void showCurrentLocation(Double latitude, Double longitude){
            if(circle != null){
                circle.remove();
            }
            LatLng cur = new LatLng(latitude, longitude);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cur, 15));
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            circleOptions = new CircleOptions()
                    .center(cur)
                    .radius(500)
                    .fillColor(0x40ff0000)
                    .strokeColor(Color.GREEN)
                    .strokeWidth(5);
            circle = mMap.addCircle(circleOptions);

            /*
            CircleOptions circle = new CircleOptions()
                    .center(cur)
                    .radius(500)
                    .fillColor(0x40ff0000)
                    .strokeColor(Color.BLUE)
                    .strokeWidth(5);
            mMap.addCircle(circle);
            prevCircle = circle;
            */
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cur, 15),2000, null);
        }
    }

    private void testingMarkers(){
        //mMap.setOnInfoWindowClickListener((GoogleMap.OnInfoWindowClickListener) this);

        LatLng tempLatLngPMU = new LatLng(40.424800,-86.911000);
        MarkerOptions tempMarkerOptionsPMU = new MarkerOptions();
        tempMarkerOptionsPMU.position(tempLatLngPMU).title("temp Marker PMU").snippet("hihihi");
        Marker pmu = mMap.addMarker(tempMarkerOptionsPMU);

        // pmu.showInfoWindow();

        LatLng tempLatLngCorec = new LatLng(40.428329,-86.922496);
        MarkerOptions tempMarkerOptionsCorec = new MarkerOptions();
        tempMarkerOptionsCorec.position(tempLatLngCorec).title("temp Marker Corec").snippet("More Info");
        Marker corec = mMap.addMarker(tempMarkerOptionsCorec);
//        corec.showInfoWindow();

        LatLng tempLatLngLawson = new LatLng(40.427728,-86.916975);
        MarkerOptions tempMarkerOptionsLawson = new MarkerOptions();
        tempMarkerOptionsLawson.position(tempLatLngLawson).title("temp Marker Lawson").snippet("testing");
        Marker lawson = mMap.addMarker(tempMarkerOptionsLawson);
//        lawson.showInfoWindow();

        mMap.setOnMarkerClickListener(this);
    }


}



