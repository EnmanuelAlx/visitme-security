package gil.mota.visitme.visitmesecurity.views.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.List;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.models.Alert;
import gil.mota.visitme.visitmesecurity.models.Interval;
import gil.mota.visitme.visitmesecurity.viewModels.ItemIntervalViewModel;
import gil.mota.visitme.visitmesecurity.views.adapters.IntervalAdapter;

/**
 * Created by mota on 29/4/2018.
 */

public class IntervalsDialog implements ItemIntervalViewModel.IntervalItemInteractor {
    private Context context;
    private Result result;
    private List<Interval> intervals;
    private IntervalAdapter adapter;

    public IntervalsDialog(Context contxt, Result result, List<Interval> intervals) {
        this.context = contxt;
        this.result = result;
        this.intervals = intervals;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.intervals_dialog, null);
        setupAdapter(view);
        FloatingActionButton button = view.findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInterval();
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

                        if (validateIntervals()) {
                            result.onClose(intervals);
                            d.dismiss();
                        }

                    }
                });
            }
        });

        d.show();
    }

    private void addInterval() {
        intervals.add(new Interval(0,0,2400));
        adapter.notifyDataSetChanged();
    }

    private void setupAdapter(View view) {
        RecyclerView recyler = view.findViewById(R.id.intervals);
        adapter = new IntervalAdapter(IntervalAdapter.EDITABLE_TYPE, this);
        adapter.setList(intervals);
        adapter.setHasStableIds(true);
        recyler.setAdapter(adapter);
        recyler.setLayoutManager(new LinearLayoutManager(context));
    }

    private boolean validateIntervals() {
        return true;
    }


    @Override
    public void remove(Interval interval) {
        this.intervals.remove(interval);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void showTimeDialog(TimePickerDialog.OnTimeSetListener listener) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog dialog = TimePickerDialog.newInstance(listener,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE), true);
        try {
            final Activity activity = (Activity) context;
            dialog.show(activity.getFragmentManager(), "timeDialogItem");
        } catch (ClassCastException e) {

        }
    }

    @Override
    public void showError(String err) {
        result.showError(err);
    }


    public interface Result {
        void onClose(List<Interval> intervals);

        void showError(String err);
    }

}
