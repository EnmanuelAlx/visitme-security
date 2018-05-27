package gil.mota.visitme.visitmesecurity.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import gil.mota.visitme.visitmesecurity.utils.Functions;

/**
 * Created by mota on 16/4/2018.
 */

public class Visit implements Parcelable {
    private String _id;
    private String kind;
    private Date dayOfVisit;
    private User guest;
    private User resident;
    private int companions;
    private Community community;
    private String partOfDay;
    private Interval[] intervals;

    protected Visit(Parcel in) {
        _id = in.readString();
        kind = in.readString();
        guest = in.readParcelable(User.class.getClassLoader());
        resident = in.readParcelable(User.class.getClassLoader());
        community = in.readParcelable(Community.class.getClassLoader());
        intervals = in.createTypedArray(Interval.CREATOR);
    }

    public static final Creator<Visit> CREATOR = new Creator<Visit>() {
        @Override
        public Visit createFromParcel(Parcel in) {
            return new Visit(in);
        }

        @Override
        public Visit[] newArray(int size) {
            return new Visit[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDayOfVisit() {
        if (kind.equals("SPORADIC"))
            return "";
        int day  = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return dayOfVisit != null ? showAsScheduled() : getNextInterval(day);
    }

    private String showAsScheduled() {
        return new SimpleDateFormat("dd/MM/yyyy").format(dayOfVisit) + " " +  getDayPartString();
    }



    private String getNextInterval(int day) {
        List<Interval> intrvl = findIntervalsInDay(day);

        if(intrvl.isEmpty())
            return getNextInterval(day >= 6 ? 0 : ++day);
        else
            return intrvl.size() == 1 ? intrvl.get(0).toString() : findNearIntervalWithHours(intrvl);

    }

    private String findNearIntervalWithHours(List<Interval> intrvl) {
        int hour  = Calendar.getInstance().get(Calendar.HOUR);
        int min = Calendar.getInstance().get(Calendar.MINUTE);
        Interval minimun = null;
        int rest = 100;
        int op = 0;
        for (Interval i : intrvl)
        {
           op = i.getTo() - (hour * 100 + min);
           if(op >= 0 && op <= rest)
           {
               rest = op;
               minimun = i;
           }

        }
        return minimun != null ? minimun.toString() : intrvl.get(0).toString();
    }

    private List<Interval> findIntervalsInDay(int day)
    {
        List<Interval> intrvls = new ArrayList<>();

        for (Interval i : intervals)
            if(i.getDay() == day)
                intrvls.add(i);

        return intrvls;
    }

    public void setDayOfVisit(Date dayOfVisit) {
        this.dayOfVisit = dayOfVisit;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public Interval[] getIntervals() {
        return intervals;
    }

    public void setIntervals(Interval[] intervals) {
        this.intervals = intervals;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "_id='" + _id + '\'' +
                ", kind='" + kind + '\'' +
                ", dayOfVisit=" + dayOfVisit +
                ", guest=" + guest +
                ", community=" + community +
                ", intervals=" + Arrays.toString(intervals) +
                '}';
    }

    public String getKindString() {
        switch (kind)
        {
            case "SPORADIC":
                return "Esporadica";
            case "FREQUENT":
                return "Frecuente";
            case "SCHEDULED":
                return "Esperada";
        }
        return "";
    }

    public User getResident() {
        return resident;
    }

    public void setResident(User resident) {
        this.resident = resident;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(kind);
        parcel.writeParcelable(guest, i);
        parcel.writeParcelable(resident, i);
        parcel.writeParcelable(community, i);
        parcel.writeTypedArray(intervals, i);
    }

    public String getDayPartString() {

        switch (partOfDay)
        {
            case "AFTERNOON":
                return "Tarde";
            case "MORNING":
                return "Mañana";
            case "NIGHT":
                return "Noche";

                default:
                    return "Mañana";
        }
    }

    public Date getDayOfVisitAsDate() {
        return dayOfVisit;
    }

    public int getCompanions() {
        return companions;
    }

    public void setCompanions(int companions) {
        this.companions = companions;
    }

    public void setPartOfDay(String partOfDay) {
        this.partOfDay = partOfDay;
    }
}
