package gil.mota.visitme.visitmesecurity.views.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import gil.mota.visitme.visitmesecurity.R;


public class ScheduledEditDialog implements DatePickerDialog.OnDateSetListener {

    private Context context;
    private Result result;
    private Date dayOfVisit;
    private int companions;
    private String partOfDay;
    private TextView day;


    public ScheduledEditDialog(Context contxt, Result result, Date dayOfVisit,
                               int companions,
                               String partOfDay) {
        this.context = contxt;
        this.result = result;
        this.dayOfVisit = dayOfVisit;
        this.companions = companions;
        this.partOfDay = partOfDay;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.edit_scheduled_dialog, null);

        final AppCompatSpinner spinner = view.findViewById(R.id.part);
        spinner.setSelection(findSelected(partOfDay));
        final EditText edit = view.findViewById(R.id.companions);
        edit.setText(""+companions);

        day = view.findViewById(R.id.dia);
        day.setText(new SimpleDateFormat("dd/MM/yyyy").format(dayOfVisit));
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateSelect();
            }
        });

        builder.setView(view);
        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);
        final AlertDialog d = builder.create();




        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("DIALOG","ON CLICK OK!");
                        result.onClose(dayOfVisit, Integer.valueOf(edit.getText().toString()),
                                getPartOfDay(spinner.getSelectedItem().toString()));
                        d.dismiss();
                    }
                });
            }
        });

        d.show();





    }

    private String getPartOfDay(String selectedItem) {
        switch (selectedItem){
            case "Mañana":
                return "MORNING";
            case "Tarde":
                return "AFTERNOON";
            case "Noche":
                return "NIGHT";
        }
        return "";
    }

    private void showDateSelect() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMinDate(now);
        try {
            final Activity activity = (Activity) context;
            dpd.show(activity.getFragmentManager(), "DateDialogItem");
        } catch (ClassCastException e) {

        }
    }

    private int findSelected(String partOfDay) {
        String[] parts = context.getResources().getStringArray(R.array.dayParts);
        for (int i = 0; i< parts.length; i++)
        {
            if (parts[i].equals(partOfDay))
                return i;
        }
        return -1;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        String d = dayOfMonth > 9 ? "" + dayOfMonth : "0" + dayOfMonth;
        String m = monthOfYear > 9 ? "" + monthOfYear : "0" + monthOfYear;
        dayOfVisit = new Date(d + "/" + m + "/" + year);
        day.setText(d + "/" + m + "/" + year);
    }


    public interface Result {
        void onClose(Date dayOfVisit,
                     int companions,
                     String partOfDay);

        void showError(String err);
    }
}
