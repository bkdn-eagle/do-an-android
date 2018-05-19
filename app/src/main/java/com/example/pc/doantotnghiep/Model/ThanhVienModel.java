package com.example.pc.doantotnghiep.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by PC on 21/03/2018.
 */

public class ThanhVienModel implements Parcelable {
    String hoten;
    String hinhanh;
    String hinhanhapp;


    protected ThanhVienModel(Parcel in) {
        hoten = in.readString();
        hinhanh = in.readString();
        mathanhvien = in.readString();
        hinhanhapp = in.readString();
    }

    public static final Creator<ThanhVienModel> CREATOR = new Creator<ThanhVienModel>() {
        @Override
        public ThanhVienModel createFromParcel(Parcel in) {
            return new ThanhVienModel(in);
        }

        @Override
        public ThanhVienModel[] newArray(int size) {
            return new ThanhVienModel[size];
        }
    };

    public String getMathanhvien() {
        return mathanhvien;
    }

    public void setMathanhvien(String mathanhvien) {
        this.mathanhvien = mathanhvien;
    }

    String mathanhvien;
    private DatabaseReference databaseNoteThanhVien;

    public ThanhVienModel() {
        databaseNoteThanhVien = FirebaseDatabase.getInstance().getReference().child("thanhviens");
    }
    public String getHinhanhapp() {
        return hinhanhapp;
    }

    public void setHinhanhapp(String hinhanhapp) {
        this.hinhanhapp = hinhanhapp;
    }
    public ThanhVienModel(String hoten, String hinhanh, String hinhanhapp) {
        this.hoten = hoten;
        this.hinhanh = hinhanh;
        this.hinhanhapp = hinhanhapp;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public void ThemThongTinThanhVien(ThanhVienModel thanhVienModel, String uid) {

        databaseNoteThanhVien.child(uid).setValue(thanhVienModel);
        Log.d("suaDoiThanhCong",thanhVienModel.getHoten());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hoten);
        parcel.writeString(hinhanh);
        parcel.writeString(mathanhvien);
        parcel.writeString(hinhanhapp);
    }
}
