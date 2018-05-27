package gil.mota.visitme.visitmesecurity.useCases;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import gil.mota.visitme.visitmesecurity.Consts;
import gil.mota.visitme.visitmesecurity.managers.RequestManager;
import gil.mota.visitme.visitmesecurity.models.Community;
import gil.mota.visitme.visitmesecurity.models.User;
import gil.mota.visitme.visitmesecurity.utils.Functions;

public class GetCompanies extends UseCase implements Observer<JSONObject> {

    private List<User> companies;
    private String query;
    public GetCompanies(Result result, List<User> companies) {
        super(result);
        this.companies = companies;
    }



    @Override
    public void run() {
        RequestManager.getInstance().getCompanies(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(JSONObject obj) {
        JSONArray arry = null;
        try {
            arry = obj.getJSONArray(Consts.COMPANIES);
            User[] companies = Functions.parse(arry,User[].class);
            this.companies.addAll(Arrays.asList(companies));
            resultSetter.onSuccess();
        } catch (JSONException e) {
            onError(new Throwable("Error inesperado"));
        }

    }

    @Override
    public void onError(Throwable e) {
        resultSetter.onError(e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
