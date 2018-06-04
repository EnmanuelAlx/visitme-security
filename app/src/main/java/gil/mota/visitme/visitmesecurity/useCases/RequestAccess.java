package gil.mota.visitme.visitmesecurity.useCases;

import android.databinding.ObservableField;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import gil.mota.visitme.visitmesecurity.managers.RequestManager;
import gil.mota.visitme.visitmesecurity.managers.UserManager;
import gil.mota.visitme.visitmesecurity.models.Community;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RequestAccess extends UseCase implements Observer<JSONObject> {

    private HashMap<String, String> params;
    private HashMap<String, String> photos;

    public RequestAccess(Result result) {
        super(result);
        params = new HashMap<>();
        photos = new HashMap<>();
    }

    @Override
    public void run() {
        Community def = null;
        try {
            def = UserManager.getInstance().getDefaultCommunity();
            RequestManager.getInstance().requestAccess(params, photos, def.get_id())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this);
        } catch (JSONException e) {
            onError(new Throwable("Ocurrio un error inesperado, intente mas tarde"));
        }
    }

    public void setParams(String name, String identification, String residentIdentification, String residentDetail) {
        params.put("name", name);
        params.put("identification", identification);
        params.put("residentIdentification", residentIdentification);
        params.put("reference", residentDetail);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(JSONObject jsonObject) {
        resultSetter.onSuccess();
    }

    @Override
    public void onError(Throwable e) {
        resultSetter.onError(e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    public void setPhotos(ArrayList<File> photos) {
        this.photos.clear();
        int i = 0;
        for (File photo : photos) {
            this.photos.put("" + i, photo.getAbsolutePath());
            i++;
        }
    }
}
