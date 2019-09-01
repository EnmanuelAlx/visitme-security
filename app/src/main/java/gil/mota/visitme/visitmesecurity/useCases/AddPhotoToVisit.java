package gil.mota.visitme.visitmesecurity.useCases;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import gil.mota.visitme.visitmesecurity.managers.RequestManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddPhotoToVisit extends UseCase implements Observer<JSONObject> {
    private String visit;
    private HashMap<String, String> photos;
    private HashMap<String, String> params;

    private boolean requestOrGive; // true for request false for give


    public AddPhotoToVisit(UseCase.Result result) {
        super(result);
        photos = new HashMap<>();
        params = new HashMap<>();

    }

    @Override
    public void run() {
        try {
            RequestManager.getInstance().SaveImageToGuest(visit, photos, params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this);
        } catch (JSONException e) {
            resultSetter.onError("Error Inesperado");
        }
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

    public void setParams(String visit) {
        params.put("visit", visit);
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public void setPhotos(ArrayList<File> photos) {
        this.photos.clear();
        int i = 0;
        for (File photo : photos) {
            this.photos.put("" + i, photo.getAbsolutePath());
            i++;
        }
    }

    public interface Result extends UseCase.Result {
        void onResidentDeviceNotFound();
    }

    public void reset() {
        requestOrGive = true;
    }
    public void changeToGive() {
        requestOrGive = false;
    }

}
