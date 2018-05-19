package com.example.pc.doantotnghiep.Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.example.pc.doantotnghiep.Controller.Interfaces.AnGiInterface;
import com.example.pc.doantotnghiep.Controller.Interfaces.OdauInterface;
import com.example.pc.doantotnghiep.View.FlashScreenActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by PC on 21/03/2018.
 */

public class QuanAnModel implements Parcelable {
    Boolean giaohang;
    String giodongcua;
    String giomocua;
    String tenquanan;
    String videogioithieu;
    String maquanan;
    long luotthich;
    long giatoida;
    long giatoithieu;
    List<String> tienich, hinhanhquanan;
    List<BinhLuanModel> binhLuanModelList;
    List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList;
    List<ThucDonModel> thucDonModelList;



    public List<ThucDonModel> getThucDonModelList() {
        return thucDonModelList;
    }

    public void setThucDonModelList(List<ThucDonModel> thucDonModelList) {
        this.thucDonModelList = thucDonModelList;
    }


    private DatabaseReference nodeRoot;

    public long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(long giatoida) {
        this.giatoida = giatoida;
    }

    public long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }


    protected QuanAnModel(Parcel in) {
        byte tmpGiaohang = in.readByte();
        giaohang = tmpGiaohang == 0 ? null : tmpGiaohang == 1;
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();
        videogioithieu = in.readString();
        maquanan = in.readString();
        luotthich = in.readLong();
        giatoida = in.readLong();
        giatoithieu = in.readLong();
        tienich = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
        chiNhanhQuanAnModelList = new ArrayList<ChiNhanhQuanAnModel>();

        in.readTypedList(chiNhanhQuanAnModelList, ChiNhanhQuanAnModel.CREATOR);
        binhLuanModelList = new ArrayList<BinhLuanModel>();
        in.readTypedList(binhLuanModelList, BinhLuanModel.CREATOR);
    }

    public static final Creator<QuanAnModel> CREATOR = new Creator<QuanAnModel>() {
        @Override
        public QuanAnModel createFromParcel(Parcel in) {
            return new QuanAnModel(in);
        }

        @Override
        public QuanAnModel[] newArray(int size) {
            return new QuanAnModel[size];
        }
    };


    public List<ChiNhanhQuanAnModel> getChiNhanhQuanAnModelList() {
        return chiNhanhQuanAnModelList;
    }

    public void setChiNhanhQuanAnModelList(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList) {
        this.chiNhanhQuanAnModelList = chiNhanhQuanAnModelList;
    }

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public QuanAnModel() {

        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public Boolean getGiaohang() {
        return giaohang;
    }

    public void setGiaohang(Boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }


    public void getDanhSachQuanAn(final OdauInterface odauInterface, final Location viTriHienTai) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            List<QuanAnModel> quanAnModelList = new ArrayList<>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");

                // Lấy danh sách quán ăn
                for (DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren()) {
                    QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
                    quanAnModel.setMaquanan(valueQuanAn.getKey());

                    // Lay danh sach hinh anh quan an theo ma
                    DataSnapshot dataSnapshotHinhQuanAn = dataSnapshot.child("hinhanhquanans").child(valueQuanAn.getKey());
                    List<String> hinhanhlist = new ArrayList<>();
                    for (DataSnapshot valueHinhAnh : dataSnapshotHinhQuanAn.getChildren()) {
                        hinhanhlist.add(valueHinhAnh.getValue(String.class));
                    }
                    quanAnModel.setHinhanhquanan(hinhanhlist);

                    // Lay danh sach binh luan cua quan an
                    DataSnapshot snapshot1BinhLuan = dataSnapshot.child("binhluans").child(quanAnModel.getMaquanan());
                    List<BinhLuanModel> binhLuanModels = new ArrayList<>();
                    for (DataSnapshot valueBinhLuan : snapshot1BinhLuan.getChildren()) {
                        try {
                            BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                            binhLuanModel.setMabinhluan(valueBinhLuan.getKey());
                            ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                            binhLuanModel.setThanhVienModel(thanhVienModel);

                            List<String> hinhAnhBinhLuanList = new ArrayList<>();

                            DataSnapshot dataSnapshotNodeHinhAnhBinhLuan = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getMabinhluan());
                            for (DataSnapshot valueHinhBinhLuan : dataSnapshotNodeHinhAnhBinhLuan.getChildren()) {
                                hinhAnhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                            }
                            binhLuanModel.setHinhanhBinhLuanList(hinhAnhBinhLuanList);
                            binhLuanModels.add(binhLuanModel);
                        } catch (Exception e) {

                        }
                    }

                    quanAnModel.setBinhLuanModelList(binhLuanModels);

                    // Lay chi nhanh quan an
                    DataSnapshot snapshotChiNhanh = dataSnapshot.child("chinhanhquanans").child(quanAnModel.getMaquanan());
                    List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();
                    for (DataSnapshot valueChiNhanhQuanAn : snapshotChiNhanh.getChildren()) {
                        ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);
                        Location viTriQuanAn = new Location("");
                        viTriQuanAn.setLatitude(chiNhanhQuanAnModel.getLatitude());
                        viTriQuanAn.setLongitude(chiNhanhQuanAnModel.getLongitude());

                        double khoangCach = viTriHienTai.distanceTo(viTriQuanAn) / 500;
                        Log.d("kiemtrakhoangcach", khoangCach + "  ");
                        chiNhanhQuanAnModel.setKhoangcach(khoangCach);
                        chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
                    }


                    quanAnModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);
                    quanAnModelList.add(quanAnModel);
                    Log.d("quanantest", quanAnModelList.size() + "");
                    // odauInterface.getDanhSachQuanAnModel(quanAnModel);
                }
                for (int i = 0; i < quanAnModelList.size(); i++) {
                    try {
                        for (int j = i + 1; j < quanAnModelList.size(); j++) {
                            QuanAnModel qi = quanAnModelList.get(i);
                            QuanAnModel qj = quanAnModelList.get(j);

                            Double qia = qi.getChiNhanhQuanAnModelList().get(0).getKhoangcach();
                            Double qib = qj.getChiNhanhQuanAnModelList().get(0).getKhoangcach();


                            if (qia >= qib) {
                                Collections.swap(quanAnModelList, i, j);
                            }
                        }
                    } catch (Exception c) {

                    }

                }
                for (int i = 0; i < quanAnModelList.size(); i++) {
                    odauInterface.getDanhSachQuanAnModel(quanAnModelList.get(i));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (giaohang == null ? 0 : giaohang ? 1 : 2));
        parcel.writeString(giodongcua);
        parcel.writeString(giomocua);
        parcel.writeString(tenquanan);
        parcel.writeString(videogioithieu);
        parcel.writeString(maquanan);
        parcel.writeLong(luotthich);
        parcel.writeLong(giatoida);
        parcel.writeLong(giatoithieu);
        parcel.writeStringList(tienich);
        parcel.writeStringList(hinhanhquanan);
        parcel.writeTypedList(chiNhanhQuanAnModelList);
        parcel.writeTypedList(binhLuanModelList);

    }

    public void getDanhSachQuanAnAnGi(final AnGiInterface anGiInterface, final Location viTriHienTai) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            List<QuanAnModel> quanAnModelList = new ArrayList<>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");

                // Lấy danh sách quán ăn
                for (DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren()) {
                    QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
                    quanAnModel.setMaquanan(valueQuanAn.getKey());

                    // Lay danh sach hinh anh quan an theo ma
                    DataSnapshot dataSnapshotHinhQuanAn = dataSnapshot.child("hinhanhquanans").child(valueQuanAn.getKey());
                    List<String> hinhanhlist = new ArrayList<>();
                    for (DataSnapshot valueHinhAnh : dataSnapshotHinhQuanAn.getChildren()) {
                        hinhanhlist.add(valueHinhAnh.getValue(String.class));
                    }
                    quanAnModel.setHinhanhquanan(hinhanhlist);

                    // Lay danh sach binh luan cua quan an
                    DataSnapshot snapshot1BinhLuan = dataSnapshot.child("binhluans").child(quanAnModel.getMaquanan());
                    List<BinhLuanModel> binhLuanModels = new ArrayList<>();
                    for (DataSnapshot valueBinhLuan : snapshot1BinhLuan.getChildren()) {
                        try {
                            BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                            binhLuanModel.setMabinhluan(valueBinhLuan.getKey());
                            ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                            binhLuanModel.setThanhVienModel(thanhVienModel);

                            List<String> hinhAnhBinhLuanList = new ArrayList<>();

                            DataSnapshot dataSnapshotNodeHinhAnhBinhLuan = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getMabinhluan());
                            for (DataSnapshot valueHinhBinhLuan : dataSnapshotNodeHinhAnhBinhLuan.getChildren()) {
                                hinhAnhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                            }
                            binhLuanModel.setHinhanhBinhLuanList(hinhAnhBinhLuanList);
                            binhLuanModels.add(binhLuanModel);
                        } catch (Exception e) {

                        }
                    }

                    quanAnModel.setBinhLuanModelList(binhLuanModels);

                    // Lay chi nhanh quan an
                    DataSnapshot snapshotChiNhanh = dataSnapshot.child("chinhanhquanans").child(quanAnModel.getMaquanan());
                    List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();
                    for (DataSnapshot valueChiNhanhQuanAn : snapshotChiNhanh.getChildren()) {
                        ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);
                        Location viTriQuanAn = new Location("");
                        viTriQuanAn.setLatitude(chiNhanhQuanAnModel.getLatitude());
                        viTriQuanAn.setLongitude(chiNhanhQuanAnModel.getLongitude());

                        double khoangCach = viTriHienTai.distanceTo(viTriQuanAn) / 500;

                        chiNhanhQuanAnModel.setKhoangcach(khoangCach);
                        chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
                    }


                    quanAnModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);
                    quanAnModelList.add(quanAnModel);

                }
                for (int i = 0; i < quanAnModelList.size(); i++) {
                    for (int j = i + 1; j < quanAnModelList.size(); j++) {
                        QuanAnModel qi = quanAnModelList.get(i);
                        QuanAnModel qj = quanAnModelList.get(j);

                        double tongDiemBinhLuan1 = 0;
                        double tongDiemBinhLuan2 = 0;
                        double tongSoBinhLuan1 = qi.getBinhLuanModelList().size();
                        double tongSoBinhLuan2 = qj.getBinhLuanModelList().size();
                        if (tongSoBinhLuan1 + 1 == 1) {
                            tongSoBinhLuan1 = 1000;
                        }
                        if (tongSoBinhLuan2 + 1 == 1) {
                            tongSoBinhLuan1 = 1000;
                        }
                        if (qi.getBinhLuanModelList().size() > 0) {
                            for (BinhLuanModel binhLuanModel : qi.getBinhLuanModelList()) {
                                tongDiemBinhLuan1 += binhLuanModel.getChamdiem();
                            }
                        }
                        if (qj.getBinhLuanModelList().size() > 0) {
                            for (BinhLuanModel binhLuanModel : qj.getBinhLuanModelList()) {
                                tongDiemBinhLuan2 += binhLuanModel.getChamdiem();
                            }
                        }
                        if ((tongDiemBinhLuan1 / (tongSoBinhLuan1 + tongDiemBinhLuan1)) <= (tongDiemBinhLuan2 / (tongSoBinhLuan2 + tongDiemBinhLuan2))) {
                            Log.d("sosanhdiem", qi.getTenquanan() + " -- " + tongDiemBinhLuan1 + " " + (qi.getBinhLuanModelList().size() + 1) + "\n" + qj.getTenquanan() + " -- " + tongDiemBinhLuan2 + " " + (qj.getBinhLuanModelList().size() + 1));


                            Collections.swap(quanAnModelList, i, j);
                        }
                    }
                }
                for (int i = 0; i < quanAnModelList.size(); i++) {
                    Log.d("quanan", quanAnModelList.get(i).getBinhLuanModelList().size() + "");
                    anGiInterface.getDanhSachQuanAnAnGi(quanAnModelList.get(i));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }
}
