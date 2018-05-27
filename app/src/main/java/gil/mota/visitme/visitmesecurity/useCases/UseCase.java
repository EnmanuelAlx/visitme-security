package gil.mota.visitme.visitmesecurity.useCases;

import org.json.JSONObject;

/**
 * Created by mota on 14/4/2018.
 */

public abstract class UseCase {

    protected final Result resultSetter;
    public UseCase(Result result)
    {
        this.resultSetter = result;
    }
    public abstract void run();

    public abstract interface Result {
        void onError(String error);
        void onSuccess();
    }
}
