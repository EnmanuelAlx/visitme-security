package gil.mota.visitme.visitmesecurity.viewModels;

import android.databinding.ObservableField;
import android.view.View;

import java.util.Observable;

import gil.mota.visitme.visitmesecurity.models.Visit;

public class VisitSuccessViewModel extends Observable {
    private Contract contract;
    private Visit visit;
    public ObservableField<String> name, residentName, residentDetail, identification;

    public VisitSuccessViewModel(Contract contract) {
        this.contract = contract;
        name = new ObservableField<>("");
        residentName = new ObservableField<>("");
        residentDetail = new ObservableField<>("");
        identification = new ObservableField<>("");
        setVisit(visit);
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
        if (visit != null) {
            name.set(visit.getGuest().getName());
            residentName.set(visit.getResident().getName());
            identification.set(visit.getGuest().getIdentification());
            residentDetail.set("????");
            contract.changeImageGuest(visit.getGuest().getImage());
            contract.changeImageResident(visit.getResident().getImage());
        }
    }

    public void aceptar(View view) {
        contract.close();
    }

    public interface Contract {
        void changeImageGuest(String url);

        void changeImageResident(String url);

        void close();
    }

}
