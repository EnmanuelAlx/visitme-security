package gil.mota.visitme.visitmesecurity.views.adapters.holders;

import android.view.View;

import gil.mota.visitme.visitmesecurity.databinding.IntervalItem1Binding;
import gil.mota.visitme.visitmesecurity.databinding.IntervalItemBinding;
import gil.mota.visitme.visitmesecurity.models.Interval;
import gil.mota.visitme.visitmesecurity.viewModels.ItemIntervalViewModel;

/**
 * Created by mota on 28/4/2018.
 */

public class IntervalViewHolder extends BaseViewHolder<Interval> {
    private ItemIntervalViewModel.IntervalItemInteractor interactor;
    private ItemIntervalViewModel viewModel;
    private IntervalItemBinding binding1;
    private IntervalItem1Binding binding2;

    public IntervalViewHolder(IntervalItemBinding itemView) {
        super(itemView.getRoot());
        binding1 = itemView;
    }


    public IntervalViewHolder(IntervalItem1Binding itemView, ItemIntervalViewModel.IntervalItemInteractor interactor) {
        super(itemView.getRoot());
        binding2 = itemView;
        this.interactor = interactor;
    }


    @Override
    public void onBind() {

        bindByNotNull();

    }

    private void bindByNotNull() {
        if(binding1 != null)
        {

            if (binding1.getViewModel() == null)
                binding1.setViewModel(new ItemIntervalViewModel(itemView.getContext(), item));
            else
                binding1.getViewModel().setInterval(item);
        }
        else {
            if (binding2.getViewModel() == null)
                binding2.setViewModel(new ItemIntervalViewModel(itemView.getContext(), item, interactor));
            else
                binding2.getViewModel().setInterval(item);
        }
    }
}
