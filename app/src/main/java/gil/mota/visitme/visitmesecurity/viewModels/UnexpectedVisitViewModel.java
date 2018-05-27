package gil.mota.visitme.visitmesecurity.viewModels;

import android.databinding.ObservableField;
import android.view.View;

import java.util.Observable;

public class UnexpectedVisitViewModel extends Observable {

    public ObservableField<String> name, identification, residentName, residentIdentification, residentDetail;
    private Contract contract;

    public UnexpectedVisitViewModel(Contract contract) {
        this.contract = contract;
    }

    public void addPhoto(View view) {

    }

    public void requestAccess(View view) {

    }

    public interface Contract
    {
        void close();
    }
}
