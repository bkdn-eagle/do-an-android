package com.example.pc.doantotnghiep.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.doantotnghiep.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

/**
 * Created by PC on 20/03/2018.
 */

public class DangNhapActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FirebaseAuth.AuthStateListener {

    Button btnDangNhapGoogle, btnDangNhap,btnDangNhapFacebook;

    TextView txtDangKyMoi, txtQuenMatKhau;
    EditText edEmail, edPassword;

    GoogleApiClient apiClient;
    public static int REQUESTCODE_DANGNHAP_GOOGLE = 99;
    public static int KIEMTRA_PROVIDER_DANGNHAP = 0;
    FirebaseAuth firebaseAuth;

    CallbackManager mCallbackFacebook;
    LoginManager loginManager;
    List<String> permissionFacebook = Arrays.asList("email","public_profile");
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.layout_dangnhap);

        mCallbackFacebook = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
       // firebaseAuth.signOut();

        btnDangNhapGoogle = (Button) findViewById(R.id.btnDangNhapGoogle);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        btnDangNhapFacebook = findViewById(R.id.btnDangNhapFacebook);

        txtDangKyMoi = (TextView) findViewById(R.id.txtDangKyMoi);
        txtQuenMatKhau = (TextView) findViewById(R.id.txtQuenMatKhau);

        edEmail = (EditText) findViewById(R.id.edEmailDN);
        edPassword = (EditText) findViewById(R.id.edPasswordDN);

        btnDangNhapGoogle.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);
        txtDangKyMoi.setOnClickListener(this);
        txtQuenMatKhau.setOnClickListener(this);
        btnDangNhapFacebook.setOnClickListener(this);
        sharedPreferences = getSharedPreferences("luunguoidung", MODE_PRIVATE);
        TaoClientDangNhapGoogle();
    }


    //Khởi tạo client cho đăng nhập google
    private void TaoClientDangNhapGoogle() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();

    }

    //end khởi tạo client cho đăng nhập google

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    //Mở form đăng nhập bằng google
    private void DangNhapGoogle(GoogleApiClient apiClient) {
        KIEMTRA_PROVIDER_DANGNHAP = 1;
        Intent iDNGoogle = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(iDNGoogle, REQUESTCODE_DANGNHAP_GOOGLE);
    }
    private void DangNhapFacebook(){
        loginManager.logInWithReadPermissions(this,permissionFacebook);
        loginManager.registerCallback(mCallbackFacebook, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                KIEMTRA_PROVIDER_DANGNHAP = 2;
                String tokenID = loginResult.getAccessToken().getToken();
                ChungThucDangNhapFireBase(tokenID);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    //end mở form đăng nhập bằng google

    //Lấy stokenID đã đăng nhập bằng google để đăng nhập trên Firebase
    private void ChungThucDangNhapFireBase(String tokenID) {
        if(KIEMTRA_PROVIDER_DANGNHAP == 1){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID,null);
            firebaseAuth.signInWithCredential(authCredential);
        }else if(KIEMTRA_PROVIDER_DANGNHAP == 2){
            AuthCredential authCredential = FacebookAuthProvider.getCredential(tokenID);
            firebaseAuth.signInWithCredential(authCredential);

        }
    }
    //end Lấy stokenID đã đăng nhập bằng google để đăng nhập trên Firebase

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_DANGNHAP_GOOGLE) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount account = signInResult.getSignInAccount();
                String tokenID = account.getIdToken();
                ChungThucDangNhapFireBase(tokenID);
            }
        }else{
            mCallbackFacebook.onActivityResult(requestCode,resultCode,data);
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    //Lắng nghe sự kiện user click vào button đăng nhập google, facebook và email account
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDangNhapGoogle:
                DangNhapGoogle(apiClient);
                break;

            case R.id.btnDangNhap:
                DangNhap();
                break;

            case R.id.txtDangKyMoi:
                Intent iDangKy = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(iDangKy);
                break;

            case R.id.txtQuenMatKhau:
                Intent iKhoiPhucMatKhau = new Intent(DangNhapActivity.this, KhoiPhucMatKhauActivity.class);
                startActivity(iKhoiPhucMatKhau);
                break;
            case R.id.btnDangNhapFacebook:
                DangNhapFacebook();
                break;

        }
    }
    //end

    private void DangNhap() {
        String email = edEmail.getText().toString();
        String matkhau = edPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email, matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(DangNhapActivity.this, getString(R.string.thongbaodangnhapthatbai), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Sự kiện kiểm tra xem người dùng đã nhập thành công hay đăng xuất
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("mauser", user.getUid());
            editor.commit();

            Intent iTrangChu = new Intent(this, TrangChuActivity.class);
            startActivity(iTrangChu);
        } else {

        }
    }
    //End
}