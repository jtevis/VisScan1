package visconsulting.simplegps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class newGTINMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker mGTIN;
    String lot;
    double lat;
    double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gtinmap);

        lot =getIntent().getExtras().getString("lot number").toString();
        lat = (getIntent().getExtras().getDouble("latitude"));
        lon = (getIntent().getExtras().getDouble("longitude"));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(newGTINMap.this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Toast.makeText(this, "Map is ready",Toast.LENGTH_SHORT).show();


        //final LatLng gtin = new LatLng(38.798902, -88.121838);
        final LatLng gtin = new LatLng(lat,lon);

        // Add some markers to the map, and add a data object to each marker.
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gtin, 15), 1000, null);
        mGTIN = mMap.addMarker(new MarkerOptions()
                .position(gtin)
                .title(lot));
        mGTIN.setTag(0);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(gtin));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
       // mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
        // Set a listener for marker click.
        // mMap.setOnMarkerClickListener(mMap);    }
}
