package com.tutomap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by guillaumeagis on 24/03/15.
 * Activity with display a google map with a market on it.
 * Geolocation activity is an activity which get the position in real of the user, see my prev tutorial to know more
 *
 *
 */
public class MapActivity extends GeolocationActivity implements GoogleMap.OnInfoWindowClickListener{

    private final String TAG = "MapFragment";


    private MapView _mapView;
    private GoogleMap _gmap;

    /**
     * the logo of the marker on the map
     */
    private Bitmap _littlePin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        _mapView = (MapView) findViewById(R.id.location_map);
        _mapView.onCreate(savedInstanceState);
        _gmap = _mapView.getMap(); // initalise the map
        if (_gmap != null)
        {
            setUpMap();
        }
    }

    /**
     * set up the map; Add the market on it with a blue dot for the location of the user in real time
     * @return false if something goes wrong, true otherwise
     * _latitude = latitude of the user, get from the GeolocationActivity
     * _longitude = longitude of the user, get from the GeolocationActivity
     */
    private boolean setUpMap()
    {
        // Gets to GoogleMap from the MapView and does initialization stuff
        _gmap.getUiSettings().setMyLocationButtonEnabled(false);
        _gmap.setMyLocationEnabled(true);
        if (_gmap == null) {
            Toast.makeText(this, "Google Maps not available",  Toast.LENGTH_LONG).show();
            return false;
        }
        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this);


        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.pin); // file stored in /res/drawable/
        Bitmap bitmap = bitmapDrawable.getBitmap();
        _littlePin = Bitmap.createScaledBitmap(bitmap, 96, 96, false); // 96/96px

        _gmap.setOnInfoWindowClickListener(this);// add listener on this class


        // here the _lat and _long should be the user of this cafe, at the moment I am using the one of the user
        this.addMarker("Cafe", "Best cafe in London", new LatLng(_latitude, _longitude));


        // camera, 13 if the zoon, you can change it manually, or dynamically in order to see all your markers
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(_latitude, _longitude), 13);
        _gmap.animateCamera(cameraUpdate); // zoom effect
        return true;
    }

    /**
     * Add markers on the map view
     * @param title title of place / marker
     * @param description short description
     * @param position geposition, LatLng object, filled with latitude and longitude.
     */
    private void addMarker(String title, String description, LatLng position)
    {
        _gmap.addMarker(new MarkerOptions()
                .position(position)
                .title(title)
                .snippet(description)
                .icon(BitmapDescriptorFactory.fromBitmap(_littlePin)));
    }

    /**
     * The user click on the marker on the map, this callback is called
     * @param marker clicked marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "onMarkerClick " + marker.getTitle());
    }


    @Override
    public void onResume() {
        _mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        _mapView.onLowMemory();
    }
}
