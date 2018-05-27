package gil.mota.visitme.visitmesecurity.viewModels;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.util.List;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.managers.UserManager;
import gil.mota.visitme.visitmesecurity.models.Alert;
import gil.mota.visitme.visitmesecurity.models.Community;
import gil.mota.visitme.visitmesecurity.useCases.CreateAlert;
import gil.mota.visitme.visitmesecurity.useCases.GetCommunities;
import gil.mota.visitme.visitmesecurity.useCases.SignOut;
import gil.mota.visitme.visitmesecurity.useCases.UseCase;
import gil.mota.visitme.visitmesecurity.utils.Functions;
import gil.mota.visitme.visitmesecurity.utils.Pnotify;
import gil.mota.visitme.visitmesecurity.views.dialogs.CreateAlertDialog;
import gil.mota.visitme.visitmesecurity.views.dialogs.SelectCommunityDialog;

/**
 * Created by mota on 14/4/2018.
 */

public class MainViewModel extends Observable implements DialogInterface.OnClickListener, CreateAlertDialog.Result, SelectCommunityDialog.Result {



    private Context context;
    private SignOut signOut;
    private GetCommunities getCommunities;
    private CreateAlert createAlert;
    private ObservableField<Boolean> showCommunitySelector;
    private Contract contract;
    public MainViewModel(@NonNull Context context, Contract contract) {
        this.context = context;
        signOut = new SignOut(context);
        getCommunities = new GetCommunities(getCommunitiesResult);
        getCommunities.run();
        createAlert = new CreateAlert(createAlertResult);
        showCommunitySelector = new ObservableField<>(false);
        this.contract = contract;
    }

    public void signOut() {
        Functions.showAskDialog(context,"¿Esta seguro que desea terminar su sesion?",this);
    }

    public void createAlert(View view) {
        CreateAlertDialog dialog = new CreateAlertDialog(context,this);
        dialog.show();
    }

    public void changeCommunity(View view)
    {
        try {
            List<Community> communities = UserManager.getInstance().getCommunities();
            showSelectCommunity(communities, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        signOut.run();
    }

    @Override
    public void onAlertCreated(Alert alert) {
        try {
            createAlert.setParams(alert);
            createAlert.run();
        } catch (JSONException e) {
            createAlertResult.onError("Error Inesperado");
        }

    }

    private final UseCase.Result getCommunitiesResult = new UseCase.Result() {
        @Override
        public void onError(String error) {
            getCommunities.run();
        }

        @Override
        public void onSuccess() {
            try {

                List<Community> communities = UserManager.getInstance().getCommunities();
                showCommunitySelector.set(communities.size() > 1);
                if(showCommunitySelector.get())
                    showSelectCommunity(communities, false);
                else
                    UserManager.getInstance().setDefaultCommunity(communities.get(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private void showSelectCommunity(List<Community> communities, boolean cancelable) {
        SelectCommunityDialog dialog = new SelectCommunityDialog(context,this, communities, cancelable);
        dialog.show();
    }

    @Override
    public void onSelectCommunity(Community community) {
        try {
            UserManager.getInstance().setDefaultCommunity(community);
            Pnotify.makeText(context,"Comunidad Seleccionada Satisfactoriamente", Toast.LENGTH_SHORT, Pnotify.INFO).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public interface Contract {
        void createVisit();
        void updateAlerts();
    }

    private final UseCase.Result createAlertResult = new UseCase.Result() {
        @Override
        public void onError(String error) {
            Pnotify.makeText(context,error, Toast.LENGTH_SHORT, Pnotify.ERROR).show();
        }

        @Override
        public void onSuccess() {
            Pnotify.makeText(context,"Creacion Satisfactoria", Toast.LENGTH_SHORT, Pnotify.INFO).show();
        }


    };
}
