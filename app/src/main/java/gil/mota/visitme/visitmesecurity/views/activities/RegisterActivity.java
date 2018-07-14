package gil.mota.visitme.visitmesecurity.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.io.IOException;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.ActivityRegisterBinding;
import gil.mota.visitme.visitmesecurity.managers.UserManager;
import gil.mota.visitme.visitmesecurity.models.User;
import gil.mota.visitme.visitmesecurity.viewModels.RegisterViewModel;

public class RegisterActivity extends BindeableActivity implements RegisterViewModel.Contract {
    private RegisterViewModel viewModel;
    private ActivityRegisterBinding binding;
    private String image;
    private boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edit = this.getIntent().getBooleanExtra("edit", false);
        initDataBinding();
        setupObserver(viewModel);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof RegisterViewModel) {
            RegisterViewModel viewModel = (RegisterViewModel) observable;
        }
    }

    @Override
    public void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        if (edit) {
            User user = null;
            try {
                user = UserManager.getInstance().getUser(this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            viewModel = new RegisterViewModel(this, this, user);

        } else {
            viewModel = new RegisterViewModel(this, this);
        }

        binding.setViewModel(viewModel);
    }

    @Override
    public void changeImage(String image) {
        Glide.with(this).load(image).placeholder(R.drawable.guy).dontAnimate()
                .error(R.drawable.guy).into(binding.profileImage);
    }

    @Override
    public void select() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una Imagen de Perfil");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, 222);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 222 && resultCode == Activity.RESULT_OK) {
            Uri u = data.getData();
            if (u != null) {
                image = u.getPath();

                Glide.with(this).load(u).dontAnimate().into(binding.profileImage);
                viewModel.changeImage(u);

            }
        }
    }

}
