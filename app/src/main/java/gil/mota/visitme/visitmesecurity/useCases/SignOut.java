package gil.mota.visitme.visitmesecurity.useCases;

import android.content.Context;

import gil.mota.visitme.visitmesecurity.MyApplication;
import gil.mota.visitme.visitmesecurity.managers.UserManager;

/**
 * Created by mota on 15/4/2018.
 */

public class SignOut extends UseCase implements UseCase.Result {

    private Context context;

    public SignOut(Context context) {
        super(null);
        this.context = context;
    }

    @Override
    public void run() {
        new RemoveDevice(this).run();
    }

    @Override
    public void onError(String error) {
        UserManager.getInstance().logout();
        MyApplication.getInstance().goToLoginActivity(context);
    }

    @Override
    public void onSuccess() {
        UserManager.getInstance().logout();
        MyApplication.getInstance().goToLoginActivity(context);
    }
}
