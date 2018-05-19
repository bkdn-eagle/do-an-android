package com.example.pc.doantotnghiep.Controller;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.pc.doantotnghiep.Model.BinhLuanModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.List;

public class BinhLuanController {
    BinhLuanModel binhLuanModel;

    public BinhLuanController() {
        binhLuanModel = new BinhLuanModel();
    }

    public void ThemBinhLuan(String maquanan, BinhLuanModel binhLuanModel, final List<String> listHinh) {
        binhLuanModel.ThemBinhLuan(maquanan, binhLuanModel, listHinh);
    }
}
