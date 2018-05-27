package gil.mota.visitme.visitmesecurity.views.activities;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import gil.mota.visitme.visitmesecurity.MyApplication;
import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.managers.PermissionManager;

public class SplashActivity extends AppCompatActivity implements PermissionManager.PermisionResult {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupPermission();
    }

    private void goToLoginActivity() {
        MyApplication.getInstance().goToLoginActivity(this);
    }

    private void setupPermission()
    {
        PermissionManager.getInstance()
                .requestPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE,5879, this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.getInstance().onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
    @Override
    public void onDenied() {
        finish();
    }

    @Override
    public void onGranted()
    {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToLoginActivity();
            }
        },2000);
    }
}
