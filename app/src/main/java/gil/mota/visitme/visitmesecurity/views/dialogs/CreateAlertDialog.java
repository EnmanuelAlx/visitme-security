package gil.mota.visitme.visitmesecurity.views.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.ganfra.materialspinner.MaterialSpinner;
import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.models.Alert;
import gil.mota.visitme.visitmesecurity.models.Community;

/**
 * Created by mota on 17/4/2018.
 */

public class CreateAlertDialog {

    private Context context;
    private Result result;
    private MaterialSpinner type;
    private EditText message;
    private EditText description;
    private String[] types = {"INCIDENT", "INFORMATION"};
    private Community community;

    public CreateAlertDialog(Context contxt, Community community, Result result) {
        this.context = contxt;
        this.result = result;
        this.community = community;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.create_alert_dialog, null);
        message = view.findViewById(R.id.message);
        description = view.findViewById(R.id.description);
        builder.setView(view);
        builder.setPositiveButton("EMITIR", null);
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        final AlertDialog d = builder.create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Alert alert = generateAlert(view);
                        if (validateAlert(alert)) {

                            result.onAlertCreated(alert);
                            d.dismiss();
                        }

                    }
                });
            }
        });

        d.show();
    }

    private boolean validateAlert(Alert alert) {
        int errorCount = 0;

        if (alert.getType() == null) {
            type.setError("No puede Estar vacio");
            errorCount++;
        }

        if (alert.getMessage().isEmpty() || alert.getMessage() == null) {
            message.setError("No puede estar vacio");
            errorCount++;
        }
        if (alert.getDescription().isEmpty() || alert.getDescription() == null) {
            description.setError("No puede estar vacio");
            errorCount++;
        }
        return errorCount == 0;
    }

    private Alert generateAlert(View view) {
        Alert alert = new Alert();

        alert.setMessage(message.getText().toString());
        alert.setDescription(description.getText().toString());
        alert.setType("INCIDENT");
        alert.setCommunity(community);
        return alert;
    }

    public interface Result {
        void onAlertCreated(Alert alert);
    }

}
