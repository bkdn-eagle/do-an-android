package com.example.pc.doantotnghiep.View.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.pc.doantotnghiep.Controller.AnGiController;
import com.example.pc.doantotnghiep.Controller.OdauController;
import com.example.pc.doantotnghiep.R;
import com.example.pc.doantotnghiep.View.GameAndFunActivity;
import com.example.pc.doantotnghiep.View.ThanhVienActivity;
import com.example.pc.doantotnghiep.View.WebSiteActivity;

/**
 * Created by PC on 12/03/2018.
 */

public class AnGiFragment extends Fragment {
    AnGiController anGiController;
    RecyclerView recyclerOdau;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    Button btnThanhVien, btnWebSite, btnPhanHoi, btnGameandFun;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_odau, container, false);
        recyclerOdau = view.findViewById(R.id.recyclerOdau);
        progressBar = view.findViewById(R.id.progressBarODau);

        sharedPreferences = getContext().getSharedPreferences("ToaDoHienTai", Context.MODE_PRIVATE);
        Location viTriHienTai = new Location("");
        viTriHienTai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        viTriHienTai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));

        btnThanhVien = view.findViewById(R.id.btnThanhVien);
        btnWebSite = view.findViewById(R.id.btnWebSite);
        btnPhanHoi = view.findViewById(R.id.btnPhanHoi);
        btnGameandFun = view.findViewById(R.id.btnGameandFun);

        anGiController = new AnGiController(getContext());
        anGiController.getDanhSachQuanAnAnGiController(recyclerOdau, progressBar, viTriHienTai);

        btnThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iThanhVien = new Intent(getContext(), ThanhVienActivity.class);
                startActivity(iThanhVien);
            }
        });
        btnWebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iWebSite = new Intent(getContext(), WebSiteActivity.class);
                startActivity(iWebSite);
            }
        });
        btnPhanHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iChiaSe = new Intent(Intent.ACTION_SEND);
                iChiaSe.setType("text/plain");
                String shareSub = "Quán này ngon lắm";
                String shareBody = "";
                iChiaSe.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                iChiaSe.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(iChiaSe);
            }
        });
        btnGameandFun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGameAndFun = new Intent(getContext(), GameAndFunActivity.class);
                startActivity(iGameAndFun);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
