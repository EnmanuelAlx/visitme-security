package gil.mota.visitme.visitmesecurity.views.dialogs;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;

import org.json.JSONException;

import java.util.Calendar;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.managers.UserManager;
import gil.mota.visitme.visitmesecurity.models.Alert;
import gil.mota.visitme.visitmesecurity.models.Community;
import gil.mota.visitme.visitmesecurity.utils.TimeSelector;

/**
 * Created by mota on 17/4/2018.
 */

public class CreateAlertDialog implements AdapterView.OnItemSelectedListener {

    private Context context;
    private Result result;
    private List<Community> communities;
    private ArrayAdapter<Community> communityAdapter;
    private MaterialSpinner community;
    private MaterialSpinner type;
    private EditText message;
    private EditText description;
    private String[] types = {"INCIDENT", "INFORMATION"};
    public CreateAlertDialog(Context contxt, Result result) {
        this.context = contxt;
        this.result = result;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.create_alert_dialog, null);
        fillSpinners(view);
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
        if (alert.getCommunity() == null) {
            community.setError("No puede estar vacio");
            errorCount++;
        }

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
        Object s = type.getVisibility() == View.GONE ? "INCIDENT" : types[type.getSelectedItemPosition() - 1];
        alert.setType((String) s);
        alert.setCommunity((Community) community.getSelectedItem());
        return alert;
    }

    private void fillSpinners(View view) {

        try {
            communities = UserManager.getInstance().getCommunities();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        type = view.findViewById(R.id.type);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, new String[]{"Incidente", "Informacion"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        community = view.findViewById(R.id.community);
        communityAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, communities);
        communityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        community.setAdapter(communityAdapter);
        community.setOnItemSelectedListener(this);

        if (communities.size() < 2) {
            community.setSelection(0);
            community.setVisibility(View.GONE);
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Community c = (Community) community.getSelectedItem();
        if (c != null) {
            int visibility = c.getKind().equals("ADMINISTRATOR") ? View.VISIBLE : View.GONE;
            type.setVisibility(visibility);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface Result {
        void onAlertCreated(Alert alert);
    }

}
