package gil.mota.visitme.visitmesecurity.viewModels;

import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.models.Visit;
import gil.mota.visitme.visitmesecurity.useCases.FindGuest;
import gil.mota.visitme.visitmesecurity.useCases.GetVisits;
import gil.mota.visitme.visitmesecurity.useCases.UseCase;

/**
 * Created by mota on 15/4/2018.
 */

public class VisitsViewModel extends Observable implements  FindGuest.Result {


    public ObservableField<String> identification, email, name;
    public FindGuest findGuest;
    private Contract contract;
    public VisitsViewModel(Contract contract) {
        identification = new ObservableField<>("");
        email = new ObservableField<>("");
        findGuest = new FindGuest(this);
        name = new ObservableField<>("");
        this.contract = contract;
    }


    public void find(View view){
        findGuest.setParams(identification.get(), email.get(), name.get());
        findGuest.run();
    }

    @Override
    public void onSuccess(Visit visit) {
        Log.i("VISIT FLOW","SUCCESS "+ visit.toString());
        contract.goToVisitSuccesfull(visit);
    }

    @Override
    public void onNotSuccess() {
        Log.i("VISIT FLOW","GOING UNEXPECTED");
        contract.goToUnexpectedVisit();
    }

    @Override
    public void onError() {
        contract.onError("Erro al consultar, intente nuevamente");
    }

    public interface Contract
    {
        void goToVisitSuccesfull(Visit visit);
        void goToUnexpectedVisit();
        void onError(String error);
    }
}
