package gil.mota.visitme.visitmesecurity.viewModels;

import android.content.Context;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import java.util.Observable;

import gil.mota.visitme.visitmesecurity.models.Alert;


/**
 * Created by mota on 19/4/2018.
 */

public class ItemAlertViewModel extends Observable {
    private Alert alert;
    private Context context;
    public ObservableField<String> title;
    public ObservableField<String> time;
    public ObservableField<String> community;
    public ObservableField<String> autor;
    public ItemAlertViewModel(Alert alert, Context context) {
        this.context = context;
        title = new ObservableField<>("");
        time = new ObservableField<>("");
        community = new ObservableField<>("");
        autor = new ObservableField<>("");
        setAlert(alert);

    }

    public void abrir(View view)
    {
        /*
        AlertDialog dialog = new AlertDialog(context,alert);
        dialog.show();*/
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
        community.set(alert.getCommunity().getName());
        title.set(alert.getMessage());
        time.set(alert.getCreated_at());
        autor.set(alert.getAuthor().getName());
    }

}
