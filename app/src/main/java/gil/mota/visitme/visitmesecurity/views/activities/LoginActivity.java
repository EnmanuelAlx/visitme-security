package gil.mota.visitme.visitmesecurity.views.activities;

import android.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import java.util.Observable;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.ActivityLoginBinding;
import gil.mota.visitme.visitmesecurity.viewModels.LoginViewModel;

public class LoginActivity extends BindeableActivity implements LoginViewModel.Contract {
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setupObserver(loginViewModel);
    }

    @Override public void initDataBinding()
    {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = new LoginViewModel(this, this);
        binding.setViewModel(loginViewModel);
    }


    @Override public void update(Observable observable, Object o)
    {
        if(observable instanceof LoginViewModel)
        {
            LoginViewModel viewModel = (LoginViewModel) observable;
        }
    }

    @Override
    public void setLoading(boolean loading) {
        binding.loader.setVisibility(loading ? View.VISIBLE : View.GONE);
    }
}
