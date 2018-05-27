package gil.mota.visitme.visitmesecurity.useCases;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import gil.mota.visitme.visitmesecurity.managers.RequestManager;
import gil.mota.visitme.visitmesecurity.models.Visit;
import gil.mota.visitme.visitmesecurity.utils.Functions;

/**
 * Created by mota on 16/4/2018.
 */

public class GetVisits extends FillListByType<Visit> {

    public GetVisits(Result result) {
        super(result);
    }

    @Override
    protected Observable<JSONObject> getPromiseByType() {
        switch (type) {
            case 0:
                return RequestManager.getInstance().getScheduledVisits(skip, 30);

            case 1:
                return RequestManager.getInstance().getFrequentVisits(skip, 30);

            case 2:
                return RequestManager.getInstance().getSporadicVisits(skip, 30);
        }
        return new Observable<JSONObject>() {
            @Override
            protected void subscribeActual(Observer<? super JSONObject> observer) {
                observer.onError(new Throwable("Error Inesperado!"));
            }
        };
    }

    @Override
    protected Collection<? extends Visit> parseList(JSONObject obj) throws JSONException {
        JSONArray arry = obj.getJSONArray("visits");
        Log.i("MOTA","VISIT JSON"+  obj.toString());
        Visit[] visits = Functions.parse(arry, Visit[].class);
        return Arrays.asList(visits);
    }

}
