package gil.mota.visitme.visitmesecurity.useCases;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import gil.mota.visitme.visitmesecurity.managers.ErrorManager;
import gil.mota.visitme.visitmesecurity.managers.RequestManager;
import gil.mota.visitme.visitmesecurity.managers.UserManager;
import gil.mota.visitme.visitmesecurity.models.Community;
import gil.mota.visitme.visitmesecurity.models.Visit;
import gil.mota.visitme.visitmesecurity.utils.Functions;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FindGuest extends UseCase implements Observer<JSONObject>, UseCase.Result {

    private String identification;
    private String email;
    private Result result;
    private Visit visit;
    private String name;
    private String token;

    public FindGuest(Result result) {
        super(null);
        this.result = result;
    }

    @Override
    public void run() {
        try {
            Community def = UserManager.getInstance().getDefaultCommunity();
            RequestManager.getInstance().findGuest(def.get_id(), identification, email, name, token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this);
        } catch (JSONException e) {
            result.onError();
        }

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(JSONObject obj) {
        if (obj.has("id")) {
            markVisitAsChecked(obj);
            this.visit = Functions.parse(obj, Visit.class);
        } else
            result.onNotSuccess();
    }

    private void markVisitAsChecked(JSONObject obj) {
        try {
            MarkVisitAsCheck subUseCase = new MarkVisitAsCheck(this);
            subUseCase.setVisit(obj.getString("id"));
            subUseCase.run();
        } catch (JSONException e) {
            resultSetter.onError("Error Inesperado");
        }

    }

    @Override
    public void onError(Throwable e) {
        try{
            ErrorManager.Error error = (ErrorManager.Error)e;
            if (error.getStatus() == HttpStatus.SC_NOT_FOUND){
                result.onNotSuccess();
                return;
            }
        }catch (ClassCastException ignored){

        }
        result.onError();
    }

    @Override
    public void onComplete() {

    }

    public void setParams(String identification, String email, String name, String token) {
        this.identification = identification;
        this.email = email;
        this.name = name;
        this.token = token;
    }

    @Override
    public void onError(String error) {
        result.onSuccess(visit);
    }

    @Override
    public void onSuccess() {
        result.onSuccess(visit);
    }

    public interface Result {
        void onSuccess(Visit visit);

        void onNotSuccess();

        void onError();
    }
}
