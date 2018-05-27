package gil.mota.visitme.visitmesecurity.views.adapters.holders;

import android.view.View;

import com.bumptech.glide.Glide;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.VisitItemBinding;
import gil.mota.visitme.visitmesecurity.models.Visit;
import gil.mota.visitme.visitmesecurity.viewModels.ItemVisitViewModel;

/**
 * Created by mota on 16/4/2018.
 */

public class VisitViewHolder extends BaseViewHolder<Visit> {
    private VisitItemBinding binding;
    private ItemVisitViewModel.Contract contract;
    public VisitViewHolder(VisitItemBinding itemView, ItemVisitViewModel.Contract contract) {
        super(itemView.itemVisit);
        binding = itemView;
        this.contract = contract;
    }

    @Override
    public void onBind() {
        if (binding.getViewModel() == null)
            binding.setViewModel(new ItemVisitViewModel(item, itemView.getContext(), contract));
        else
            binding.getViewModel().setVisit(item);

        Glide.with(itemView.getContext())
                .load(item.getGuest().getImage())
                .placeholder(R.drawable.guy)
                .error(R.drawable.guy)
                .dontAnimate()
                .into(binding.image);
    }
}
