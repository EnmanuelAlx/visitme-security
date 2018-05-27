package gil.mota.visitme.visitmesecurity.viewModels;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.models.Interval;
import gil.mota.visitme.visitmesecurity.models.Visit;
import gil.mota.visitme.visitmesecurity.useCases.DeleteVisit;
import gil.mota.visitme.visitmesecurity.useCases.EditVisit;
import gil.mota.visitme.visitmesecurity.useCases.UseCase;
import gil.mota.visitme.visitmesecurity.utils.Functions;
import gil.mota.visitme.visitmesecurity.utils.Pnotify;
import gil.mota.visitme.visitmesecurity.views.dialogs.IntervalsDialog;
import gil.mota.visitme.visitmesecurity.views.dialogs.ScheduledEditDialog;
import gil.mota.visitme.visitmesecurity.views.dialogs.VisitDialog;

/**
 * Created by mota on 16/4/2018.
 */

public class ItemVisitViewModel extends Observable implements PopupMenu.OnMenuItemClickListener,
                                                              DialogInterface.OnClickListener, UseCase.Result {
    private Visit visit;
    private Context context;
    public ObservableField<String> username;
    public ObservableField<String> time;
    public ObservableField<String> community;
    private Contract contract;

    public ItemVisitViewModel(Visit visit, Context context, Contract contract) {
        this.context = context;
        username = new ObservableField<>("");
        time = new ObservableField<>("");
        community = new ObservableField<>("");
        setVisit(visit);
        this.contract = contract;
    }

    public void abrir(View view)
    {
        VisitDialog dialog = new VisitDialog(context,visit);
        dialog.show();
    }

    public void menu(View view)
    {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_visit);

        if(visit.getKind().equals("SPORADIC"))
            popupMenu.getMenu().removeItem(R.id.edit);

        popupMenu.show();
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
        username.set(visit.getGuest().getName());
        community.set(visit.getCommunity().getName());
        time.set(visit.getDayOfVisit());
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.edit:
                edit();
                return true;
            case R.id.delete:
                delete();
                return true;
        }
        return false;
    }

    private void edit() {
        if (visit.getKind().equals("FREQUENT"))
            showIntervalsDialog();
        else if(visit.getKind().equals("SCHEDULED"))
            showDaySelectDialog();
    }

    private void showIntervalsDialog() {
        ArrayList<Interval> intervals = new ArrayList<>( Arrays.asList(visit.getIntervals()));
        IntervalsDialog dialog = new IntervalsDialog(context, intervalsResult,intervals);
        dialog.show();
    }

    private void showDaySelectDialog() {
        ScheduledEditDialog dialog = new ScheduledEditDialog(context, scheduledResult,
                visit.getDayOfVisitAsDate(),visit.getCompanions(), visit.getDayPartString());
        dialog.show();
    }

    private void delete() {
        Functions.showConfirmDialog(context,this);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        DeleteVisit delete = new DeleteVisit(this);
        delete.setVisit(this.visit);
        delete.run();
    }

    @Override
    public void onError(String error) {
        Pnotify.makeText(context, "Hubo un Error", Toast.LENGTH_SHORT, Pnotify.ERROR).show();
    }

    @Override
    public void onSuccess() {
        Pnotify.makeText(context, "Eliminado Satisfactoriamente", Toast.LENGTH_SHORT, Pnotify.INFO).show();
        contract.remove(visit);
    }

    public interface Contract {
        void remove(Visit visit);
    }

    private IntervalsDialog.Result intervalsResult = new IntervalsDialog.Result() {
        @Override
        public void onClose(final List<Interval> intervals) {
            final Interval[] arry = intervals.toArray(new Interval[intervals.size()]);

            EditVisit useCase = new EditVisit(new UseCase.Result() {
                @Override
                public void onError(String error) {
                    showError(error);
                }

                @Override
                public void onSuccess() {
                    Pnotify.makeText(context,"Actualizacion Satisfactoria", Toast.LENGTH_SHORT, Pnotify.INFO).show();
                    visit.setIntervals(arry);
                    setVisit(visit);
                }
            });
            try {
                useCase.setParam("intervals",arry);
                useCase.setVisitId(visit.get_id());
                useCase.run();
            } catch (JSONException e) {
                onError("Error Inesperado");
            }

        }

        @Override
        public void showError(String err) {
            onError(err);
        }
    };


    private ScheduledEditDialog.Result scheduledResult = new ScheduledEditDialog.Result() {
        @Override
        public void onClose(final Date dayOfVisit, final int companions, final String partOfDay) {
            EditVisit e = new EditVisit(new UseCase.Result() {
                @Override
                public void onError(String error) {
                    showError(error);
                }

                @Override
                public void onSuccess() {
                    visit.setCompanions(companions);
                    visit.setPartOfDay(partOfDay.toLowerCase());
                    visit.setDayOfVisit(dayOfVisit);
                    setVisit(visit);
                    Pnotify.makeText(context,"Actualizacion Satisfactoria", Toast.LENGTH_SHORT, Pnotify.INFO).show();
                }
            });

            try {
                e.setVisitId(visit.get_id());
                e.setParam("dayOfVisit",dayOfVisit.toString());
                e.setParam("companions",companions);
                e.setParam("partOfDay",partOfDay.toUpperCase());
                e.run();
            } catch (JSONException e1) {
                onError("Error Inesperado");
            }

        }

        @Override
        public void showError(String err) {
            onError(err);
        }
    };
}
