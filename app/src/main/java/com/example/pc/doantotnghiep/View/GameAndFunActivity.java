package com.example.pc.doantotnghiep.View;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.pc.doantotnghiep.R;

public class GameAndFunActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gameandfun);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iDangNhap = new Intent(GameAndFunActivity.this, TrangChuActivity.class);
                startActivity(iDangNhap);
                finish();
            }
        }, 3000);
    }
}
