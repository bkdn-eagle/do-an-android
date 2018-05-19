package com.example.pc.doantotnghiep.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.pc.doantotnghiep.Controller.CapNhatWifiController;
import com.example.pc.doantotnghiep.R;

public class CapNhatDanhSachWifiActivity extends AppCompatActivity {
    Button btnCapNhatWifi;
    RecyclerView recyclerView;
    CapNhatWifiController capNhatWifiController;
    String maquanan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_capnhatdanhsachwifi);

        btnCapNhatWifi = findViewById(R.id.btnCapNhatWifi);
        recyclerView = findViewById(R.id.recyclerDanhSachWifi);
        maquanan = getIntent().getStringExtra("maquanan");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        capNhatWifiController = new CapNhatWifiController(this);
        capNhatWifiController.HienThiDanhSachWifi(maquanan, recyclerView);

        btnCapNhatWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCapNhatWifi = new Intent(CapNhatDanhSachWifiActivity.this, PopupCapNhatWifiActivity.class);
                iCapNhatWifi.putExtra("maquanan",maquanan);
                startActivity(iCapNhatWifi);
            }
        });
    }
}
