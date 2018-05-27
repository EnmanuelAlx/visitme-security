package gil.mota.visitme.visitmesecurity.views.adapters.holders;

import android.view.View;

import com.bumptech.glide.Glide;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.AlertItemBinding;
import gil.mota.visitme.visitmesecurity.models.Alert;
import gil.mota.visitme.visitmesecurity.viewModels.ItemAlertViewModel;

/**
 * Created by mota on 19/4/2018.
 */

public class AlertViewHolder extends BaseViewHolder<Alert> {
    private AlertItemBinding binding;
    public AlertViewHolder(AlertItemBinding binding) {
        super(binding.itemAlert);
        this.binding = binding;
    }

    @Override
    public void onBind() {
        if (binding.getViewModel() == null)
            binding.setViewModel(new ItemAlertViewModel(item, itemView.getContext()));
        else
            binding.getViewModel().setAlert(item);

        Glide.with(itemView.getContext())
                .load(item.getAuthor().getImage())
                .placeholder(R.drawable.guy)
                .error(R.drawable.guy)
                .dontAnimate()
                .into(binding.image);
    }
}
