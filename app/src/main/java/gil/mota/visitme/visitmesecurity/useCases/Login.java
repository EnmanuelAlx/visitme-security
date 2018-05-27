package gil.mota.visitme.visitmesecurity.useCases;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

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

public class Login extends Auth {
    private JSONObject params;
    public Login(Result result, Context context) {
        super(result,context);
        params = new JSONObject();

        if (!UserManager.getInstance().getAuth().equals(""))
            resultSetter.onSuccess();
    }

    public void setParams(String username, String password) throws JSONException {
        params.put("email", username);
        params.put("password", password);
    }

    @Override
    public void run() {
        RequestManager.getInstance()
                       .login(params)
                       .subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(this);
    }

}
