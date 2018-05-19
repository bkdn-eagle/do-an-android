package com.example.pc.doantotnghiep.Controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.pc.doantotnghiep.Adapters.AdapterThucDon;
import com.example.pc.doantotnghiep.Controller.Interfaces.ThucDonInterface;
import com.example.pc.doantotnghiep.Model.ThucDonModel;

import java.util.List;

public class ThucDonController {
    ThucDonModel thucDonModel;

    public ThucDonController() {
        thucDonModel = new ThucDonModel();
    }

    public void getDanhSachThucDonQuanAnTheoMa(final Context context, String maquanan, final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ThucDonInterface thucDonInterface = new ThucDonInterface() {
            @Override
            public void getThucDonThanhCong(List<ThucDonModel> thucDonModelList) {
                AdapterThucDon adapterThucDon = new AdapterThucDon(context,thucDonModelList);
                recyclerView.setAdapter(adapterThucDon);
                adapterThucDon.notifyDataSetChanged();
            }
        };
        thucDonModel.getDanhSachThucDonQuanAnTheoMa(maquanan, thucDonInterface);
    }
}
