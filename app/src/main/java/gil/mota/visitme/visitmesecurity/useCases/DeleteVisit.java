package gil.mota.visitme.visitmesecurity.useCases;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import gil.mota.visitme.visitmesecurity.managers.RequestManager;
import gil.mota.visitme.visitmesecurity.models.Visit;

public class DeleteVisit extends UseCase implements Observer<JSONObject> {


    private Visit visit;

    public DeleteVisit(Result result) {
        super(result);
    }

    @Override
    public void run() {
        try {
            RequestManager.getInstance().deleteVisit(visit.get_id())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this);
        } catch (JSONException e) {
            onError(e);
        }
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
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
        resultSetter.onError("");
    }

    @Override
    public void onComplete() {

    }
}
