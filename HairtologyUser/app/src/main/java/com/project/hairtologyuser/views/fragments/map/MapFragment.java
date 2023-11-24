package com.project.hairtologyuser.views.fragments.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.models.ShopDetail;
import com.project.hairtologyuser.models.ShopModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MapFragment extends Fragment {

    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap mMap;
    private Location mCurrentLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ActivityResultLauncher<String[]> mLocationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarseLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION,false);
                        if (fineLocationGranted != null && fineLocationGranted) {
                            getLastLocation();
                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
                            getLastLocation();
                        } else {
                            Toast.makeText(getContext(), "Location permission is denied", Toast.LENGTH_LONG).show();
                        }
                    }
            );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mLocationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        ShopDetail shop;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String jsonString = bundle.getString("data");

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                shop = new ShopDetail();
                shop.setUuid(jsonObject.getString("uuid"));
                shop.setName(jsonObject.getString("name"));
                shop.setDescription(jsonObject.getString("description"));
                shop.setAddress(jsonObject.getString("address"));
                shop.setPrice(jsonObject.getString("price"));
                shop.setHour(jsonObject.getString("hour"));
                shop.setImageId1(jsonObject.getString("imageId1"));
                shop.setImageId2(jsonObject.getString("imageId2"));
                shop.setImageId3(jsonObject.getString("imageId3"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }

        Task<Location> task = mFusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mCurrentLocation = location;

                    SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    if (supportMapFragment != null) {
                        Log.e(MapFragment.class.getSimpleName(), "No errors");
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull GoogleMap googleMap) {
                                mMap = googleMap;
                                LatLng latlng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(latlng).title("Me"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17.0f));
//                                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                                    @Override
//                                    public void onMapClick(@NonNull LatLng latLng) {
//                                        MarkerOptions markerOptions = new MarkerOptions();
//                                        markerOptions.position(latLng);
//                                        markerOptions.title(latLng.latitude + " KG " + latLng.longitude);
//                                        googleMap.clear();
//                                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
//                                        googleMap.addMarker(markerOptions);
//                                    }
//                                });
                            }
                        });
                    } else {
                        Log.e(MapFragment.class.getSimpleName(), "Have errors");
                    }
                }
            }
        });
    }

}