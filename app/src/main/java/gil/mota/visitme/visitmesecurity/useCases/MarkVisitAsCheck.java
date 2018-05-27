package gil.mota.visitme.visitmesecurity.useCases;

import org.json.JSONException;
import org.json.JSONObject;

import gil.mota.visitme.visitmesecurity.managers.RequestManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MarkVisitAsCheck extends UseCase implements Observer<JSONObject> {

    private String visit;

    public MarkVisitAsCheck(Result result) {
        super(result);
    }

    @Override
    public void run() {
        try {
            RequestManager.getInstance().markVisitAsCheck(visit)
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


    public void setVisit(String visit) {
        this.visit = visit;
    }
}
