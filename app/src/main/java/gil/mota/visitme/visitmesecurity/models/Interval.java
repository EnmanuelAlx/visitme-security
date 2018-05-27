package gil.mota.visitme.visitmesecurity.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import gil.mota.visitme.visitmesecurity.MyApplication;
import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.utils.Functions;

/**
 * Created by mota on 16/4/2018.
 */

public class Interval implements Parcelable {
    private int from;
    private int to;
    private int day;

    public Interval(int day, int from, int to) {
        this.day = day;
        this.from = from;
        this.to = to;
    }

    protected Interval(Parcel in) {
        from = in.readInt();
        to = in.readInt();
        day = in.readInt();
    }

    public static final Creator<Interval> CREATOR = new Creator<Interval>() {
        @Override
        public Interval createFromParcel(Parcel in) {
            return new Interval(in);
        }

        @Override
        public Interval[] newArray(int size) {
            return new Interval[size];
        }
    };

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getFromStr() {
        return Functions.intHourToStr(from);
    }

    public String getToStr() {
        return Functions.intHourToStr(to);
    }


    @Override
    public String toString() {
        return MyApplication.getInstance().getResources().getStringArray(R.array.days)[day] + " "
                                                                + getFromStr() + " - " + getToStr();
    }

    public void setTo(String s) {
        try {
            if (s != null)
                to = Integer.valueOf(s.replace(":", ""));
        } catch (Exception e) {
            Log.i("INTERVAL" , "SET TO EX" + s + " " + s.replace(":", ""));
        }

    }

    public void setFrom(String s) {
        try {
            if (s != null)
                from = Integer.valueOf(s.replace(":", ""));
        } catch (Exception e) {
            Log.i("INTERVAL" , "SET FROM EX" + s + " " + s.replace(":", ""));
        }


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(day);
        parcel.writeInt(from);
        parcel.writeInt(to);
    }
}
