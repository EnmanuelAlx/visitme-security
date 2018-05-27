package gil.mota.visitme.visitmesecurity.views.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.json.JSONException;

import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.models.Community;

public class SelectCommunityDialog {


    private Context context;
    private Result result;
    private List<Community> communities;
    private MaterialSpinner community;
    private ArrayAdapter<Community> communityAdapter;
    private String[] types = {"INCIDENT", "INFORMATION"};
    private boolean cancelable;

    public SelectCommunityDialog(Context contxt, Result result, List<Community> communities, boolean cancelable) {
        this.context = contxt;
        this.result = result;
        this.communities = communities;
        this.cancelable = cancelable;
    }

    public void show() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.select_community_dialog, null);
        fillSpinners(view);
        builder.setView(view);
        builder.setPositiveButton("ACEPTAR", null);
        if (cancelable)
            builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
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
                        result.onSelectCommunity((Community) community.getSelectedItem());
                        d.dismiss();
                    }
                });
            }
        });

        d.show();
    }


    private void fillSpinners(View view) {
        community = view.findViewById(R.id.community);
        communityAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, communities);
        communityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        community.setAdapter(communityAdapter);
    }


    public interface Result {
        void onSelectCommunity(Community community);
    }

}
