package com.example.pc.doantotnghiep.View;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.pc.doantotnghiep.Model.ChiNhanhQuanAnModel;
import com.example.pc.doantotnghiep.Model.MonAnModel;
import com.example.pc.doantotnghiep.Model.QuanAnModel;
import com.example.pc.doantotnghiep.Model.ThemThucDonModel;
import com.example.pc.doantotnghiep.Model.ThucDonModel;
import com.example.pc.doantotnghiep.Model.TienIchModel;
import com.example.pc.doantotnghiep.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThemQuanAnActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button btnGioMoCua, btnGioDongCua, btnThemQuanAn;
    String gioMoCua = "", gioDongCua = "", khuvuc;
    Spinner spinnerKhuVuc;
    String maQuanAn;
    Toolbar toolbar;
    List<ThucDonModel> thucDonModelList;
    List<String> selectTienIchList;
    List<String> khuVucList, thucDonList;
    List<String> chiNhanhList;
    ArrayAdapter<String> adapterKhuVuc;
    LinearLayout khungTienIch, khungChuaChiNhanh, khungChiNhanh, khungChuaThucDon;
    List<ThemThucDonModel> themThucDonModelList;
    ImageView imgTam, imgHinhQuan1, imgHinhQuan2, imgHinhQuan3, imgHinhQuan4, imgHinhQuan5, imgHinhQuan6, imgVideo;
    VideoView videoView;
    List<Bitmap> hinhDaChup;
    List<Uri> hinhQuanAn;
    Uri videoSelected = Uri.parse("");
    RadioGroup rdgTrangThai;
    EditText edTenQuan, edGiaToiDa, edGiaToiThieu;
    boolean checkThemChiNhanh = false;

    final int RESULT_IMG1 = 111;
    final int RESULT_IMG2 = 112;
    final int RESULT_IMG3 = 113;
    final int RESULT_IMG4 = 114;
    final int RESULT_IMG5 = 115;
    final int RESULT_IMG6 = 116;
    final int RESULT_IMGTHUCDON = 117;
    final int RESULT_VIDEO = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themquanan);

        khungTienIch = findViewById(R.id.khungTienIch);
        khungChuaChiNhanh = findViewById(R.id.khungChuaChiNhanh);
        khungChiNhanh = findViewById(R.id.khungChiNhanh);
        khungChuaThucDon = findViewById(R.id.khungChuaThucDon);

        btnGioDongCua = findViewById(R.id.btnGioDongCua);
        btnGioMoCua = findViewById(R.id.btnGioMoCua);
        btnThemQuanAn = findViewById(R.id.btnThemQuanAn);

        spinnerKhuVuc = findViewById(R.id.spinnerKhuVuc);

        imgHinhQuan1 = findViewById(R.id.imgHinhQuan1);
        imgHinhQuan2 = findViewById(R.id.imgHinhQuan2);
        imgHinhQuan3 = findViewById(R.id.imgHinhQuan3);
        imgHinhQuan4 = findViewById(R.id.imgHinhQuan4);
        imgHinhQuan5 = findViewById(R.id.imgHinhQuan5);
        imgHinhQuan6 = findViewById(R.id.imgHinhQuan6);
        imgVideo = findViewById(R.id.imgVideo);
        videoView = findViewById(R.id.videoView);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Thêm quán ăn");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rdgTrangThai = findViewById(R.id.rdgTrangThai);
        edGiaToiThieu = findViewById(R.id.edGiaToiThieu);
        edGiaToiDa = findViewById(R.id.edGiaToiDa);
        edTenQuan = findViewById(R.id.edTenQuan);

        thucDonModelList = new ArrayList<>();
        selectTienIchList = new ArrayList<>();
        khuVucList = new ArrayList<>();
        thucDonList = new ArrayList<>();
        chiNhanhList = new ArrayList<>();
        themThucDonModelList = new ArrayList<>();
        hinhDaChup = new ArrayList<>();
        hinhQuanAn = new ArrayList<>();

        adapterKhuVuc = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, khuVucList);
        spinnerKhuVuc.setAdapter(adapterKhuVuc);
        adapterKhuVuc.notifyDataSetChanged();

        btnThemQuanAn.setOnClickListener(this);
        btnGioMoCua.setOnClickListener(this);
        btnGioDongCua.setOnClickListener(this);
        spinnerKhuVuc.setOnItemSelectedListener(this);

        imgHinhQuan1.setOnClickListener(this);
        imgHinhQuan2.setOnClickListener(this);
        imgHinhQuan3.setOnClickListener(this);
        imgHinhQuan4.setOnClickListener(this);
        imgHinhQuan5.setOnClickListener(this);
        imgHinhQuan6.setOnClickListener(this);
        imgVideo.setOnClickListener(this);


        CloneChiNhanh();
        CloneThucDon();

        LayDanhSachKhuVuc();
//      LayDanhSachThucDon();
        LayDanhSachTienIch();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_IMG1:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    imgHinhQuan1.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;

            case RESULT_IMG2:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    imgHinhQuan2.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;

            case RESULT_IMG3:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    imgHinhQuan3.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;

            case RESULT_IMG4:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    imgHinhQuan4.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;

            case RESULT_IMG5:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    imgHinhQuan5.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;

            case RESULT_IMG6:
                if (RESULT_OK == resultCode) {
                    Uri uri = data.getData();
                    imgHinhQuan6.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;

            case RESULT_IMGTHUCDON:
                try {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imgTam.setImageBitmap(bitmap);
                    hinhDaChup.add(bitmap);
                } catch (Exception e) {
                    Toast.makeText(ThemQuanAnActivity.this, R.string.khongchuphinh, Toast.LENGTH_LONG).show();
                }
                break;

            case RESULT_VIDEO:
                if (RESULT_OK == resultCode) {
                    imgVideo.setVisibility(View.GONE);
                    Uri uri = data.getData();
                    videoSelected = uri;
                    videoView.setVideoURI(uri);
                    videoView.start();

                }
                break;
        }

    }

    private void CloneThucDon() {

        final View view = LayoutInflater.from(ThemQuanAnActivity.this).inflate(R.layout.layout_clone_thucdon, null);
        Button btnThemThucDon = view.findViewById(R.id.btnThemThucDon);
        ImageView imgChupHinh = view.findViewById(R.id.imgChupHinh);
        imgTam = imgChupHinh;

        final Spinner spinnerThucDon = view.findViewById(R.id.spinnerThucDon);
        final EditText edTenMon = view.findViewById(R.id.edTenMon);
        final EditText edGiaTien = view.findViewById(R.id.edGiaTien);

        ArrayAdapter<String> adapterThucDon = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, thucDonList);
        spinnerThucDon.setAdapter(adapterThucDon);
        adapterThucDon.notifyDataSetChanged();
        if (thucDonList.size() == 0) {
            LayDanhSachThucDon(adapterThucDon);
        }
        imgChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iChupHinh = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iChupHinh, RESULT_IMGTHUCDON);
            }
        });


        btnThemThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MonAnModel monAnModel = new MonAnModel();
                String tenMon = edTenMon.getText().toString();
                String giaTien = edGiaTien.getText().toString();
                String tenHinh = String.valueOf(Calendar.getInstance().getTimeInMillis()) + ".jpg";
                int position = spinnerThucDon.getSelectedItemPosition();
                String maThucDon = thucDonModelList.get(position).getMathucdon();


                if (tenMon.trim().length() == 0 || giaTien.trim().length() == 0) {
                    Toast.makeText(ThemQuanAnActivity.this, "Vui lòng nhập đầy đủ dữ liệu", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        long b = Integer.parseInt(String.valueOf(edGiaTien.getText()));
                        if (b == 0) {
                            Toast.makeText(ThemQuanAnActivity.this, "Giá tiền phải lớn hơn 0", Toast.LENGTH_LONG).show();
                        } else {
                            monAnModel.setTenmon(tenMon);
                            monAnModel.setGiatien(Long.parseLong(giaTien));
                            monAnModel.setHinhanh(tenHinh);

                            ThemThucDonModel themThucDonModel = new ThemThucDonModel();
                            themThucDonModel.setMathucdon(maThucDon);
                            themThucDonModel.setMonAnModel(monAnModel);
                            themThucDonModelList.add(themThucDonModel);

                            CloneThucDon();
                            v.setVisibility(View.GONE);
                        }
                    } catch (NumberFormatException ne) {
                        Toast.makeText(ThemQuanAnActivity.this, "Nhập giá tiền chưa đúng", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });
        khungChuaThucDon.addView(view);
    }

    private void CloneChiNhanh() {
        final View view = LayoutInflater.from(ThemQuanAnActivity.this).inflate(R.layout.layout_clone_chinhanh, null);
        ImageButton btnThemChiNhanh = view.findViewById(R.id.btnThemChiNhanh);
        final ImageButton btnXoaChiNhanh = view.findViewById(R.id.btnXoaChiNhanh);
        btnThemChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = view.findViewById(R.id.edTenChiNhanh);
                String tenChiNhanh = editText.getText().toString();
                if (tenChiNhanh.trim().length() != 0) {
                    v.setVisibility(View.GONE);
                    btnXoaChiNhanh.setVisibility(View.VISIBLE);
                    btnXoaChiNhanh.setTag(tenChiNhanh);
                    chiNhanhList.add(tenChiNhanh);
                    CloneChiNhanh();
                } else {
                    Toast.makeText(ThemQuanAnActivity.this, "Nhập chưa đúng", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnXoaChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenChiNhanh = v.getTag().toString();
                chiNhanhList.remove(tenChiNhanh);
                khungChuaChiNhanh.removeView(view);
            }
        });
        khungChuaChiNhanh.addView(view);
    }

    private void LayDanhSachKhuVuc() {
        FirebaseDatabase.getInstance().getReference().child("khuvucs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tenKhuVuc = snapshot.getKey();
                    Log.d("kiemtrakhuvuc", tenKhuVuc);
                    khuVucList.add(tenKhuVuc);

                }
                adapterKhuVuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void LayDanhSachThucDon(final ArrayAdapter<String> adapterThucDon) {
        FirebaseDatabase.getInstance().getReference().child("thucdons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ThucDonModel thucDonModel = new ThucDonModel();
                    String key = snapshot.getKey();
                    String value = snapshot.getValue(String.class);

                    thucDonModel.setTenthucdon(value);
                    thucDonModel.setMathucdon(key);

                    thucDonModelList.add(thucDonModel);
                    thucDonList.add(value);
                }
                adapterThucDon.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void LayDanhSachTienIch() {
        FirebaseDatabase.getInstance().getReference().child("quanlytienichs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String maTienIch = snapshot.getKey();
                    TienIchModel tienIchModel = snapshot.getValue(TienIchModel.class);
                    tienIchModel.setMaTienIch(maTienIch);

                    CheckBox checkBox = new CheckBox(ThemQuanAnActivity.this);
                    checkBox.setText(tienIchModel.getTentienich());
                    checkBox.setTag(maTienIch);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            String maTienIch = compoundButton.getTag().toString();
                            if (b) {
                                selectTienIchList.add(maTienIch);
                            } else {
                                selectTienIchList.remove(maTienIch);
                            }
                        }
                    });
                    khungTienIch.addView(checkBox);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(final View v) {
        int id = v.getId();
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        switch (id) {
            case R.id.btnGioDongCua:
                TimePickerDialog timePickerDialog = new TimePickerDialog(ThemQuanAnActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioDongCua = hourOfDay + ":" + minute;
                        ((Button) v).setText(gioDongCua);
                    }
                }, gio, phut, true);

                timePickerDialog.show();
                break;

            case R.id.btnGioMoCua:
                TimePickerDialog moCuatimePickerDialog = new TimePickerDialog(ThemQuanAnActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioMoCua = hourOfDay + ":" + minute;
                        ((Button) v).setText(gioMoCua);
                    }
                }, gio, phut, true);

                moCuatimePickerDialog.show();
                break;

            case R.id.imgHinhQuan1:
                ChonHinhTuGallary(RESULT_IMG1);
                break;
            case R.id.imgHinhQuan2:
                ChonHinhTuGallary(RESULT_IMG2);
                break;
            case R.id.imgHinhQuan3:
                ChonHinhTuGallary(RESULT_IMG3);
                break;
            case R.id.imgHinhQuan4:
                ChonHinhTuGallary(RESULT_IMG4);
                break;
            case R.id.imgHinhQuan5:
                ChonHinhTuGallary(RESULT_IMG5);
                break;
            case R.id.imgHinhQuan6:
                ChonHinhTuGallary(RESULT_IMG6);
                break;
            case R.id.imgVideo:
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Chọn hình..."), RESULT_VIDEO);
                break;
            case R.id.btnThemQuanAn:
                ThemQuanAn();
                break;

        }
    }

    private void ThemQuanAn() {
        String tenQuanAn = edTenQuan.getText().toString();
        String giaToiDa = edGiaToiDa.getText().toString();
        String giaToiThieu = edGiaToiThieu.getText().toString();

        int idRadioSelected = rdgTrangThai.getCheckedRadioButtonId();
        boolean giaoHang = true;
        DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();
        if (idRadioSelected == R.id.rdGiaoHang) {
            giaoHang = true;
        }
        if (idRadioSelected == R.id.rdKhongGiaoHang) {
            giaoHang = false;
        }

        if (tenQuanAn.trim().length() == 0 || giaToiDa.trim().length() == 0 || giaToiThieu.trim().length() == 0) {
            Toast.makeText(ThemQuanAnActivity.this, "Vui lòng nhập đầy đủ dữ liệu", Toast.LENGTH_LONG).show();
        } else if (videoSelected == null) {
            Toast.makeText(ThemQuanAnActivity.this, "Vui lòng nhập đầy đủ dữ liệu", Toast.LENGTH_LONG).show();
        } else if (chiNhanhList.size() == 0) {
            Toast.makeText(ThemQuanAnActivity.this, "Bạn phải thêm chi nhánh", Toast.LENGTH_LONG).show();
        } else {
            try {

                long giaTD = Long.parseLong(String.valueOf(edGiaToiDa.getText()));
                long giaTT = Long.parseLong(String.valueOf(edGiaToiThieu.getText()));
                if (giaTD <= 0 || giaTT <= 0) {
                    Toast.makeText(ThemQuanAnActivity.this, "Giá tiền phải lớn hơn 0", Toast.LENGTH_LONG).show();
                } else {

                    DatabaseReference nodeQuanAn = nodeRoot.child("quanans");
                    maQuanAn = nodeQuanAn.push().getKey();

                    nodeRoot.child("khuvucs").child(khuvuc).push().setValue(maQuanAn);

                    for (String chinhanh : chiNhanhList) {
                        String urlGeoCoding = "https://maps.googleapis.com/maps/api/geocode/json?address=" + chinhanh.replace(" ", "%20") + "&key=AIzaSyCSuPONeTxmfkcWuFqnNtK0hkhzf94CUNQ";
                        DownloadToaDo downloadToaDo = new DownloadToaDo();
                        downloadToaDo.execute(urlGeoCoding);
                    }

                    QuanAnModel quanAnModel = new QuanAnModel();
                    quanAnModel.setTenquanan(tenQuanAn);
                    quanAnModel.setGiatoida(giaTD);
                    quanAnModel.setGiatoithieu(giaTT);
                    quanAnModel.setGiaohang(giaoHang);
                    quanAnModel.setVideogioithieu(videoSelected.getLastPathSegment());
                    quanAnModel.setTienich(selectTienIchList);
                    quanAnModel.setLuotthich(0);
                    quanAnModel.setGiomocua(gioMoCua);
                    quanAnModel.setGiodongcua(gioDongCua);
                    FirebaseStorage.getInstance().getReference().child("video/" + videoSelected.getLastPathSegment()).putFile(videoSelected);
                    nodeQuanAn.child(maQuanAn).setValue(quanAnModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ThemQuanAnActivity.this, "Đã thêm thành công", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ThemQuanAnActivity.this, TrangChuActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                }
            } catch (NumberFormatException ne) {
                Toast.makeText(ThemQuanAnActivity.this, "Nhập giá tiền chưa đúng", Toast.LENGTH_LONG).show();
            }
        }


        for (Uri hinhquan : hinhQuanAn) {
            FirebaseStorage.getInstance().getReference().child("hinhanh/" + hinhquan.getLastPathSegment()).putFile(hinhquan);
            nodeRoot.child("hinhanhquanans").child(maQuanAn).push().child(hinhquan.getLastPathSegment());

        }
        for (int i = 0; i < themThucDonModelList.size(); i++) {
            nodeRoot.child("thucdonquanans").child(maQuanAn).child(themThucDonModelList.get(i).getMathucdon()).push().setValue(themThucDonModelList.get(i).getMonAnModel());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap bitmap = hinhDaChup.get(i);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            FirebaseStorage.getInstance().getReference().child("hinhanh/" + themThucDonModelList.get(i).getMonAnModel().getHinhanh()).putBytes(data);
        }

    }

    class DownloadToaDo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("kiemtralatitude", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray results = jsonObject.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject object = results.getJSONObject(i);
                    String address = object.getString("formatted_address");
                    JSONObject geometry = object.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    double latitude = (double) location.get("lat");
                    double longitude = (double) location.get("lng");

                    ChiNhanhQuanAnModel chiNhanhQuanAnModel = new ChiNhanhQuanAnModel();
                    chiNhanhQuanAnModel.setDiachi(address);
                    chiNhanhQuanAnModel.setLatitude(latitude);
                    chiNhanhQuanAnModel.setLongitude(longitude);

                    FirebaseDatabase.getInstance().getReference().child("chinhanhquanans").child(maQuanAn).push().setValue(chiNhanhQuanAnModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkThemChiNhanh = true;
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void ChonHinhTuGallary(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình..."), requestCode);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spinnerKhuVuc:
                khuvuc = khuVucList.get(i);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
