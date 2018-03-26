package com.cs307.evant.evant;

import android.Manifest;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.net.Uri;
import android.os.Build;
//import android.support.design.widget.FloatingActionButton;
import android.provider.MediaStore;
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
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cs307.evant.evant.MainActivity.db;


public class MapView extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    final Context context = this;

    Map<Marker, String> markerLoc = new HashMap<Marker, String>();
    Map<Marker, String> markerTime = new HashMap<Marker, String>();
    Map<Marker, String> markerHost = new HashMap<Marker, String>();


    class infoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private final View contentsView;
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        infoWindowAdapter() {
            contentsView = inflater.inflate(R.layout.event_card, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {

            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            TextView tvTitle = ((TextView) contentsView.findViewById(R.id.eventTitle));
            tvTitle.setText(marker.getTitle());
            TextView tvDescription = ((TextView) contentsView.findViewById(R.id.description));
            tvDescription.setText(marker.getSnippet());
            TextView tvLoc = ((TextView) contentsView.findViewById(R.id.locationText));
            tvLoc.setText(markerLoc.get(marker));
            TextView tvTime = ((TextView) contentsView.findViewById(R.id.time));
            if (markerTime.get(marker) != null) {
                tvTime.setText(markerTime.get(marker));
            } else {
                tvTime.setText("NULL");
            }
            //TextView tvLocation = ((TextView)contentsView.findViewById(R.id.locationText));
            //tvLocation.setText(marker.get);
            //ImageView imgView = (ImageView)contentsView.findViewById(R.id.)
            return contentsView;
        }
    }


    private static final int MY_REQUEST_INT = 177;
    private GoogleMap mMap;
    private SensorManager mSensorManager;
    private CircleOptions circleOptions;
    private Circle circle;
    private double radiusVal = db.getRadius(db.getUid());
    private String radiusStr = "";
    private String curTime = DateFormat.getDateTimeInstance().format(new Date());
    private Calendar timeCalendar = Calendar.getInstance();
    private Calendar curCalendar = Calendar.getInstance();
    private Date date = curCalendar.getTime();


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

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapView.this, catList.class);
                startActivity(intent);
            }
        });

        Button profileButton = (Button) findViewById(R.id.profilebutton);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapView.this, Profile.class);
                intent.putExtra("uid", db.getUid());
                startActivity(intent);
            }
        });

        //attempt
        Button settingsButton = (Button) findViewById(R.id.settingsbutton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapView.this, Settings.class);
                intent.putExtra("uid", db.getUid());
                startActivity(intent);
            }
        });
        //attempt end

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
                Intent intent = new Intent(MapView.this, catList.class);
                startActivity(intent);
            }
        });


        final DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                timeCalendar.set(Calendar.YEAR, year);
                timeCalendar.set(Calendar.MONTH, month);
                timeCalendar.set(Calendar.DAY_OF_MONTH, day);

            }
        };

        final TimePickerDialog.OnTimeSetListener tpd = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                timeCalendar.set(Calendar.HOUR, hour);
                timeCalendar.set(Calendar.MINUTE, min);
            }
        };


        FloatingActionButton radius = (FloatingActionButton) findViewById(R.id.radius);
        radius.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "DISTANCE = " + radiusVal, Toast.LENGTH_SHORT).show();

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Set Radius");
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alertDialogBuilder.setView(input);

                alertDialogBuilder.setMessage("Current radius is = " + radiusVal)
                        .setCancelable(false)
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                radiusStr = input.getText().toString();
                                radiusVal = Double.parseDouble(radiusStr);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
        FloatingActionButton time = (FloatingActionButton) findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Set Time");
                final EditText input = new EditText(context);
                date = timeCalendar.getTime();
                alertDialogBuilder.setMessage("Current time setting is = " + date)
                        .setCancelable(false)
                        .setPositiveButton("Change Date", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        new TimePickerDialog(MapView.this, tpd, timeCalendar.get(Calendar.HOUR), timeCalendar.get(Calendar.MINUTE), false).show();
                                        new DatePickerDialog(MapView.this, dpd, timeCalendar.get(Calendar.YEAR), timeCalendar.get(Calendar.MONTH), timeCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                    }
                                }
                        )
                        /*
                        .setPositiveButton("Change Time", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        */
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        FloatingActionButton attended = (FloatingActionButton) findViewById(R.id.attended);
        attended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapView.this, atten_events.class);
                startActivity(intent);
            }
        });


        final ToggleButton upcomingButton = (ToggleButton) findViewById(R.id.upcoming);
        final ToggleButton arrowButton = (ToggleButton) findViewById(R.id.arrowUP);
        upcomingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (upcomingButton.isChecked()) {
                    arrowButton.setChecked(true);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.upcomingRecycler);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.addItemDecoration(new DividerItemDecoration(MapView.this, LinearLayoutManager.VERTICAL));
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MapView.this);
                    //recyclerView.addItemDecoration(new DividerItemDecoration(HomeScreen.this, LinearLayoutManager.VERTICAL));
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    ArrayList<String> titles = new ArrayList<>();
                    ArrayList<String> dttime = new ArrayList<>();
                    ArrayList<String> loc = new ArrayList<>();
                    ArrayList<String> descrip = new ArrayList<>();
                    ArrayList<String> hst = new ArrayList<>();
                    //cats = db.getCategories();

                    //System.out.println(cats.get(0)[0]);

                    //filterCats(cats);

                    titles = db.getTitles();

                    hst = db.getHost();

                    dttime = db.getTime();

                    loc = db.getLoc();

                    descrip = db.getDescription();

                    eventAdapter cadapter = new eventAdapter(titles, descrip, dttime, loc, hst, MapView.this);

                    recyclerView.bringToFront();
                    recyclerView.setAdapter(cadapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                else {
                    arrowButton.setChecked(false);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.upcomingRecycler);
                    recyclerView.setAdapter(null);
                    recyclerView.setVisibility(View.INVISIBLE);
                }
            }
        });


        arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrowButton.isChecked()) {
                    upcomingButton.setChecked(true);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.upcomingRecycler);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.addItemDecoration(new DividerItemDecoration(MapView.this, LinearLayoutManager.VERTICAL));
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MapView.this);
                    //recyclerView.addItemDecoration(new DividerItemDecoration(HomeScreen.this, LinearLayoutManager.VERTICAL));
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    ArrayList<String> titles = new ArrayList<>();
                    ArrayList<String> dttime = new ArrayList<>();
                    ArrayList<String> loc = new ArrayList<>();
                    ArrayList<String> descrip = new ArrayList<>();
                    ArrayList<String> hst = new ArrayList<>();
                    //cats = db.getCategories();

                    //System.out.println(cats.get(0)[0]);

                    //filterCats(cats);

                    titles = db.getTitles();

                    hst = db.getHost();

                    dttime = db.getTime();

                    loc = db.getLoc();

                    descrip = db.getDescription();

                    eventAdapter cadapter = new eventAdapter(titles, descrip, dttime, loc, hst, MapView.this);

                    recyclerView.bringToFront();
                    recyclerView.setAdapter(cadapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                else {
                    upcomingButton.setChecked(false);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.upcomingRecycler);
                    recyclerView.setAdapter(null);
                    recyclerView.setVisibility(View.INVISIBLE);
                }
            }
        });

        if(getIntent().getExtras() != null && getIntent().getExtras().getBoolean("firstTime")) {
            Intent intent = new Intent(MapView.this, MapView.class);
            intent.putExtra("firstTime", false);
            try {
                Thread.sleep(1000);
            }
            catch (Exception e) {

            }
            startActivity(intent);
        }

    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
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
        //       testingMarkers();
        placeMarkers();
        mMap.setInfoWindowAdapter(new infoWindowAdapter());

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapView.this);
                alertDialog.setTitle("Choose an option");
                alertDialog.setMessage("What I would you like to do?");
                alertDialog.setPositiveButton("Attend event", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       String uid = db.getUid();
                       Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_LONG).show();
//                        ArrayList<String> myEvents = db.getMyEvents("2t1O9DuHH4ePPINBMKLDWvmC4wJ3");
/*
                        ArrayList<String> events = db.getTitles();
                        for(int a =0; a < events.size(); a++){
                            if(marker.getTitle().equals(events.indexOf(a))){
                                myEvents.add(marker.getTitle());
                            }
                        }
                        db.updateMyEvents(db.getUid(),myEvents);
*/
                    }
                });
                alertDialog.setNegativeButton("Learn more", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MapView.this, eventPage.class);
                        intent.putExtra("Title", marker.getTitle());
                        intent.putExtra("Description", (marker.getSnippet()));
                        intent.putExtra("dttime", markerTime.get(marker));
                        intent.putExtra("location", markerLoc.get(marker));
                        intent.putExtra("Host", markerHost.get(marker));
                        startActivity(intent);
                    }
                });
                alertDialog.show();
            }
        });


    }

    LocationManager manager;

    private void startLocationService() {
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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
        double distance = calculateDistance(marker.getPosition());
        System.out.println(distance);
        if (distance < 0) {
            return false;
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
            marker.showInfoWindow();
            Toast.makeText(getApplicationContext(), "DISTANCE = " + distance, Toast.LENGTH_SHORT).show();


            return true;
        }

    }

    private class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
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

        private void showCurrentLocation(Double latitude, Double longitude) {
            if (circle != null) {
                circle.remove();
            }
            LatLng cur = new LatLng(latitude, longitude);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cur, 15));
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            circleOptions = new CircleOptions()
                    .center(cur)
                    .radius(radiusVal)
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
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cur, 15), 2000, null);
        }
    }

    private void placeMarkers() {
        ArrayList<Double> lats;
        ArrayList<Double> lngs;
        ArrayList<String> titles;
        ArrayList<String> discrips;
        ArrayList<String> locations;
        ArrayList<String> times;
        ArrayList<String> host;

        lats = db.getLat();
        lngs = db.getLng();
        titles = db.getTitles();
        discrips = db.getDescription();
        locations = db.getLoc();
        times = db.getTime();
        host = db.getHost();

        int totalEvents = lats.size();

        ArrayList<LatLng> markerLatlngs = new ArrayList<>();
        ArrayList<MarkerOptions> markerOptions = new ArrayList<>();
        ArrayList<Marker> markers = new ArrayList<>();

        for (int index = 0; index < totalEvents; index++) {
            final Object latObj = lats.get(index);
            final Object lngObj = lngs.get(index);
            final double douLat = ((Number) latObj).doubleValue();
            final double douLng = ((Number) lngObj).doubleValue();

            LatLng tempLatLng = new LatLng(douLat, douLng);
            markerLatlngs.add(tempLatLng);
            MarkerOptions tempMarkerOptions = new MarkerOptions();

            tempMarkerOptions.position(markerLatlngs.get(index)).title(titles.get(index)).snippet(discrips.get(index));
            markerOptions.add(tempMarkerOptions);

        }

        for (int index = 0; index < totalEvents; index++) {
            Marker tempMarker = mMap.addMarker(markerOptions.get(index));
            markerLoc.put(tempMarker, locations.get(index));
            markerTime.put(tempMarker, times.get(index));
            markerHost.put(tempMarker, host.get(index));
            markers.add(tempMarker);
        }

        mMap.setOnMarkerClickListener(this);

        //       Toast.makeText(getApplicationContext(), "Lat = " + lats.get(0) + " Lng = " + lngs.get(0) + " titles = " + titles.get(0) + " discrips = " + discrips.get(0), Toast.LENGTH_SHORT).show();
        /*
        for(int a=0; a < lats.size(); a++){
            LatLng tempLatLng = new LatLng(lats.get(a), lngs.get(a));
            markerLatlngs.add(tempLatLng);
            MarkerOptions tempMarkerOptions = new MarkerOptions();
            tempMarkerOptions.position(markerLatlngs.get(a)).title(titles.get(a)).snippet(discrips.get(a));
            markerOptions.add(tempMarkerOptions);
        }

        for(int a=0; a<lats.size(); a++){
            Marker tempMarker = mMap.addMarker(markerOptions.get(a));
            markers.add(tempMarker);
        }

        mMap.setOnMarkerClickListener(this);
        */
    }


    private void testingMarkers() {
        ArrayList<Double> lats = new ArrayList<Double>();
        ArrayList<Double> lngs = new ArrayList<Double>();
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<String> discrips = new ArrayList<String>();


        titles.add("PMU");
        lats.add(40.424800);
        lngs.add(-86.911000);
        discrips.add("PMU Bowling Tourney");

        titles.add("Corec");
        lats.add(40.428329);
        lngs.add(-86.922496);
        discrips.add("Corec Yoga Class");

        titles.add("Lawson");
        lats.add(40.427728);
        lngs.add(-86.916975);
        discrips.add("Lawson Hackaton");

        titles.add("House");
        lats.add(40.462390);
        lngs.add(-86.947603);
        discrips.add("Soun's home party");

        ArrayList<LatLng> markerLatlngs = new ArrayList<>();
        ArrayList<MarkerOptions> markerOptions = new ArrayList<>();
        ArrayList<Marker> markers = new ArrayList<>();

        for (int a = 0; a < lats.size(); a++) {
            LatLng tempLatLng = new LatLng(lats.get(a), lngs.get(a));
            markerLatlngs.add(tempLatLng);
            MarkerOptions tempMarkerOptions = new MarkerOptions();
            tempMarkerOptions.position(markerLatlngs.get(a)).title(titles.get(a)).snippet(discrips.get(a));
            markerOptions.add(tempMarkerOptions);
        }

        for (int a = 0; a < lats.size(); a++) {
            Marker tempMarker = mMap.addMarker(markerOptions.get(a));
            markers.add(tempMarker);
        }

        mMap.setOnMarkerClickListener(this);
    }

    private double calculateDistance(LatLng marLoc) {
        Location curLoc = new Location(LocationManager.GPS_PROVIDER);

        try {
            curLoc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException e) {


        }


        Location markerLoc = new Location("");
        markerLoc.setLatitude(marLoc.latitude);
        markerLoc.setLongitude(marLoc.longitude);

        double distance = -1;

        try {
            distance = curLoc.distanceTo(markerLoc);
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_SHORT).show();
        }

        distance = distance / 1000;


        return distance;
    }

}



