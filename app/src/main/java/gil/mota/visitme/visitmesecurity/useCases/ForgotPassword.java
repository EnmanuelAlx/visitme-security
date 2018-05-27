package gil.mota.visitme.visitmesecurity.useCases;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import gil.mota.visitme.visitmesecurity.managers.RequestManager;

public class ForgotPassword extends UseCase implements Observer<JSONObject> {

    private String email;

    public ForgotPassword(Result result) {
        super(result);
    }

    public void setEmail(String email){
        this.email = email;
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

    @Override
    public void run() {
        try {
            RequestManager.getInstance().forgotPassword(email)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this);
        } catch (JSONException e) {
            onError(new Throwable("Error Inesperado"));
        }
    }
}
