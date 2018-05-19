package com.example.pc.doantotnghiep.View.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pc.doantotnghiep.Controller.OdauController;
import com.example.pc.doantotnghiep.R;
import com.example.pc.doantotnghiep.View.GameAndFunActivity;
import com.example.pc.doantotnghiep.View.WebSiteActivity;
import com.example.pc.doantotnghiep.View.ThanhVienActivity;

/**
 * Created by PC on 12/03/2018.
 */

public class OdauFragment extends Fragment {
    OdauController odauController;
    RecyclerView recyclerOdau;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    Button btnThanhVien, btnWebSite,btnPhanHoi, btnGameandFun;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_odau, container, false);
        recyclerOdau = view.findViewById(R.id.recyclerOdau);
        progressBar = view.findViewById(R.id.progressBarODau);
        btnThanhVien = view.findViewById(R.id.btnThanhVien);
        btnWebSite = view.findViewById(R.id.btnWebSite);
        btnPhanHoi=view.findViewById(R.id.btnPhanHoi);
        btnGameandFun=view.findViewById(R.id.btnGameandFun);

        sharedPreferences = getContext().getSharedPreferences("ToaDoHienTai", Context.MODE_PRIVATE);
        Location viTriHienTai = new Location("");
        viTriHienTai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        viTriHienTai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));

        odauController = new OdauController(getContext());
        odauController.getDanhSachQuanAnController(recyclerOdau, progressBar, viTriHienTai);

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
                String[] TO = {"truongdat4567@gmail.com"};

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));


                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(),
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
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
