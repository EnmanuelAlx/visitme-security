package gil.mota.visitme.visitmesecurity.useCases;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import gil.mota.visitme.visitmesecurity.managers.RequestManager;
import gil.mota.visitme.visitmesecurity.managers.UserManager;
import gil.mota.visitme.visitmesecurity.utils.PreferencesHelper;


/**
 * Created by mota on 18/4/2018.
 */

public class AddDevice extends UseCase implements Observer<JSONObject> {

    private final int MAX_TRIES = 5;
    private int triesCount;
    public AddDevice(Result result) {
        super(result);
        triesCount = 0;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(JSONObject jsonObject) {

    }

    @Override
    public void onError(Throwable e) {
        if (triesCount < MAX_TRIES)
            this.run();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void run() {
        try {
            RequestManager.getInstance()
                          .addDevice(UserManager.getInstance().getDevice())
                          .subscribeOn(Schedulers.io())
                          .observeOn(AndroidSchedulers.mainThread())
                          .subscribe(this);
            triesCount++;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
