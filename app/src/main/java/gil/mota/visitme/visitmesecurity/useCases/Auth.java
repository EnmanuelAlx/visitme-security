package gil.mota.visitme.visitmesecurity.useCases;

import android.content.Context;

import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

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

public abstract class Auth extends UseCase implements Observer<JSONObject> {

    private Context context;
    public Auth(Result result, Context context) {
        super(result);
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(JSONObject obj) {
        try {
            onSuccess(obj);
            runAddDevice();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void runAddDevice() {
        new AddDevice(null).run();
    }


    @Override
    public void onError(Throwable e) {
        resultSetter.onError(e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    @Override
    abstract public void run();

    public void onSuccess(JSONObject obj) throws JSONException {
        User u = Functions.parse(obj.getJSONObject("user"),User.class);
        String deviceId = OneSignal.getPermissionSubscriptionState()
                                   .getSubscriptionStatus()
                                   .getUserId();
        UserManager.getInstance().saveUserCredentials(u, obj.getString("token"), deviceId);
        resultSetter.onSuccess();
    }
}
