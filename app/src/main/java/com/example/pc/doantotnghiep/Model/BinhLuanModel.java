package com.example.pc.doantotnghiep.Model;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;

/**
 * Created by PC on 21/03/2018.
 */

public class BinhLuanModel implements Parcelable {
    long chamdiem;
    long luotthich;
    ThanhVienModel thanhVienModel;
    String noidung;
    String tieude;
    String mauser;
    List<String> hinhanhBinhLuanList;
    String mabinhluan;

    private DatabaseReference databaseNoteBinhLuan;

    protected BinhLuanModel(Parcel in) {
        chamdiem = in.readLong();
        luotthich = in.readLong();
        noidung = in.readString();
        tieude = in.readString();
        mauser = in.readString();
        hinhanhBinhLuanList = in.createStringArrayList();
        mabinhluan = in.readString();
        thanhVienModel = in.readParcelable(ThanhVienModel.class.getClassLoader());
    }

    public static final Creator<BinhLuanModel> CREATOR = new Creator<BinhLuanModel>() {
        @Override
        public BinhLuanModel createFromParcel(Parcel in) {
            return new BinhLuanModel(in);
        }

        @Override
        public BinhLuanModel[] newArray(int size) {
            return new BinhLuanModel[size];
        }
    };

    public String getMabinhluan() {
        return mabinhluan;
    }

    public void setMabinhluan(String mabinhluan) {
        this.mabinhluan = mabinhluan;
    }


    public List<String> getHinhanhBinhLuanList() {
        return hinhanhBinhLuanList;
    }

    public void setHinhanhBinhLuanList(List<String> hinhanhList) {
        this.hinhanhBinhLuanList = hinhanhList;
    }


    public long getChamdiem() {
        return chamdiem;
    }

    public BinhLuanModel() {

    }

    public void setChamdiem(long chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public ThanhVienModel getThanhVienModel() {
        return thanhVienModel;
    }

    public void setThanhVienModel(ThanhVienModel thanhVienModel) {
        this.thanhVienModel = thanhVienModel;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }


    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(chamdiem);
        parcel.writeLong(luotthich);
        parcel.writeString(noidung);
        parcel.writeString(tieude);
        parcel.writeString(mauser);
        parcel.writeStringList(hinhanhBinhLuanList);
        parcel.writeString(mabinhluan);
        parcel.writeParcelable(thanhVienModel, i);
    }

    public void ThemBinhLuan(String maquanan, BinhLuanModel binhLuanModel, final List<String> listHinh) {

        databaseNoteBinhLuan = FirebaseDatabase.getInstance().getReference().child("binhluans");
        String mabinhluan = databaseNoteBinhLuan.child(maquanan).push().getKey();
        databaseNoteBinhLuan.child(maquanan).child(mabinhluan).setValue(binhLuanModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (listHinh.size() > 0) {
                        for (String value : listHinh) {
                            Uri uri = Uri.fromFile(new File(value));
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh/" + uri.getLastPathSegment());
                            storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                }
                            });
                        }
                    }

                }
            }
        });
        if (listHinh.size() > 0) {
            for (String value : listHinh) {
                Uri uri = Uri.fromFile(new File(value));
                FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(mabinhluan).push().setValue(uri.getLastPathSegment());
            }
        }

    }
}
