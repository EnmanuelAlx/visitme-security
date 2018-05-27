package gil.mota.visitme.visitmesecurity.useCases;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import gil.mota.visitme.visitmesecurity.managers.RequestManager;
import gil.mota.visitme.visitmesecurity.managers.UserManager;
import gil.mota.visitme.visitmesecurity.models.User;
import gil.mota.visitme.visitmesecurity.utils.Functions;

/**
 * Created by mota on 14/4/2018.
 */

public class Register extends Auth {

    private HashMap<String, String> data;
    private String image;

    public Register(Result result, Context context) {
        super(result, context);
        data = new HashMap<>();

    }

    @Override
    public void run() {
        RequestManager.getInstance()
                .register(data, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    public void setParams(String cedula, String nombre, String email, String password, String cellPhone, String homePhone, String image) {
        if (cedula != null)
            data.put("identification", cedula);
        if (nombre != null)
            data.put("name", nombre);
        if (email != null)
            data.put("email", email);
        if (password != null)
            data.put("password", password);
        if (cellPhone != null)
            data.put("cellPhone", cellPhone);
        if (homePhone != null)
            data.put("homePhone", homePhone);
        this.image = image;
        Log.i("REGISTER", "SET PARAMS" + data.toString() + image);
    }


}
