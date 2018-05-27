package gil.mota.visitme.visitmesecurity.viewModels;


import android.content.Context;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.models.Interval;

/**
 * Created by mota on 28/4/2018.
 */

public class ItemIntervalViewModel extends Observable {
    public ObservableField<Integer> daySelected;
    public ObservableField<String> day;
    public ObservableField<String> from;
    public ObservableField<String> to;
    private Interval interval;
    private Context context;
    private IntervalItemInteractor interactor;

    public ItemIntervalViewModel(Context context, final Interval itrvl) {

        day = new ObservableField<>("");
        from = new ObservableField<>("");
        to = new ObservableField<>("");
        daySelected = new ObservableField<>(0);
        this.context = context;
        setInterval(itrvl);


    }


    public ItemIntervalViewModel(Context context, Interval interval, IntervalItemInteractor interactor) {
        day = new ObservableField<>("");
        from = new ObservableField<>("");
        to = new ObservableField<>("");
        daySelected = new ObservableField<>(0);
        this.context = context;
        this.interactor = interactor;
        setInterval(interval);
        addFieldChangesListeners();
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
        day.set(context.getResources().getStringArray(R.array.days)[interval.getDay()]);
        from.set(interval.getFromStr());
        to.set(interval.getToStr());
        daySelected.set(interval.getDay());
    }

    private void addFieldChangesListeners() {
        to.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
                if (!to.get().equals(""))
                    interval.setTo(to.get());
            }
        });

        from.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
                if (!from.get().equals(""))
                    interval.setFrom(from.get());
            }
        });
    }

    public void remove(View view) {
        interactor.remove(interval);
    }

    public void changeTime(View view) {
        final String tag = (String) view.getTag();
        final ObservableField<String> field = tag.equals("from") ? from : to;
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(com.wdullaer.materialdatetimepicker.time.TimePickerDialog view, int hourOfDay, int minute, int second) {
                onTimeChange(tag, field, hourOfDay, minute);
            }
        };
        interactor.showTimeDialog(listener);


    }

    private void onTimeChange(String tag, ObservableField<String> field, int hourOfDay, int minute) {
        if (validByType(tag, hourOfDay, minute)) {
            String h = hourOfDay > 9 ? "" + hourOfDay : "0" + hourOfDay;
            String m = minute > 9 ? "" + minute : "0" + minute;
            field.set("" + h + ":" + m);
        }
        else {
            this.interactor.showError("Hora No Valida!");
        }
    }

    private boolean validByType(String tag, int hourOfDay, int minute) {
        return tag.equals("FROM") ? (hourOfDay * 100 + minute) < interval.getTo() :
                                    (hourOfDay * 100 + minute) > interval.getFrom();
    }

    public void onDaySelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        this.interval.setDay(position);
    }

    public interface IntervalItemInteractor {
        void remove(Interval interval);

        void showTimeDialog(com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener listener);

        void showError(String err);
    }

}
