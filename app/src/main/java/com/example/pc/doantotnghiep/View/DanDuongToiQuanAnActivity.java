package com.example.pc.doantotnghiep.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.pc.doantotnghiep.Controller.DanDuongToiQuanAnController;
import com.example.pc.doantotnghiep.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DanDuongToiQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    MapFragment mapFragment;
    double latitude, longitude;
    SharedPreferences sharedPreferences;
    Location viTriHienTai;
    DanDuongToiQuanAnController danDuongToiQuanAnController;
    String duongDan = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_danduong);
        danDuongToiQuanAnController = new DanDuongToiQuanAnController();
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);

        sharedPreferences = getSharedPreferences("ToaDoHienTai", Context.MODE_PRIVATE);
        viTriHienTai = new Location("");
        viTriHienTai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        viTriHienTai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));
        duongDan = "https://maps.googleapis.com/maps/api/directions/json?origin=" + viTriHienTai.getLatitude() + "," + viTriHienTai.getLongitude() + "&destination=" + latitude + "," + longitude + "&language=vi&key=AIzaSyC_xZkTctE_pF4JSznOk4AKDECXdwGiCxQ";
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        googleMap.clear();
        LatLng latLng = new LatLng(viTriHienTai.getLatitude(), viTriHienTai.getLongitude());
        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);
        googleMap.addMarker(marker);

        LatLng viTriQuanAn = new LatLng(latitude, longitude);
        MarkerOptions markerVitriquanAn = new MarkerOptions();

        markerVitriquanAn.position(viTriQuanAn);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.bitmapgg);
        markerVitriquanAn.icon(icon);
        googleMap.addMarker(markerVitriquanAn);


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);
        danDuongToiQuanAnController.HienThiDanDuongToiQuanAn(googleMap, duongDan);
    }
}
