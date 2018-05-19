package com.example.pc.doantotnghiep.Model;

import android.content.Context;
import android.widget.Toast;

import com.example.pc.doantotnghiep.Controller.Interfaces.ChiTietQuanAnInterface;
import com.example.pc.doantotnghiep.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WifiQuanAnModel {
    String ten;
    String matkhau;
    String ngaydang;
    private DatabaseReference nodeWifiQuanAn;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }

    public WifiQuanAnModel() {

    }


    public void LayDanhSachWifiQuanAn(String maquanan, final ChiTietQuanAnInterface chiTietQuanAnInterface) {
        nodeWifiQuanAn = FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maquanan);
        nodeWifiQuanAn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot valueWifiQuanAn : dataSnapshot.getChildren()){
                    WifiQuanAnModel wifiQuanAnModel = valueWifiQuanAn.getValue(WifiQuanAnModel.class);
                    chiTietQuanAnInterface.HienThiDanhSachWifi(wifiQuanAnModel);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void ThemWifiQuanAn(final Context context, WifiQuanAnModel wifiQuanAnModel, String maquanan){
        DatabaseReference dataNodeWifiQuanAn = FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maquanan);
        dataNodeWifiQuanAn.push().setValue(wifiQuanAnModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(context,context.getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
