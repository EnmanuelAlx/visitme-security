package gil.mota.visitme.visitmesecurity.viewModels;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.models.User;
import gil.mota.visitme.visitmesecurity.useCases.EditProfile;
import gil.mota.visitme.visitmesecurity.useCases.Register;
import gil.mota.visitme.visitmesecurity.useCases.UseCase;
import gil.mota.visitme.visitmesecurity.utils.FilePath;
import gil.mota.visitme.visitmesecurity.utils.Pnotify;
import gil.mota.visitme.visitmesecurity.views.activities.MainActivity;

/**
 * Created by mota on 12/4/2018.
 */

public class RegisterViewModel extends Observable implements UseCase.Result {


    public ObservableField<String> cedula;
    public ObservableField<String> name;
    public ObservableField<String> password;
    public ObservableField<String> email;
    public ObservableField<String> confirmPassword;
    public ObservableField<String> cellPhone;
    public ObservableField<String> homePhone;

    public Contract contract;
    public ObservableField<Boolean> edit;
    private Uri imageSelected;
    private Context context;
    private Register register;
    private EditProfile editProfile;
    private User user;

    public RegisterViewModel(@NonNull Context context, Contract contract) {
        this.context = context;
        this.cedula = new ObservableField<>("");
        this.password = new ObservableField<>("");
        this.confirmPassword = new ObservableField<>("");
        this.homePhone = new ObservableField<>("");
        this.cellPhone = new ObservableField<>("");
        this.name = new ObservableField<>("");
        this.email = new ObservableField<>("");

        this.edit = new ObservableField<>(false);
        this.contract = contract;
        register = new Register(this, context);

    }

    public RegisterViewModel(@NonNull Context context, Contract contract, User user) {
        Log.i("EDIT USER", "USer u:" + user.toString());
        this.context = context;
        this.cedula = new ObservableField<>(user.getIdentification());
        this.password = new ObservableField<>("");
        this.confirmPassword = new ObservableField<>("");
        this.homePhone = new ObservableField<>(user.getHomePhone());
        this.cellPhone = new ObservableField<>(user.getCellPhone());
        this.name = new ObservableField<>(user.getName());
        this.email = new ObservableField<>(user.getEmail());
        this.user = user;
        this.edit = new ObservableField<>(true);
        this.contract = contract;
        contract.changeImage(user.getImage());
        editProfile = new EditProfile(onEditResult);

    }


    public void register(View view) {
        if (imageSelected == null) {
            Pnotify.makeText(context, "No has seleccionado una imagen de Perfil", Toast.LENGTH_SHORT, Pnotify.WARNING).show();
            return;
        }

        if (!password.get().equals(confirmPassword.get())) {
            Pnotify.makeText(context, "Las contraseñas no Coinciden", Toast.LENGTH_SHORT, Pnotify.WARNING).show();
            return;
        }

        if (cellPhone.get().isEmpty() && homePhone.get().isEmpty()) {
            Pnotify.makeText(context, "Deber proveer por lo menos 1 numero de telefono", Toast.LENGTH_SHORT, Pnotify.WARNING).show();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            register.setParams(cedula.get(), name.get(),
                    email.get(), password.get(),
                    cellPhone.get(),
                    homePhone.get(),
                    FilePath.getPath(context, imageSelected));
            register.run();
        }

    }

    public void editProfile(View view) {
        /*if (imageSelected == null) {
            Pnotify.makeText(context, "No has seleccionado una imagen de Perfil", Toast.LENGTH_SHORT, Pnotify.WARNING).show();
            return;
        }*/

        if (!password.get().equals(confirmPassword.get())) {
            Pnotify.makeText(context, "Las contraseñas no Coinciden", Toast.LENGTH_SHORT, Pnotify.WARNING).show();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            editProfile.setParams(
                    cedula.get().isEmpty() ? null : cedula.get(),
                    name.get().isEmpty() ? null : name.get(),
                    email.get().isEmpty() ? null : email.get(),
                    password.get().isEmpty() ? null : password.get(),
                    cellPhone.get().isEmpty() ? null : cellPhone.get(),
                    homePhone.get().isEmpty() ? null : homePhone.get(),
                    FilePath.getPath(context, imageSelected));
        }
        editProfile.run();


    }

    public void selectImage(View view) {
        contract.select();
    }

    public void changeImage(Uri image) {
        this.imageSelected = image;
    }

    @Override
    public void onError(String error) {
        Pnotify.makeText(context, error, Toast.LENGTH_SHORT, Pnotify.ERROR).show();
    }

    @Override
    public void onSuccess() {
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }


    public interface Contract {
        void changeImage(String image);

        void select();
    }

    private final UseCase.Result onEditResult = new UseCase.Result() {
        @Override
        public void onError(String error) {
            Pnotify.makeText(context,error,Toast.LENGTH_SHORT, Pnotify.ERROR).show();
        }

        @Override
        public void onSuccess() {
            Pnotify.makeText(context,"Actualizacion Satisfactoria",Toast.LENGTH_SHORT, Pnotify.INFO).show();
        }
    };

}
