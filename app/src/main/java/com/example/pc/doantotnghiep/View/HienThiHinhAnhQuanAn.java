package com.example.pc.doantotnghiep.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pc.doantotnghiep.Adapters.AdapterHienThiHinhAnQuanAn;
import com.example.pc.doantotnghiep.Adapters.AdapterHienThiHinhBinhLuanDuocChon;
import com.example.pc.doantotnghiep.Model.MonAnModel;
import com.example.pc.doantotnghiep.Model.QuanAnModel;
import com.example.pc.doantotnghiep.Model.ThemThucDonModel;
import com.example.pc.doantotnghiep.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HienThiHinhAnhQuanAn extends AppCompatActivity {

    QuanAnModel quanAnModel;
    List<Bitmap> bitmapList;
    LinearLayout khungChuaHinh;
    RecyclerView recyclerChonHinhAnhQuan;

    AdapterHienThiHinhAnQuanAn adapterHienThiHinhBinhLuanDuocChon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hienthihinhanhquanan);

        bitmapList = new ArrayList<>();
        quanAnModel = getIntent().getParcelableExtra("quananmodel");
        khungChuaHinh= findViewById(R.id.khungChuaHinh);
        recyclerChonHinhAnhQuan=findViewById(R.id.recyclerChonHinhAnhQuan);

        for (String listHinh : quanAnModel.getHinhanhquanan()) {
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference("hinhanh").child(listHinh);
            long ONE_MEGA = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGA).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if (bitmapList.size() == quanAnModel.getHinhanhquanan().size()) {
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HienThiHinhAnhQuanAn.this, 2);
                        adapterHienThiHinhBinhLuanDuocChon = new AdapterHienThiHinhAnQuanAn(HienThiHinhAnhQuanAn.this, R.layout.custom_layout_hienthibinhluanduocchon, bitmapList);
                        recyclerChonHinhAnhQuan.setLayoutManager(layoutManager);
                        recyclerChonHinhAnhQuan.setAdapter(adapterHienThiHinhBinhLuanDuocChon);
                        adapterHienThiHinhBinhLuanDuocChon.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
