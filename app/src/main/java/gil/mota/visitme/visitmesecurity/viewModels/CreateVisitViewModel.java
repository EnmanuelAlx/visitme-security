package gil.mota.visitme.visitmesecurity.viewModels;

import android.os.Bundle;

import org.json.JSONException;

import java.util.List;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.managers.UserManager;
import gil.mota.visitme.visitmesecurity.models.Community;
import gil.mota.visitme.visitmesecurity.models.Interval;
import gil.mota.visitme.visitmesecurity.models.Visit;
import gil.mota.visitme.visitmesecurity.useCases.CreateVisit;
import gil.mota.visitme.visitmesecurity.useCases.UseCase;

/**
 * Created by mota on 22/4/2018.
 */

public class CreateVisitViewModel extends Observable implements CreateVisit.Result {

    private Contract contract;
    private String visitType;
    private Community community;
    private Bundle actualInfo;
    private CreateVisit createVisit;
    public CreateVisitViewModel(Contract contract) {
        this.contract = contract;
        actualInfo = new Bundle();
        createVisit = new CreateVisit(this);
    }

    public void setVisitType(String visitType) {

        try {
            this.visitType = visitType;
            actualInfo.putString("VISIT_TYPE", visitType);
            goToSelectCommunityIfNeeded();
        } catch (JSONException e) {
            contract.selectStep(1);
        }
    }

    private void goToSelectCommunityIfNeeded() throws JSONException {
        if (UserManager.getInstance().getCommunities().size() > 1)
            contract.selectStep(1);
        else {
            try {
                setCommunity(UserManager.getInstance().getCommunities().get(0));
                contract.selectStep(2);
            } catch (Exception e) {
                contract.setError("No estas en una comunidad aun!");
            }

        }

    }

    public void setCommunity(Community community) {
        this.community = community;
        actualInfo.putString("COMMUNITY", community.get_id());
        contract.selectStep(2);
    }

    public Bundle getActualInfo() {
        return actualInfo;
    }

    public void fillGuestData(String cedula, String name, String dayOfVisit, String partOfDay, int companions, List<Interval> intervals) {
        createVisit.setParams(cedula, name, dayOfVisit, intervals, partOfDay, companions, community, visitType);
        createVisit.run();
    }

    @Override
    public void onError(String error) {
        contract.setError(error);
    }

    @Override
    public void onSuccess() {

    }


    @Override
    public void onVisitCreated(Visit visit) {
        contract.finishSuccess(visit);
    }

    public interface Contract {
        void selectStep(int step);

        void prevStep();

        void setError(String error);

        void finishSuccess(Visit visit);
    }
}
