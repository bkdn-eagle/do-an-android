package com.example.pc.doantotnghiep.Controller;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.pc.doantotnghiep.Adapters.AdapterRecyclerAnGi;
import com.example.pc.doantotnghiep.Adapters.AdapterRecyclerOdau;
import com.example.pc.doantotnghiep.Controller.Interfaces.AnGiInterface;
import com.example.pc.doantotnghiep.Controller.Interfaces.OdauInterface;
import com.example.pc.doantotnghiep.Model.QuanAnModel;
import com.example.pc.doantotnghiep.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 21/03/2018.
 */

public class AnGiController {
    Context context;
    QuanAnModel quanAnModel;
    AdapterRecyclerAnGi adapterRecyclerAnGi;


    public AnGiController(Context context) {
        this.context = context;
        quanAnModel = new QuanAnModel();

    }

    public void getDanhSachQuanAnAnGiController(RecyclerView recyclerOdau, final ProgressBar progressBar, final Location vitrihientai) {

        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerOdau.setLayoutManager(layoutManager);
        adapterRecyclerAnGi = new AdapterRecyclerAnGi(context, quanAnModelList, R.layout.custom_layout_recyclerview_odau);
        recyclerOdau.setAdapter(adapterRecyclerAnGi);


        final AnGiInterface anGiInterface = new AnGiInterface() {
            @Override
            public void getDanhSachQuanAnAnGi(final QuanAnModel quanAnModel) {

                quanAnModelList.add(quanAnModel);
                adapterRecyclerAnGi.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }
        };

        quanAnModel.getDanhSachQuanAnAnGi(anGiInterface, vitrihientai);
    }
}
