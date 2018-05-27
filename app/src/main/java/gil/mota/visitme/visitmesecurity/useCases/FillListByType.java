package gil.mota.visitme.visitmesecurity.useCases;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mota on 19/4/2018.
 */

public abstract class FillListByType<T> extends UseCase implements Observer<JSONObject> {
    protected int type;
    protected int skip;
    private List<T> listToFill;

    public FillListByType(Result result) {
        super(result);
    }

    @Override
    public void run() {
        getPromiseByType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);

    }

    protected abstract Observable<JSONObject> getPromiseByType();
    public void setParams(int type, int skip, List<T> toFill) {
        this.type = type;
        this.skip = skip;
        this.listToFill = toFill;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(JSONObject obj) {
        try {
            fill(obj);
            resultSetter.onSuccess();
        } catch (JSONException e) {
            e.printStackTrace();
            resultSetter.onError("Error Inesperado");
        }
    }

    private void fill(JSONObject obj) throws JSONException {

        this.listToFill.addAll(parseList(obj));
    }

    protected abstract Collection<? extends T> parseList(JSONObject obj) throws JSONException;

    @Override
    public void onError(Throwable e) {
        resultSetter.onError(e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
