package org.clynch203.gcu.coursework.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.clynch203.gcu.coursework.R;
import org.clynch203.gcu.coursework.activities.ItemActivity;
import org.clynch203.gcu.coursework.controllers.ChannelController;
import org.clynch203.gcu.coursework.models.Item;

import java.util.ArrayList;

public class MapFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    ChannelController channelController;
    private Item targetItem;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootVew = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = rootVew.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immeadiately

        try {
            MapsInitializer.initialize(requireActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                mMap.setOnInfoWindowClickListener(MapFragment.this);

                // For showing a move to my location button
//                googleMap.setMyLocationEnabled(true);

                displayMarkers();

                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });

        return rootVew;
    }

    private void displayMarkers() {
        ArrayList<Marker> markers = new ArrayList<>(channelController.items().size());
        LatLng latLng;
        for (Item item : channelController.items()) {
            latLng = new LatLng(item.getLat(), item.getLon());
            Marker marker = googleMap.addMarker(
                    new MarkerOptions()
                            .position(latLng)
                            .title(item.getLocation())
            );

            if (targetItem != null) {
                if (item.getId() == targetItem.getId()) {
                    marker.showInfoWindow();
                }
            }

            markers.add(marker);
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();

        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.animateCamera(cameraUpdate);
    }

    public void setTargetItem(Item targetItem) {
        this.targetItem = targetItem;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void setChannelController(ChannelController channelController) {
        this.channelController = channelController;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        int backgroundColor = ResourcesCompat.getColor(requireContext().getResources(), R.color.item_2_background, null);
        int id = Integer.valueOf(marker.getId().substring(1));
        Item item = channelController.items().get(id);
        Intent intent = new Intent(requireContext(), ItemActivity.class);
        intent.putExtra("backgroundColor", backgroundColor);
        intent.putExtra("item", item);
        requireContext().startActivity(intent);
    }
}
