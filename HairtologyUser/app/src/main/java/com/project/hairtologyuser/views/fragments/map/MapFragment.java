package com.project.hairtologyuser.views.fragments.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.models.ShopModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MapFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        ShopModel shop;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String jsonString = bundle.getString("data");

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                shop = new ShopModel();
                shop.setId(jsonObject.getInt("id"));
                shop.setName(jsonObject.getString("name"));
                shop.setDescription(jsonObject.getString("description"));
                shop.setAddress(jsonObject.getString("address"));
                shop.setPrice(jsonObject.getString("price"));
                shop.setHour(jsonObject.getString("hour"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        if (supportMapFragment != null) {
            Log.e(MapFragment.class.getSimpleName(), "No errors");
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(@NonNull LatLng latLng) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng);
                            markerOptions.title(latLng.latitude + " KG " + latLng.longitude);
                            googleMap.clear();
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                            googleMap.addMarker(markerOptions);
                        }
                    });
                }
            });
        } else {
            Log.e(MapFragment.class.getSimpleName(), "Have errors");
        }

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}