package gil.mota.visitme.visitmesecurity.useCases;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import gil.mota.visitme.visitmesecurity.managers.RequestManager;
import gil.mota.visitme.visitmesecurity.managers.UserManager;
import gil.mota.visitme.visitmesecurity.models.Community;
import gil.mota.visitme.visitmesecurity.utils.Functions;

/**
 * Created by mota on 17/4/2018.
 */

public class GetCommunities extends UseCase implements Observer<JSONObject> {

    public GetCommunities(Result result) {
        super(result);
    }

    @Override
    public void run() {
        RequestManager.getInstance().getCommunities().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(JSONObject jsonObject) {
        onSuccess(jsonObject);
    }

    private void onSuccess(JSONObject obj)
    {
        if (obj.has("communities"))
        {
            Log.i("COM","GET COM " + obj.toString());
            UserManager.getInstance().saveCommunities(obj);
            if (resultSetter != null)
                resultSetter.onSuccess();
        }
        else
        {
            if (resultSetter != null)
                resultSetter.onError("Error inesperado");
        }


    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
