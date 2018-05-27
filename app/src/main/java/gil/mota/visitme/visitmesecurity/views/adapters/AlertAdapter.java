package gil.mota.visitme.visitmesecurity.views.adapters;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.AlertItemBinding;
import gil.mota.visitme.visitmesecurity.databinding.VisitItemBinding;
import gil.mota.visitme.visitmesecurity.models.Alert;
import gil.mota.visitme.visitmesecurity.views.adapters.holders.AlertViewHolder;
import gil.mota.visitme.visitmesecurity.views.adapters.holders.BaseViewHolder;
import gil.mota.visitme.visitmesecurity.views.adapters.holders.VisitViewHolder;

/**
 * Created by mota on 19/4/2018.
 */

public class AlertAdapter extends BaseRecyclerAdapter<Alert> {
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AlertItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.alert_item,
                parent, false);
        return new AlertViewHolder(binding);
    }
}
