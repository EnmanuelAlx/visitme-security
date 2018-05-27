package gil.mota.visitme.visitmesecurity.viewModels;

import android.view.View;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.managers.UserManager;
import gil.mota.visitme.visitmesecurity.models.Community;

/**
 * Created by mota on 17/4/2018.
 */

public class SelectCommunityViewModel extends Observable {

    private Contract contract;
    private Community community;
    public SelectCommunityViewModel(Contract contract) {
        this.contract = contract;
    }

    public void select(View view)
    {
        if (contract.getSelected() != null) {
            community = contract.getSelected();
            contract.onSelectCommunity();
        }
        else
            contract.setError("No hay Comunidad Seleccionada");
    }

    public Community getCommunity() {
        return community;
    }

    public List<Community> getCommunities()
    {
        try {
            return UserManager.getInstance().getCommunities();
        } catch (JSONException e) {
            return new ArrayList<>();
        }
    }

    public interface Contract
    {
        void onSelectCommunity();
        void setError(String err);
        Community getSelected();
    }
}
