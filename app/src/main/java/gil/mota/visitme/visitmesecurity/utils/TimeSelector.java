package gil.mota.visitme.visitmesecurity.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import gil.mota.visitme.visitmesecurity.R;

/**
 * Created by mota on 18/4/2018.
 */

public class TimeSelector extends LinearLayout implements AdapterView.OnItemSelectedListener {

    private Spinner day,hour,minute,meridian;

    public TimeSelector(Context context) {
        super(context);
    }

    public TimeSelector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.time_selector, this, true);
        hour = view.findViewById(R.id.hour);
        day = view.findViewById(R.id.day);
        minute = view.findViewById(R.id.minutes);
        meridian = view.findViewById(R.id.meridian);
        hour.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayFromTo(1,12)));
        hour.setSelection(0);
        minute.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayFromTo(1,59)));
        minute.setOnItemSelectedListener(this);
        meridian.setOnItemSelectedListener(this);
        hour.setOnItemSelectedListener(this);
        minute.setSelection(0);
    }

    public TimeSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private List<String> arrayFromTo(int from, int to)
    {
        ArrayList<String> arry = new ArrayList<>();
        for (int i = from; i<= to; i++)
            arry.add(String.valueOf(i));
        return arry;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("TIME SELECTOR","ITEM:"+i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
