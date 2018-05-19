package com.example.pc.doantotnghiep.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.pc.doantotnghiep.Adapters.AdapterChonHinhBinhLuan;
import com.example.pc.doantotnghiep.Adapters.AdapterHienThiHinhBinhLuanDuocChon;
import com.example.pc.doantotnghiep.Controller.BinhLuanController;
import com.example.pc.doantotnghiep.Model.BinhLuanModel;
import com.example.pc.doantotnghiep.R;

import java.util.ArrayList;
import java.util.List;

public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtTenQuanAn, txtDiaChiQuanAn, txtDangBinhLuan;
    Toolbar toolbar;
    private Dialog dialog;
    ImageButton btnChonHinh, btnChoDiem;
    final int REQUEST_CHONHINH = 1;
    RecyclerView recyclerViewHinhBinhLuan;
    AdapterHienThiHinhBinhLuanDuocChon adapterHienThiHinhBinhLuanDuocChon;
    String maquanan;
    EditText edTieuDeBinhLuan, edNoiDungBinhLuan;
    BinhLuanController binhLuanController;
    SharedPreferences sharedPreferences;
    List<String> listHinhDuocChon;
    long chamDiem = 0;

    Button btnDongY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);

        String tenquan = getIntent().getStringExtra("tenquanan");
        String diachi = getIntent().getStringExtra("diachi");
        maquanan = getIntent().getStringExtra("maquanan");

        txtDiaChiQuanAn = findViewById(R.id.txtDiaChiQuanAn);
        txtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        txtDangBinhLuan = findViewById(R.id.txtDangBinhLuan);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        recyclerViewHinhBinhLuan = findViewById(R.id.recyclerChonHinhBinhLuan);
        edTieuDeBinhLuan = findViewById(R.id.edTieuDeBinhLuan);
        edNoiDungBinhLuan = findViewById(R.id.edNoiDungBinhLuan);

        listHinhDuocChon = new ArrayList<>();
        binhLuanController = new BinhLuanController();
        sharedPreferences = getSharedPreferences("luunguoidung", MODE_PRIVATE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTenQuanAn.setText(tenquan);
        txtDiaChiQuanAn.setText(diachi);

        btnChoDiem = findViewById(R.id.btnChoDiem);
        btnChonHinh = findViewById(R.id.btnChonHinh);
        btnChonHinh.setOnClickListener(this);
        btnChoDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(BinhLuanActivity.this);
                dialog.setContentView(R.layout.layout_chodiem);

                dialog.show();
                btnDongY = dialog.findViewById(R.id.btnOk);
                final EditText edChoDiem = dialog.findViewById(R.id.edChoDiem);
                btnDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String diem = edChoDiem.getText().toString();
                        if (diem.trim().length() == 0) {
                            Toast.makeText(BinhLuanActivity.this, "Vui lòng nhập đầy đủ dữ liệu", Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                long d = Integer.parseInt(String.valueOf(edChoDiem.getText()));
                                if(0 <= d && d <=10){
                                    chamDiem = d;
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(BinhLuanActivity.this, "Nhập điểm chưa đúng", Toast.LENGTH_LONG).show();
                                    edChoDiem.setText("");
                                }

                            } catch (Exception e) {
                                Toast.makeText(BinhLuanActivity.this, "Nhập điểm chưa đúng", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });
        txtDangBinhLuan.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnChonHinh:
                Intent iChonHinhBinhLuan = new Intent(BinhLuanActivity.this, ChonHinhBinhLuanActivity.class);
                startActivityForResult(iChonHinhBinhLuan, REQUEST_CHONHINH);
                break;
            case R.id.txtDangBinhLuan:
                BinhLuanModel binhLuanModel = new BinhLuanModel();
                String tieude = edTieuDeBinhLuan.getText().toString();
                String noidung = edNoiDungBinhLuan.getText().toString();
                String mauser = sharedPreferences.getString("mauser", "");
                if(tieude.trim().length()==0|| noidung.trim().length()==0){
                    Toast.makeText(BinhLuanActivity.this, "Vui lòng nhập đầy đủ dữ liệu", Toast.LENGTH_LONG).show();
                }else {
                    binhLuanModel.setTieude(tieude);
                    binhLuanModel.setNoidung(noidung);
                    binhLuanModel.setMauser(mauser);
                    binhLuanModel.setChamdiem(chamDiem);
                    binhLuanModel.setLuotthich(0);

                    binhLuanController.ThemBinhLuan(maquanan, binhLuanModel, listHinhDuocChon);
                    Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_LONG).show();
                    finish();
                }


                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHONHINH) {
            if (resultCode == RESULT_OK) {
                listHinhDuocChon = data.getStringArrayListExtra("listHinhDuocChon");
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
                adapterHienThiHinhBinhLuanDuocChon = new AdapterHienThiHinhBinhLuanDuocChon(this, R.layout.custom_layout_hienthibinhluanduocchon, listHinhDuocChon);
                recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                recyclerViewHinhBinhLuan.setAdapter(adapterHienThiHinhBinhLuanDuocChon);
                adapterHienThiHinhBinhLuanDuocChon.notifyDataSetChanged();
            }
        }
    }
}
