package com.example.pc.doantotnghiep.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.pc.doantotnghiep.Adapters.AdapterChonHinhBinhLuan;
import com.example.pc.doantotnghiep.Model.ChonHinhBinhLuanModel;
import com.example.pc.doantotnghiep.R;

import java.util.ArrayList;
import java.util.List;

public class ChonHinhBinhLuanActivity extends AppCompatActivity {
    List<ChonHinhBinhLuanModel> listDuongDan;
    List<String> listHinhDuocChon;
    RecyclerView recyclerView;
    AdapterChonHinhBinhLuan adapterChonHinhBinhLuan;
    TextView txtXong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chonhinh_binhluan);

        listDuongDan = new ArrayList<>();
        listHinhDuocChon = new ArrayList<>();

        txtXong = findViewById(R.id.txtXong);
        recyclerView = findViewById(R.id.recyclerChonHinhBinhLuan);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        adapterChonHinhBinhLuan = new AdapterChonHinhBinhLuan(this, R.layout.custom_layout_chonhinhbinhluan, listDuongDan);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterChonHinhBinhLuan);

        int checkQuyen = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (checkQuyen != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getTatCaHinhAnh();
        }
        txtXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ChonHinhBinhLuanModel chonHinhBinhLuanModel : listDuongDan) {
                    if (chonHinhBinhLuanModel.isCheckBox()) {
                        listHinhDuocChon.add(chonHinhBinhLuanModel.getDuongDan());
                    }
                }
                Intent data = getIntent();
                data.putStringArrayListExtra("listHinhDuocChon", (ArrayList<String>) listHinhDuocChon);
                setResult(RESULT_OK,data);
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getTatCaHinhAnh();
            }
        }
    }

    public void getTatCaHinhAnh() {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String duongdan = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            ChonHinhBinhLuanModel chonHinhBinhLuanModel = new ChonHinhBinhLuanModel(duongdan, false);
            listDuongDan.add(chonHinhBinhLuanModel);
            adapterChonHinhBinhLuan.notifyDataSetChanged();
            cursor.moveToNext();
            Log.d("gethinhanh", duongdan);
        }
    }
}
