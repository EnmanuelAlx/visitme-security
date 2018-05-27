package gil.mota.visitme.visitmesecurity.viewModels;

import android.os.Bundle;
import android.view.View;

import java.util.Observable;

import gil.mota.visitme.visitmesecurity.R;

/**
 * Created by mota on 17/4/2018.
 */

public class SelectVisitTypeViewModel extends Observable {

    private Contract contract;
    private String selected;
    public SelectVisitTypeViewModel(Contract contract) {
        this.contract = contract;
    }

    public void setSelect(View v){
        contract.removeBackgrounds();
        contract.setBackgroundTo(v);
        selected = (String) v.getTag();
    }

    public void next(View view)
    {
        if (selected != null)
        contract.next();
        else
            contract.setError("Selecciona el tipo de Visita");
    }

    public String getSelected() {
        return selected;
    }

    public void setArguments(Bundle arguments) {
        selected = arguments.getString("VISIT_TYPE");
        if (selected != null)
        contract.setBackgroundTo(selected);
    }


    public interface Contract {
        void removeBackgrounds();
        void setBackgroundTo(View v);
        void setBackgroundTo(String type);
        void next();
        void setError(String err);
    }
}
