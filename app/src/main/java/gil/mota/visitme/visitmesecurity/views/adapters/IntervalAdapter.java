package gil.mota.visitme.visitmesecurity.views.adapters;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.IntervalItem1Binding;
import gil.mota.visitme.visitmesecurity.databinding.IntervalItemBinding;
import gil.mota.visitme.visitmesecurity.models.Interval;
import gil.mota.visitme.visitmesecurity.viewModels.ItemIntervalViewModel;
import gil.mota.visitme.visitmesecurity.views.adapters.holders.BaseViewHolder;
import gil.mota.visitme.visitmesecurity.views.adapters.holders.IntervalViewHolder;

/**
 * Created by mota on 28/4/2018.
 */

public class IntervalAdapter extends BaseRecyclerAdapter<Interval> {

    public static final int SHOWABLE_TYPE = 0;
    public static final int EDITABLE_TYPE = 1;
    private int type;
    private ItemIntervalViewModel.IntervalItemInteractor interactor;

    public IntervalAdapter(int type) {
        this.type = type;
    }

    public IntervalAdapter(int type, ItemIntervalViewModel.IntervalItemInteractor interactor) {
        this.type = type;
        this.interactor = interactor;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (type == SHOWABLE_TYPE) {
            IntervalItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.interval_item,
                    parent, false);
            return new IntervalViewHolder(binding);
        } else {
            IntervalItem1Binding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.interval_item_1,
                    parent, false);
            return new IntervalViewHolder(binding, interactor);
        }

    }

}
