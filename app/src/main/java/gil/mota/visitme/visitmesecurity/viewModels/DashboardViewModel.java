package gil.mota.visitme.visitmesecurity.viewModels;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.models.Alert;
import gil.mota.visitme.visitmesecurity.models.Visit;
import gil.mota.visitme.visitmesecurity.useCases.GetAlerts;

/**
 * Created by mota on 15/4/2018.
 */

public class DashboardViewModel extends TabedListViewModel<Alert> {
    private List<Alert> incidents, information, others;
    private GetAlerts getAlerts;
    private int skipIncidents = 0, skipInformation = 0,  skipOthers = 0;

    public DashboardViewModel(Interactor interactor) {
        super(interactor);
    }

    @Override
    protected void init() {
        incidents = new ArrayList<>();
        information = new ArrayList<>();
        others = new ArrayList<>();
        getAlerts = new GetAlerts(this);
    }

    @Override
    protected List<Alert> getListByCurrentTab() {
        switch (currentTab) {
            case 0:
                return incidents;
            case 1:
                return information;
            case 2:
                return others;
            default:
                return incidents;
        }
    }

    @Override
    protected void runGetByCurrentTab() {
        switch (currentTab) {
            case 0:
                getAlerts.setParams(currentTab, skipIncidents, incidents);

                break;
            case 1:
                getAlerts.setParams(currentTab, skipInformation, information);

                break;
            case 2:
                getAlerts.setParams(currentTab, skipOthers, others);

                break;
        }
        interactor.loading(true);
        getAlerts.run();
    }

    @Override
    protected void refreshByCurrentTab() {
        switch (currentTab) {
            case 0:
                skipIncidents = 0;
                incidents.clear();
                break;
            case 1:
                skipInformation = 0;
                information.clear();
                break;
            case 2:
                skipOthers = 0;
                others.clear();
                break;
        }
    }


}
