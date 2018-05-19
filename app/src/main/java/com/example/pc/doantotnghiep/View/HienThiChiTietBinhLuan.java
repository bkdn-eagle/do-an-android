package com.example.pc.doantotnghiep.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.pc.doantotnghiep.Adapters.AdapterRecyclerHinhBinhLuan;
import com.example.pc.doantotnghiep.Model.BinhLuanModel;
import com.example.pc.doantotnghiep.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HienThiChiTietBinhLuan extends AppCompatActivity {
    CircleImageView cicleImageUser;
    TextView txtTieudebinhluan, txtNodungbinhluan, txtChamDiemBinhLuan;
    RecyclerView recyclerHinhBinhLuan;
    BinhLuanModel binhLuanModel;
    List<Bitmap> bitmapList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_binhluan);

        cicleImageUser = findViewById(R.id.cicleImageUser);
        txtTieudebinhluan = findViewById(R.id.txtTieudebinhluan);
        txtNodungbinhluan = findViewById(R.id.txtNodungbinhluan);
        txtChamDiemBinhLuan = findViewById(R.id.txtChamDiemBinhLuan);
        recyclerHinhBinhLuan = findViewById(R.id.recyclerHinhBinhLuan);

        bitmapList = new ArrayList<>();

        binhLuanModel = getIntent().getParcelableExtra("binhluanmodel");

        txtTieudebinhluan.setText(binhLuanModel.getTieude());
        txtNodungbinhluan.setText(binhLuanModel.getNoidung());
        txtChamDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");
        setHinhAnhUser(cicleImageUser, binhLuanModel.getThanhVienModel().getHinhanhapp());

        for (String listHinh : binhLuanModel.getHinhanhBinhLuanList()) {
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference("hinhanh").child(listHinh);
            long ONE_MEGA = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGA).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);

                    if (bitmapList.size() == binhLuanModel.getHinhanhBinhLuanList().size()) {
                        AdapterRecyclerHinhBinhLuan adapterRecyclerHinhBinhLuan = new AdapterRecyclerHinhBinhLuan(HienThiChiTietBinhLuan.this, R.layout.custom_layout_hinhbinhluan, bitmapList, binhLuanModel,true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HienThiChiTietBinhLuan.this, 2);
                        recyclerHinhBinhLuan.setLayoutManager(layoutManager);
                        recyclerHinhBinhLuan.setAdapter(adapterRecyclerHinhBinhLuan);
                    }
                }
            });
        }

    }

    private void setHinhAnhUser(final CircleImageView circleImageView, String link) {
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference("thanhvien").child(link);
        long ONE_MEGA = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGA).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
}
