package com.example.pc.doantotnghiep.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pc.doantotnghiep.Controller.DangKyController;
import com.example.pc.doantotnghiep.Model.ThanhVienModel;
import com.example.pc.doantotnghiep.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by PC on 20/03/2018.
 */

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnDangKy;
    FirebaseAuth firebaseAuth;
    EditText edEmailDK,edPasswordDK,edNhapLaiPasswordDK;
    ProgressDialog progressDialog;
    DangKyController dangKyController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);

        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnDangKy= findViewById(R.id.btnDangKy);
        btnDangKy.setOnClickListener(this);
        edEmailDK = (EditText) findViewById(R.id.edEmailDK);
        edPasswordDK = (EditText) findViewById(R.id.edPasswordDK);
        edNhapLaiPasswordDK = (EditText) findViewById(R.id.edNhapLaiPasswordDK);
    }

    @Override
    public void onClick(View view) {
        progressDialog.setMessage(getString(R.string.dangxuly));
        progressDialog.setIndeterminate(true);

        progressDialog.show();

        final String email = edEmailDK.getText().toString();
        String matkhau = edPasswordDK.getText().toString();
        String nhaplaimatkhau = edNhapLaiPasswordDK.getText().toString();
        String thongbaoloi = getString(R.string.thongbaoloidangky);

        if(email.trim().length() == 0 ){
            thongbaoloi += getString(R.string.email);
            Toast.makeText(this,thongbaoloi,Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else if(matkhau.trim().length() == 0){
            thongbaoloi += getString(R.string.matkhau);
            Toast.makeText(this,thongbaoloi,Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else if(!nhaplaimatkhau.equals(matkhau)){
            Toast.makeText(this,getString(R.string.thongbaonhaplaimatkhau),Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else{
            firebaseAuth.createUserWithEmailAndPassword(email,matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        ThanhVienModel thanhVienModel = new ThanhVienModel();
                        thanhVienModel.setHoten(email);
                        thanhVienModel.setHinhanhapp("user.png");
                        String uid = task.getResult().getUser().getUid();
                        dangKyController = new DangKyController();
                        dangKyController.ThemThongTinThanhVienController(thanhVienModel,uid);
                        Toast.makeText(DangKyActivity.this,getString(R.string.thongbaodangkythanhcong),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    if(!task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(DangKyActivity.this,"Mail không đúng hoặc đã được đăng kí", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
