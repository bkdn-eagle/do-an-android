package com.example.pc.doantotnghiep.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.pc.doantotnghiep.Adapters.AdapterRecyclerOdau;
import com.example.pc.doantotnghiep.Controller.Interfaces.OdauInterface;
import com.example.pc.doantotnghiep.Model.QuanAnModel;
import com.example.pc.doantotnghiep.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 21/03/2018.
 */

public class OdauController {
    Context context;
    QuanAnModel quanAnModel;
    AdapterRecyclerOdau adapterRecyclerOdau;


    public OdauController(Context context) {
        this.context = context;
        quanAnModel = new QuanAnModel();

    }

    public void getDanhSachQuanAnController(RecyclerView recyclerOdau, final ProgressBar progressBar, final Location vitrihientai) {

        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerOdau.setLayoutManager(layoutManager);
        adapterRecyclerOdau = new AdapterRecyclerOdau(context, quanAnModelList, R.layout.custom_layout_recyclerview_odau);
        recyclerOdau.setAdapter(adapterRecyclerOdau);


        final OdauInterface odauInterface = new OdauInterface() {
            @Override
            public void getDanhSachQuanAnModel(final QuanAnModel quanAnModel) {

                quanAnModelList.add(quanAnModel);
                adapterRecyclerOdau.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }
        };

        quanAnModel.getDanhSachQuanAn(odauInterface, vitrihientai);
    }
}
