package gil.mota.visitme.visitmesecurity.useCases;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import gil.mota.visitme.visitmesecurity.managers.RequestManager;
import gil.mota.visitme.visitmesecurity.models.Alert;

/**
 * Created by mota on 18/4/2018.
 */

public class CreateAlert extends UseCase implements Observer<JSONObject> {

    private JSONObject params;

    public CreateAlert(Result result) {
        super(result);
        params = new JSONObject();
    }

    public void setParams(Alert alert) throws JSONException {
        params.put("community", alert.getCommunity().get_id());
        params.put("message",alert.getMessage());
        params.put("kind",alert.getType());
        params.put("description",alert.getDescription());
    }

    @Override
    public void run() {
        RequestManager
                .getInstance()
                .createAlert(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
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
}
