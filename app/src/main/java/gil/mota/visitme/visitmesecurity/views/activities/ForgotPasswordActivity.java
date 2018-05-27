package gil.mota.visitme.visitmesecurity.views.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Observable;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.ActivityForgotPasswordBinding;
import gil.mota.visitme.visitmesecurity.databinding.ActivityLoginBinding;
import gil.mota.visitme.visitmesecurity.viewModels.ForgotPasswordViewModel;
import gil.mota.visitme.visitmesecurity.viewModels.LoginViewModel;

public class ForgotPasswordActivity extends BindeableActivity{

    public ForgotPasswordViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
    }

    @Override public void initDataBinding()
    {
        ActivityForgotPasswordBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        viewModel = new ForgotPasswordViewModel(this);
        binding.setViewModel(viewModel);
    }


    @Override public void update(Observable observable, Object o)
    {
        if(observable instanceof ForgotPasswordViewModel)
        {
            ForgotPasswordViewModel viewModel = (ForgotPasswordViewModel) observable;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ChangePasswordActivity.CLOSE)
            finishSuccessful();
    }

    private void finishSuccessful() {
        setResult(ChangePasswordActivity.CLOSE);
        finish();
    }
}
