package gil.mota.visitme.visitmesecurity.viewModels;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.util.Observable;

import gil.mota.visitme.visitmesecurity.useCases.Login;
import gil.mota.visitme.visitmesecurity.useCases.UseCase;
import gil.mota.visitme.visitmesecurity.utils.Pnotify;
import gil.mota.visitme.visitmesecurity.views.activities.ForgotPasswordActivity;
import gil.mota.visitme.visitmesecurity.views.activities.MainActivity;
import gil.mota.visitme.visitmesecurity.views.activities.RegisterActivity;


/**
 * Created by Slaush on 22/05/2017.
 */

public class LoginViewModel extends Observable implements Login.Result {
    public ObservableField<String> username;
    public ObservableField<String> password;

    private Context context;
    private Login login;
    private Contract contract;

    public LoginViewModel(@NonNull Context context, Contract contract) {
        this.context = context;
        this.contract = contract;
        this.username = new ObservableField<>("");
        this.password = new ObservableField<>("");
        login = new Login(this, context);

    }

    public void onClickLogin(View view) {
        try {
            login.setParams(username.get(), password.get());
            login.run();
            contract.setLoading(true);
        } catch (JSONException e) {
            onError("Error Inesperado");
        }
    }

    public void register(View view) {
        Intent i = new Intent(context, RegisterActivity.class);
        context.startActivity(i);
    }

    public void forgotPassword(View view) {
        Intent i = new Intent(context, ForgotPasswordActivity.class);
        context.startActivity(i);
    }

    @Override
    public void onError(String errorStr) {
        contract.setLoading(false);
        Pnotify.makeText(context, errorStr, Toast.LENGTH_SHORT, Pnotify.ERROR).show();
    }

    @Override
    public void onSuccess() {
        contract.setLoading(false);
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }

    public interface Contract {
        void setLoading(boolean loading);
    }
}
