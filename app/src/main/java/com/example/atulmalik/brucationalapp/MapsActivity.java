package com.example.atulmalik.brucationalapp;



import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ZoomControls;
import android.widget.Button;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.MapStyleOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    ZoomControls zoom;
    Button markBt;
    Double myLatitude = null;
    Double myLongitude = null;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    protected static final String TAG = "MapsActivity";
    private LatLngBounds BRUNEL = new LatLngBounds(
            new LatLng(51.529863, -0.465732), new LatLng(51.534997, -0.482392));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        locationRequest = new LocationRequest();
        locationRequest.setInterval(15 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        zoom = (ZoomControls) findViewById(R.id.zcZoom);
        zoom.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
        zoom.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());

            }
        });
        markBt = (Button) findViewById(R.id.btMark);
        markBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng myLocation = new LatLng(myLatitude, myLongitude);
                mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));


            }
        });

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

        // Customise the styling of the base map using a JSON object defined
        // in a string resource file. First create a MapStyleOptions object
        // from the JSON styles string, then pass this to the setMapStyle
        // method of the GoogleMap object.
        boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));

        if (!success) {
            Log.e(TAG, "Style parsing failed.");
        }

        //Sets boundaries to Brunel.
        //mMap.setLatLngBoundsForCameraTarget(BRUNEL);
        //Sets camera to view the bounds.
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(BRUNEL, 0));

        //Zone E
        PolygonOptions rectOptions = new PolygonOptions()
                .add(new LatLng(51.534730, -0.471630),
                        new LatLng(51.533237, -0.471702),
                        new LatLng(51.533090, -0.469846),
                        new LatLng(51.531775, -0.469825),
                        new LatLng(51.532996, -0.467465),
                        new LatLng(51.532996, -0.467465),
                        new LatLng(51.532996, -0.467465),
                        new LatLng(51.534758, -0.468677),
                        new LatLng(51.534758, -0.468677))
                .strokeColor(Color.BLUE);


// Get back the mutable Polygon
        Polygon polygon = mMap.addPolygon(rectOptions);
        //Markers for zone E
        Marker Eastern = mMap.addMarker(new MarkerOptions().position(new LatLng(51.533591, -0.468423)).title("Eastern Gateway").snippet("Houses The Business School"));
        Marker SportsCentre = mMap.addMarker(new MarkerOptions().position(new LatLng(51.533303, -0.469959)).title("Sports Centre").snippet("State of Th Art Facilities Including fully Equipped Gym"));
        Marker Lancaster = mMap.addMarker(new MarkerOptions().position(new LatLng(51.534345, -0.471376)).title("Lancaster Complex").snippet("Student Halls"));
        Marker MarySeacole = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532825, -0.468563)).title("Mary Seacole").snippet("The Building Is Home To Students and Staff From The College Of Health and Life Sciences."));
        Marker BrunelSports = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532383, -0.469628)).title("Brunel Indoor Athletic Centre").snippet("Brunel University Sport's state-of-the-art Facilities Are Open To Students, Staff and Members Of The Community."));
        Marker St_Johns = mMap.addMarker(new MarkerOptions().position(new LatLng(51.534496, -0.469319)).title("St Johns").snippet("Computer Science Centre"));



        //Zone G
        PolygonOptions rectOptions1 = new PolygonOptions()
                .add(new LatLng(51.532045, -0.469105),
                        new LatLng(51.531211, -0.469159),
                        new LatLng(51.531531, -0.466187),
                        new LatLng(51.532432, -0.467324),
                        new LatLng(51.532439, -0.468311),
                        new LatLng(51.532019, -0.469094),
                        new LatLng(51.532045, -0.469105))
                .strokeColor(Color.rgb(238,130,238));


        //Markers for zone G
        Polygon polygon1 = mMap.addPolygon(rectOptions1);
        Marker Russel = mMap.addMarker(new MarkerOptions().position(new LatLng(51.531908, -0.468371)).title("Russell Building").snippet(""));
        Marker Elliot = mMap.addMarker(new MarkerOptions().position(new LatLng(51.531868, -0.467499)).title("Elliot Jaques").snippet(""));
        Marker Gardiner = mMap.addMarker(new MarkerOptions().position(new LatLng(51.531470, -0.468052)).title("Gardiner").snippet(""));
        Marker Metal = mMap.addMarker(new MarkerOptions().position(new LatLng(51.531448, -0.466968)).title("Advanced Metal Casting Centre").snippet(""));


        //Zone F
        PolygonOptions rectOptions2 = new PolygonOptions()
                .add(new LatLng(51.533142, -0.472416),
                        new LatLng(51.532211, -0.472447),
                        new LatLng(51.532219, -0.472232),
                        new LatLng(51.530877, -0.472253),
                        new LatLng(51.531144, -0.469303),
                        new LatLng(51.531751, -0.469303),
                        new LatLng(51.531858, -0.469925),
                        new LatLng(51.533059, -0.470022),
                        new LatLng(51.533146, -0.472393))
                .strokeColor(Color.RED);



        //Markers for zone F
        Polygon polygon2 = mMap.addPolygon(rectOptions2);
        Marker Gordon = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532997, -0.472008)).title("Gordon Hall").snippet(""));
        Marker Saltash = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532360, -0.472003)).title("Saltash Hall").snippet(""));
        Marker Chespstow = mMap.addMarker(new MarkerOptions().position(new LatLng(51.531691, -0.471258)).title("Chepstow Hall").snippet(""));
        Marker Clifton = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532378, -0.470845)).title("Clifton Hall").snippet(""));
        Marker Bishop = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532865, -0.470137)).title("Bishop Hall").snippet(""));
        Marker Killmorey = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532470, -0.470264)).title("Killmorey Hall").snippet(""));
        Marker Lacy = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532076, -0.470084)).title("Lacy Hall").snippet(""));
        Marker st = mMap.addMarker(new MarkerOptions().position(new LatLng(51.531655, -0.469955)).title("St Margarets Hall").snippet(""));
        Marker Faraday1 = mMap.addMarker(new MarkerOptions().position(new LatLng(51.531321, -0.469477)).title("Faraday Complex").snippet(""));
        Marker Faraday2 = mMap.addMarker(new MarkerOptions().position(new LatLng(51.531187, -0.470507)).title("Faraday Complex").snippet(""));
        Marker Faraday3 = mMap.addMarker(new MarkerOptions().position(new LatLng(51.531740, -0.470399)).title("Gardiner").snippet(""));




        //Zone B
        PolygonOptions rectOptions3 = new PolygonOptions()
                .add(new LatLng(51.534604, -0.475535),
                        new LatLng(51.533643, -0.475546),
                        new LatLng(51.533626, -0.472452),
                        new LatLng(51.533192, -0.472402),
                        new LatLng(51.533232, -0.471748),
                        new LatLng(51.533726, -0.471823),
                        new LatLng(51.533786, -0.472156),
                        new LatLng(51.534727, -0.472188),
                        new LatLng(51.534604, -0.475535))
                .strokeColor(Color.rgb(139,69,19));

        //Markers for zone B
        Polygon polygon3 = mMap.addPolygon(rectOptions3);
        Marker Wolfson = mMap.addMarker(new MarkerOptions().position(new LatLng(51.534211, -0.472475)).title("Wolfson Centre").snippet(""));
        Marker Bragg = mMap.addMarker(new MarkerOptions().position(new LatLng(51.534525, -0.473025)).title("Bragg").snippet(""));
        Marker Halsbury = mMap.addMarker(new MarkerOptions().position(new LatLng(51.533824, -0.472735)).title("Halsbury").snippet(""));
        Marker Design = mMap.addMarker(new MarkerOptions().position(new LatLng(51.533777, -0.474001)).title("Design and Print Service").snippet(""));
        Marker Heinz = mMap.addMarker(new MarkerOptions().position(new LatLng(51.534057, -0.474752)).title("Heinz Wolff").snippet(""));
        Marker John = mMap.addMarker(new MarkerOptions().position(new LatLng(51.533443, -0.472016)).title("John Crank").snippet(""));


        //Zone C
        PolygonOptions rectOptions4 = new PolygonOptions()
                .add(new LatLng(51.533639, -0.475580),
                        new LatLng(51.532490, -0.475586),
                        new LatLng(51.532473, -0.472444),
                        new LatLng(51.533556, -0.472578),
                        new LatLng(51.533609, -0.475583),
                        new LatLng(51.533027, -0.475594))
                .strokeColor(Color.BLACK);

        //Markers for zone C
        Polygon polygon4 = mMap.addPolygon(rectOptions4);
        Marker Hamilton = mMap.addMarker(new MarkerOptions().position(new LatLng(51.533310, -0.473909)).title("The Hamilton Centre").snippet(""));
        Marker LectureCentre = mMap.addMarker(new MarkerOptions().position(new LatLng(51.533141, -0.472838)).title("Lecture Centre").snippet(""));
        Marker Banneram = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532747, -0.473625)).title("Bannerman Centre").snippet(""));
        Marker Sterling = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532933, -0.474790)).title("Micheal Sterling").snippet(""));
        Marker Brown = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532900, -0.475338)).title("Wilfred Brown").snippet(""));
        Marker HQ = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532500, -0.474740)).title("Security HQ").snippet(""));

        //Zone D
        PolygonOptions rectOptions5 = new PolygonOptions()
                .add(new LatLng(51.532428, -0.475619),
                        new LatLng(51.530589, -0.475593),
                        new LatLng(51.530833, -0.472487),
                        new LatLng(51.532188, -0.472519),
                        new LatLng(51.532438, -0.472442))
                .strokeColor(Color.YELLOW);


        //Markers for zone D
        Polygon polygon5 = mMap.addPolygon(rectOptions5);
        Marker Ta = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532207, -0.474161)).title("Tower A").snippet(""));
        Marker Tb = mMap.addMarker(new MarkerOptions().position(new LatLng(51.531488, -0.474266)).title("Tower B").snippet(""));
        Marker Tc = mMap.addMarker(new MarkerOptions().position(new LatLng(51.531328, -0.473526)).title("Tower C").snippet(""));
        Marker td = mMap.addMarker(new MarkerOptions().position(new LatLng(51.531345, -0.472829)).title("Tower D").snippet(""));
        Marker Howell = mMap.addMarker(new MarkerOptions().position(new LatLng(51.531952, -0.473237)).title("Howell").snippet(""));
        Marker JL = mMap.addMarker(new MarkerOptions().position(new LatLng(51.530881, -0.474032)).title("Joseph Lowe").snippet(""));
        Marker AA = mMap.addMarker(new MarkerOptions().position(new LatLng(51.530958, -0.474966)).title("Antonin Artaud").snippet(""));

        //Zone D
        PolygonOptions rectOptions6 = new PolygonOptions()
                .add(new LatLng(51.534623, -0.476249),
                        new LatLng(51.534523, -0.479650),
                        new LatLng(51.535217, -0.479747),
                        new LatLng(51.535190, -0.481517),
                        new LatLng(51.533495, -0.481442),
                        new LatLng(51.533442, -0.482365),
                        new LatLng(51.532590, -0.482434),
                        new LatLng(51.532408, -0.480970),
                        new LatLng(51.530353, -0.480845),
                        new LatLng(51.530406, -0.479287),
                        new LatLng(51.532515, -0.479287),
                        new LatLng(51.532562, -0.476251),
                        new LatLng(51.534631, -0.476240))
                .strokeColor(Color.GREEN);

        //Markers for zone A
        Polygon polygon6 = mMap.addPolygon(rectOptions6);
        Marker MJ = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532955, -0.476642)).title("Marie Jahoda").snippet(""));
        Marker MT = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532985, -0.477315)).title("Meeting House").snippet(""));
        Marker Gaskell = mMap.addMarker(new MarkerOptions().position(new LatLng(51.533002, -0.478173)).title("Gaskell").snippet(""));
        Marker Chadwick = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532702, -0.478838)).title("Chadwick").snippet(""));
        Marker St = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532604, -0.479378)).title("Student Store").snippet(""));
        Marker MH = mMap.addMarker(new MarkerOptions().position(new LatLng(51.533355, -0.476754)).title("Mill Hall").snippet(""));
        Marker FH = mMap.addMarker(new MarkerOptions().position(new LatLng(51.533448, -0.477994)).title("Fleming Hall").snippet(""));
        Marker GH = mMap.addMarker(new MarkerOptions().position(new LatLng(51.533405, -0.479126)).title("Galbraith Hall").snippet(""));
        Marker IC = mMap.addMarker(new MarkerOptions().position(new LatLng(51.532388, -0.480361)).title("Isambard Complex:A-Q").snippet(""));



        // Add a marker in Brunel and move the camera
        LatLng brunel = new LatLng(51.533092, -0.474968);
        // mMap.addMarker(new MarkerOptions().position(brunel).title("Marker in Brunel"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(brunel, 18));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);

            }

        }

    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "This app requires locations permissions to be granted", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

       // public void onClick(View v){

   //Intent j = new Intent(this, SettingsActivity.class);
   //startActivity(j);

   //}
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "connection suspended");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection Failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());

    }

    @Override
    public void onLocationChanged(Location location) {
        myLatitude = location.getLatitude();
        myLongitude = location.getLongitude();

    }

    protected void onStart() {
        super.onStart();
    }
    protected void onPause(){
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }
    protected void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            requestLocationUpdates();
        }
    }
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }



}
