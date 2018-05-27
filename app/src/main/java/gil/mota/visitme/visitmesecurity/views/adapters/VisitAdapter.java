package gil.mota.visitme.visitmesecurity.views.adapters;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.VisitItemBinding;
import gil.mota.visitme.visitmesecurity.models.Visit;
import gil.mota.visitme.visitmesecurity.viewModels.ItemVisitViewModel;
import gil.mota.visitme.visitmesecurity.views.adapters.holders.BaseViewHolder;
import gil.mota.visitme.visitmesecurity.views.adapters.holders.VisitViewHolder;

/**
 * Created by mota on 16/4/2018.
 */

public class VisitAdapter extends BaseRecyclerAdapter<Visit> implements ItemVisitViewModel.Contract {
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        VisitItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.visit_item,
                parent, false);
        return new VisitViewHolder(binding, this);

    }

    @Override
    public void remove(Visit visit) {
        this.list.remove(visit);
        notifyDataSetChanged();
    }
}
