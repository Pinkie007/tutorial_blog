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
 */
public class MapActivity extends GeolocationActivity implements GoogleMap.OnInfoWindowClickListener{

    private final String TAG = "MapFragment";
    private MapView _mapView;
    private GoogleMap _gmap;
    private Bitmap _littlePin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        _mapView = (MapView) findViewById(R.id.location_map);
        _mapView.onCreate(savedInstanceState);
        _gmap = _mapView.getMap();
        if (_gmap != null)
        {
            setUpMap();
        }
    }

    private boolean setUpMap()
    {
        // Gets to GoogleMap from the MapView and does initialization stuff
        _gmap.getUiSettings().setMyLocationButtonEnabled(false);
        _gmap.setMyLocationEnabled(true);
        if (_gmap == null) {
            Toast.makeText(this, "Google Maps not available",  Toast.LENGTH_LONG).show();
        }
        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this);


        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.pin);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        _littlePin = Bitmap.createScaledBitmap(bitmap, 96, 96, false);

        _gmap.setOnInfoWindowClickListener(this);


        this.addMarker("Cafe", "Best cafe in London", new LatLng(_latitude, _longitude));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(_latitude, _longitude), 13);
        _gmap.animateCamera(cameraUpdate);
        return true;
    }

    private void addMarker(String title, String description, LatLng position)
    {
        _gmap.addMarker(new MarkerOptions()
                .position(position)
                .title(title)
                .snippet(description)
                .icon(BitmapDescriptorFactory.fromBitmap(_littlePin)));
    }

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
