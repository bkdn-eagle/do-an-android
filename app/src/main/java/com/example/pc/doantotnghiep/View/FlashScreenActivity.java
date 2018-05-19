package com.example.pc.doantotnghiep.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;

import com.example.pc.doantotnghiep.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class FlashScreenActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    TextView txtPhienban;
    GoogleApiClient googleApiClient;
    public static final int REQUEST_PERMISSION_LOCATION = 1;
    SharedPreferences sharedPreferencesToaDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        MultiDex.install(this);

        sharedPreferencesToaDo = getSharedPreferences("ToaDoHienTai", MODE_PRIVATE);
        txtPhienban = findViewById(R.id.txtPhienban);
        // Get Toa do nguoi dung hien tai
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        int checkQuyen = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (checkQuyen != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        } else {
            googleApiClient.connect();
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    googleApiClient.connect();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        Location viTriHienTai = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (viTriHienTai != null) {
            SharedPreferences.Editor shareEdit = sharedPreferencesToaDo.edit();
            shareEdit.putString("latitude", String.valueOf(viTriHienTai.getLatitude()));
            shareEdit.putString("longitude", String.valueOf(viTriHienTai.getLongitude()));
            shareEdit.commit();
            Log.d("testToaDo", viTriHienTai.getLatitude() + " // " + viTriHienTai.getLongitude());
        } else {
            Log.d("testToaDosai", "chet cu roi");
        }
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            txtPhienban.setText(getString(R.string.phienban) + " " + packageInfo.versionName);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent iDangNhap = new Intent(FlashScreenActivity.this, DangNhapActivity.class);
                    startActivity(iDangNhap);
                    finish();
                }
            }, 2000);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
