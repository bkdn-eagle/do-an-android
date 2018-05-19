package com.example.pc.doantotnghiep.View;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import android.widget.VideoView;

import com.example.pc.doantotnghiep.Adapters.AdapterBinhLuan;
import com.example.pc.doantotnghiep.Adapters.AdapterMonAn;
import com.example.pc.doantotnghiep.Controller.ChiTietQuanAnController;
import com.example.pc.doantotnghiep.Controller.ThucDonController;
import com.example.pc.doantotnghiep.Model.QuanAnModel;
import com.example.pc.doantotnghiep.Model.TienIchModel;
import com.example.pc.doantotnghiep.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChiTietQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    QuanAnModel quanAnModel;
    TextView tongSoBinhLuan, tongSoLuuLai, txtThoiGianHoatDong, txtTrangThaiHoatDong, txtTieuDeToolbar;
    TextView txtTenQuanAn, txtDiaChiQuanAn, txtGioiHanGia, tongSoHinhAnh, tongSoCheckIn, txtTenWifi, txtMatKhauWifi, txtNgayDangWifi;
    ImageView imHinhQuanAn, imgPLayTrailer;
    RecyclerView recyclerBinhLuanChiTietQuanAn, recyclerThucDon;
    GoogleMap googleMap;
    MapFragment mapFragment;
    LinearLayout khungTienTich, khungWifi;
    ChiTietQuanAnController chiTietQuanAnController;

    View khungTinhNang;
    Button btnBinhLuan, btnTaiAnh, btnCheckIn, btnChiaSe, btnThich, btnDatMon, btnDongY;
    VideoView videoTrailer;
    ThucDonController thucDonController;
    final int REQUEST_CHONHINH = 1;
    final int RESULT_IMGTHUCDON = 2;
    List<String> listHinhDuocChon;
    boolean isThich = false;
    long luotThich = 0;
    Toolbar toolbar;
    private Dialog dialog;
    String sdt = "";
    String diaChi = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);

        khungTienTich = findViewById(R.id.khungTienTich);
        imgPLayTrailer = findViewById(R.id.imgPLayTrailer);
        quanAnModel = getIntent().getParcelableExtra("quanan");
        txtGioiHanGia = findViewById(R.id.txtGioiHanGia);
        txtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        txtDiaChiQuanAn = findViewById(R.id.txtDiaChiQuanAn);
        tongSoHinhAnh = findViewById(R.id.tongSoHinhAnh);
        tongSoCheckIn = findViewById(R.id.tongSoCheckIn);
        tongSoBinhLuan = findViewById(R.id.tongSoBinhLuan);
        tongSoLuuLai = findViewById(R.id.tongSoLuuLai);
        txtThoiGianHoatDong = findViewById(R.id.txtThoiGianHoatDong);
        txtTrangThaiHoatDong = findViewById(R.id.txtTrangThaiHoatDong);
        imHinhQuanAn = findViewById(R.id.imHinhQuanAn);
        txtTieuDeToolbar = findViewById(R.id.txtTieuDeToolbar);
        txtNgayDangWifi = findViewById(R.id.txtNgayDangWifi);
        txtTenWifi = findViewById(R.id.txtTenWifi);
        txtMatKhauWifi = findViewById(R.id.txtMatKhauWifi);
        khungWifi = findViewById(R.id.khungWifi);
        recyclerBinhLuanChiTietQuanAn = findViewById(R.id.recyclerBinhLuanChiTietQuanAn);
        recyclerThucDon = findViewById(R.id.recyclerThucDon);
        khungTinhNang = findViewById(R.id.khungTinhNang);
        btnCheckIn = findViewById(R.id.btnCheckIn);
        btnTaiAnh = findViewById(R.id.btnTaiAnh);
        btnBinhLuan = findViewById(R.id.btnBinhLuan);
        btnChiaSe = findViewById(R.id.btnChiaSe);
        btnThich = findViewById(R.id.btnThich);
        btnDatMon = findViewById(R.id.btnDatMon);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        videoTrailer = findViewById(R.id.videoTrailer);
        listHinhDuocChon = new ArrayList<>();

        videoTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaController mediaController = new MediaController(ChiTietQuanAnActivity.this);
                videoTrailer.setMediaController(mediaController);
                imgPLayTrailer.setVisibility(View.GONE);
                videoTrailer.start();
            }
        });

        imHinhQuanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iHienThiHinhAnhQuanAn = new Intent(ChiTietQuanAnActivity.this, HienThiHinhAnhQuanAn.class);
                iHienThiHinhAnhQuanAn.putExtra("quananmodel", quanAnModel);
                startActivity(iHienThiHinhAnhQuanAn);
            }
        });

        btnTaiAnh.setOnClickListener(this);
        btnCheckIn.setOnClickListener(this);
        btnChiaSe.setOnClickListener(this);
        btnBinhLuan.setOnClickListener(this);
        btnThich.setOnClickListener(this);
        btnDatMon.setOnClickListener(this);
        khungTinhNang.setOnClickListener(this);

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        chiTietQuanAnController = new ChiTietQuanAnController();
        thucDonController = new ThucDonController();
        HienThiChiTiet();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude(), quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude());
        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);
        marker.title(quanAnModel.getTenquanan());
        googleMap.addMarker(marker);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);
    }

    private void HienThiChiTiet() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String gioHienTai = simpleDateFormat.format(calendar.getTime());

        String gioMoCua = quanAnModel.getGiomocua();
        String gioDongCua = quanAnModel.getGiodongcua();
        Log.d("giohientai", gioHienTai + " " + gioMoCua + " " + gioDongCua);
        try {
            Date dateHienTai = simpleDateFormat.parse(gioHienTai);
            Date dateMoCua = simpleDateFormat.parse(gioMoCua);
            Date dateDongCua = simpleDateFormat.parse(gioDongCua);
            if (dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)) {
                txtTrangThaiHoatDong.setText("Đang mở cửa");
            } else {
                txtTrangThaiHoatDong.setText("Đang đóng cửa");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (quanAnModel.getGiatoithieu() != 0 && quanAnModel.getGiatoida() != 0) {
            txtGioiHanGia.setText(quanAnModel.getGiatoithieu() + "-" + quanAnModel.getGiatoida());
        } else {
            txtGioiHanGia.setVisibility(View.GONE);
        }
        tongSoLuuLai.setText(quanAnModel.getLuotthich() + "");
        txtTenQuanAn.setText(quanAnModel.getTenquanan());
        tongSoBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
        tongSoHinhAnh.setText(quanAnModel.getHinhanhquanan().size() + "");
        txtThoiGianHoatDong.setText(quanAnModel.getGiomocua() + " - " + quanAnModel.getGiodongcua());
        txtTieuDeToolbar.setText(quanAnModel.getTenquanan());
        txtDiaChiQuanAn.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());

        DownLoadHinhTienIch();
        try {

            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModel.getHinhanhquanan().get(0));
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imHinhQuanAn.setImageBitmap(bitmap);
                }
            });
        } catch (Exception e) {
            Toast.makeText(ChiTietQuanAnActivity.this, "Nothing", Toast.LENGTH_LONG).show();
        }

        // download video
        if (quanAnModel.getVideogioithieu() != null) {
            videoTrailer.setVisibility(View.VISIBLE);
            imgPLayTrailer.setVisibility(View.VISIBLE);
            imHinhQuanAn.setVisibility(View.GONE);
            StorageReference storageVideo = FirebaseStorage.getInstance().getReference().child("video").child(quanAnModel.getVideogioithieu());
            storageVideo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    videoTrailer.setVideoURI(uri);
                    videoTrailer.seekTo(10);

                }
            });
        } else {
            videoTrailer.setVisibility(View.GONE);
            imgPLayTrailer.setVisibility(View.GONE);
            imHinhQuanAn.setVisibility(View.VISIBLE);
        }

        //Load danh sach binh luan cua quan
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerBinhLuanChiTietQuanAn.setLayoutManager(layoutManager);
        AdapterBinhLuan adapterBinhLuan = new AdapterBinhLuan(this, R.layout.custom_layout_binhluan, quanAnModel.getBinhLuanModelList(),quanAnModel);
        recyclerBinhLuanChiTietQuanAn.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();

        chiTietQuanAnController.HienThiDanhSachWifiQuanAn(quanAnModel.getMaquanan(), txtTenWifi, txtMatKhauWifi, txtNgayDangWifi);
        khungWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCapNhatWifi = new Intent(ChiTietQuanAnActivity.this, CapNhatDanhSachWifiActivity.class);
                iCapNhatWifi.putExtra("maquanan", quanAnModel.getMaquanan());
                startActivity(iCapNhatWifi);
            }
        });
        thucDonController.getDanhSachThucDonQuanAnTheoMa(this, quanAnModel.getMaquanan(), recyclerThucDon);
    }

    private void DownLoadHinhTienIch() {

        if (quanAnModel.getTienich() != null) {
            for (String matienich : quanAnModel.getTienich()) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(matienich);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        TienIchModel tienIchModel = dataSnapshot.getValue(TienIchModel.class);
                        try {
                            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhanh());
                            long ONE_MEGABYTE = 1024 * 1024;
                            storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    ImageView imageTienIch = new ImageView(ChiTietQuanAnActivity.this);
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
                                    imageTienIch.setLayoutParams(layoutParams);
                                    layoutParams.setMargins(10, 10, 10, 10);
                                    imageTienIch.setScaleType(ImageView.ScaleType.FIT_XY);
                                    imageTienIch.setPadding(5, 5, 5, 5);
                                    imageTienIch.setImageBitmap(bitmap);
                                    khungTienTich.addView(imageTienIch);
                                }
                            });
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.khungTinhNang:
                Intent iDanDuong = new Intent(ChiTietQuanAnActivity.this, DanDuongToiQuanAnActivity.class);
                iDanDuong.putExtra("latitude", quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude());
                iDanDuong.putExtra("longitude", quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude());
                Log.d("kiemtratoado", quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude() + " - " + quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude());
                startActivity(iDanDuong);
                break;
            case R.id.btnBinhLuan:
                Intent iBinhLuan = new Intent(ChiTietQuanAnActivity.this, BinhLuanActivity.class);
                iBinhLuan.putExtra("tenquanan", quanAnModel.getTenquanan());
                iBinhLuan.putExtra("diachi", quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
                iBinhLuan.putExtra("maquanan", quanAnModel.getMaquanan());
                startActivity(iBinhLuan);
                break;
            case R.id.btnTaiAnh:
                Intent iChonHinhQuanAn = new Intent(ChiTietQuanAnActivity.this, ChonHinhBinhLuanActivity.class);
                startActivityForResult(iChonHinhQuanAn, REQUEST_CHONHINH);
                break;
            case R.id.btnCheckIn:
                Intent iChupHinh = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iChupHinh, RESULT_IMGTHUCDON);
                break;
            case R.id.btnChiaSe:
                Intent iChiaSe = new Intent(Intent.ACTION_SEND);
                iChiaSe.setType("text/plain");
                String shareSub = "Quán này ngon lắm";
                String shareBody = "Tên quán" + "\n" + quanAnModel.getTenquanan() + "\nĐịa chỉ" + quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi() + "\n Nhớ đến nghe.";
                iChiaSe.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                iChiaSe.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(iChiaSe);
                break;
            case R.id.btnThich:

                if (isThich == false) {
                    luotThich = quanAnModel.getLuotthich();
                    luotThich = luotThich + 1;
                    quanAnModel.setLuotthich(luotThich);
                    btnThich.setText("Đã thích");
                    isThich = true;
                } else {
                    luotThich = quanAnModel.getLuotthich();
                    luotThich = luotThich - 1;
                    quanAnModel.setLuotthich(luotThich);
                    btnThich.setText("Thích");
                    isThich = false;
                }
                FirebaseDatabase.getInstance().getReference().child("quanans").child(quanAnModel.getMaquanan()).child("luotthich").setValue(quanAnModel.getLuotthich()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
                break;
            case R.id.btnDatMon:
                dialog = new Dialog(ChiTietQuanAnActivity.this);
                dialog.setContentView(R.layout.layout_nhapthongtin);

                dialog.show();
                btnDongY = dialog.findViewById(R.id.btnDiaChi);
                final EditText soDienThoai = dialog.findViewById(R.id.edNhapSoDienThoai);
                final EditText edDiaChi = dialog.findViewById(R.id.edNhapDiaChi);
                btnDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sdt = soDienThoai.getText().toString();
                        diaChi = edDiaChi.getText().toString();

                        if (sdt.trim().length() == 0 || diaChi.trim().length() == 0) {
                            Toast.makeText(ChiTietQuanAnActivity.this, "Vui lòng nhập đầy đủ dữ liệu", Toast.LENGTH_LONG).show();
                        } else {
                            try {

                                if (checkNumberPhone(sdt)) {
                                    String datMon = "";
                                    long tongTien =0;
                                    if (AdapterMonAn.datMonList.size() != 0) {
                                        String[] TO = {"truongdat4567@gmail.com"};

                                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                        emailIntent.setData(Uri.parse("mailto:"));
                                        emailIntent.setType("text/plain");
                                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);


                                        for (int i = 0; i < AdapterMonAn.datMonList.size(); i++) {
                                            datMon += "Món ăn: " + AdapterMonAn.datMonList.get(i).getTenMonAn() +
                                                    " -- Số lượng: " + AdapterMonAn.datMonList.get(i).getSoLuong() +
                                                    " -- Thành tiền: " + AdapterMonAn.datMonList.get(i).getSoLuong() * AdapterMonAn.datMonList.get(i).getGiaTien() + "\n";
                                            tongTien+= AdapterMonAn.datMonList.get(i).getSoLuong() * AdapterMonAn.datMonList.get(i).getGiaTien();
                                        }
                                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Đặt món ăn ở quán -- " + quanAnModel.getTenquanan());
                                        emailIntent.putExtra(Intent.EXTRA_TEXT, datMon + "\nSố điện thoại: " + sdt + "-- Địa chỉ: " + diaChi+"\nTổng tiền: "+tongTien);

                                        try {
                                            startActivity(Intent.createChooser(emailIntent, "Send mail..."));


                                        } catch (android.content.ActivityNotFoundException ex) {
                                            Toast.makeText(ChiTietQuanAnActivity.this,
                                                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(ChiTietQuanAnActivity.this, R.string.chuadatmon, Toast.LENGTH_LONG).show();
                                    }
                                    dialog.dismiss();
                                } else {
                                    soDienThoai.setText("");
                                }

                            } catch (Exception e) {

                            }
                        }
                    }
                });

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHONHINH) {
            if (resultCode == RESULT_OK) {
                listHinhDuocChon = data.getStringArrayListExtra("listHinhDuocChon");
                if (listHinhDuocChon.size() > 0) {
                    for (String value : listHinhDuocChon) {
                        Uri uri = Uri.fromFile(new File(value));
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh/" + uri.getLastPathSegment());
                        storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            }
                        });
                        FirebaseDatabase.getInstance().getReference().child("hinhanhquanans").child(quanAnModel.getMaquanan()).push().setValue(uri.getLastPathSegment()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ChiTietQuanAnActivity.this, "Thêm hình ảnh quán thành công", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        }
        if (requestCode == RESULT_IMGTHUCDON) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Log.d("bitmap", bitmap.toString());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] dataput = baos.toByteArray();
                FirebaseDatabase.getInstance().getReference().child("hinhanhquanans").child(quanAnModel.getMaquanan()).push().setValue(bitmap.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ChiTietQuanAnActivity.this, "Thêm hình ảnh quán thành công", Toast.LENGTH_LONG).show();
                    }
                });
                FirebaseStorage.getInstance().getReference().child("hinhanh/" + bitmap.toString()).putBytes(dataput);
            }
        }
    }

    public boolean checkNumberPhone(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            Toast.makeText(ChiTietQuanAnActivity.this, "Chuỗi nhập vào không phải là số!", Toast.LENGTH_LONG).show();
            return false;
        } else if (number.length() == 10 || number.length() == 11) {
            if (number.length() == 10) {
                if (number.substring(0, 2).equals("09")) {
                    Toast.makeText(ChiTietQuanAnActivity.this, "Vui lòng chọn phương thức email!", Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    Toast.makeText(ChiTietQuanAnActivity.this, "Số điện thoại không hợp lệ!", Toast.LENGTH_LONG).show();
                    return false;
                }
            } else if (number.substring(0, 2).equals("01")) {
                Toast.makeText(ChiTietQuanAnActivity.this, "Vui lòng chọn phương thức email!", Toast.LENGTH_LONG).show();
                return true;
            } else {
                Toast.makeText(ChiTietQuanAnActivity.this, "Số điện thoại không hợp lệ!", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(ChiTietQuanAnActivity.this, "Số điện thoại không hợp lệ!", Toast.LENGTH_LONG).show();
            return false;
        }

    }
}
