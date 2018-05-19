package com.example.pc.doantotnghiep.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pc.doantotnghiep.Adapters.AdapterViewPageTrangChu;
import com.example.pc.doantotnghiep.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by PC on 20/03/2018.
 */

public class TrangChuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    ViewPager viewPagerTrangChu;
    RadioButton rdOdau, rdAngi;
    RadioGroup groupOdauAngi;
    ImageView btnDangXuat, imgThemQuanAn;
    FirebaseAuth firebaseAuth;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);
        rdOdau = findViewById(R.id.rd_odau);
        rdAngi = findViewById(R.id.rd_angi);
        groupOdauAngi = findViewById(R.id.group_odau_angi);

        viewPagerTrangChu = findViewById(R.id.viewpager_trangchu);
        AdapterViewPageTrangChu adapterViewPageTrangChu = new AdapterViewPageTrangChu(getSupportFragmentManager());
        viewPagerTrangChu.setAdapter(adapterViewPageTrangChu);
        viewPagerTrangChu.addOnPageChangeListener(this);
        groupOdauAngi.setOnCheckedChangeListener(this);
        imgThemQuanAn = findViewById(R.id.imgThemQuanAn);
        imgThemQuanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iThemQuanAn = new Intent(TrangChuActivity.this, ThemQuanAnActivity.class);
                startActivity(iThemQuanAn);
            }
        });
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth = FirebaseAuth.getInstance();
                showAlertDialog();
            }
        });
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có muốn đăng xuất không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ứ chịu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(TrangChuActivity.this, "Không thoát được", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseAuth.signOut();
                Intent iDangNhap = new Intent(TrangChuActivity.this, DangNhapActivity.class);
                startActivity(iDangNhap);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                rdOdau.setChecked(true);
                break;

            case 1:
                rdAngi.setChecked(true);
                break;

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rd_angi:
                viewPagerTrangChu.setCurrentItem(1);
                break;

            case R.id.rd_odau:
                viewPagerTrangChu.setCurrentItem(0);
                break;
        }
    }
}


