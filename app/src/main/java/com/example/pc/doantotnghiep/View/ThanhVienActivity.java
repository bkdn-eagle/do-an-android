package com.example.pc.doantotnghiep.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.pc.doantotnghiep.Controller.DangKyController;
import com.example.pc.doantotnghiep.Model.ThanhVienModel;
import com.example.pc.doantotnghiep.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThanhVienActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ThanhVienModel thanhVienModel;
    TextView tvThanhVien;
    CircleImageView circleImageView;
    final int RESULT_IMG1 = 1;
    EditText edTenThanhVien;
    Button btnLuuThanhVien;
    DangKyController dangKyController;
    Uri uri;
    ImageView imgHinhThanhVien;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhvien);
        thanhVienModel = new ThanhVienModel();
        sharedPreferences = getSharedPreferences("luunguoidung", MODE_PRIVATE);
        final String mauser = sharedPreferences.getString("mauser", "");
        DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();
        tvThanhVien = findViewById(R.id.tvThanhVien);
        circleImageView = findViewById(R.id.cicleImageUser);
        edTenThanhVien = findViewById(R.id.edTenThanhVien);
        btnLuuThanhVien = findViewById(R.id.btnLuuThanhVien);
        imgHinhThanhVien = findViewById(R.id.imgHinhThanhVien);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("      Thông Tin Thành Viên");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thanhVienModel = dataSnapshot.child("thanhviens").child(mauser).getValue(ThanhVienModel.class);
                try{
                    tvThanhVien.setText(thanhVienModel.getHoten());
                    StorageReference storageHinhUser1 = FirebaseStorage.getInstance().getReference("thanhvien").child(thanhVienModel.getHinhanhapp());
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhUser1.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            circleImageView.setImageBitmap(bitmap);
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(ThanhVienActivity.this, "Tài khoản này đã bị xóa",Toast.LENGTH_LONG).show();
                    System.exit(0);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonHinhTuGallary(RESULT_IMG1);
            }
        });
        tvThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvThanhVien.setVisibility(View.GONE);
                edTenThanhVien.setVisibility(View.VISIBLE);
            }
        });
        btnLuuThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edTenThanhVienMoi = edTenThanhVien.getText().toString();

                try {
                    String uriHinhAnh = uri.getLastPathSegment();
                    FirebaseStorage.getInstance().getReference().child("thanhvien/" + uriHinhAnh).putFile(uri);
                    FirebaseDatabase.getInstance().getReference().child("thanhviens").child(mauser).child("hinhanhapp").setValue(uriHinhAnh);
                } catch (Exception e) {
                    Toast.makeText(ThanhVienActivity.this, "Bạn không thay đổi ảnh", Toast.LENGTH_LONG).show();
                    Log.d("loiExcep", e.getMessage().toString());
                }
                if (edTenThanhVienMoi.trim().length() != 0) {
                    tvThanhVien.setText(edTenThanhVienMoi);
                    FirebaseDatabase.getInstance().getReference().child("thanhviens").child(mauser).child("hoten").setValue(edTenThanhVienMoi);
                    tvThanhVien.setVisibility(View.VISIBLE);
                    edTenThanhVien.setVisibility(View.GONE);
                }

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void ChonHinhTuGallary(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình..."), requestCode);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_IMG1:
                if (RESULT_OK == resultCode) {
                    uri = data.getData();
                    if(uri!=null){
                        circleImageView.setImageURI(uri);
                    }
                   else {
                        Toast.makeText(ThanhVienActivity.this,"Bạn không chọn hình đại diện mới???",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}
