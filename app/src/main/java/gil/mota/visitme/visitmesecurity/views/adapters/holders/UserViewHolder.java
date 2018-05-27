package gil.mota.visitme.visitmesecurity.views.adapters.holders;

import android.view.View;

import com.bumptech.glide.Glide;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.UserItemBinding;
import gil.mota.visitme.visitmesecurity.models.User;
import gil.mota.visitme.visitmesecurity.viewModels.ItemUserViewModel;
import gil.mota.visitme.visitmesecurity.viewModels.ItemVisitViewModel;

public class UserViewHolder extends BaseViewHolder<User>{

    private UserItemBinding binding;
    private ItemUserViewModel.Contract contract;
    public UserViewHolder(UserItemBinding binding, ItemUserViewModel.Contract contract) {
        super(binding.getRoot());
        this.binding = binding;
        this.contract = contract;
    }

    @Override
    public void onBind() {
        if (binding.getViewModel() == null)
            binding.setViewModel(new ItemUserViewModel(contract,item));
        else
            binding.getViewModel().setUser(item);

        Glide.with(itemView.getContext())
                .load(item.getImage())
                .placeholder(R.drawable.guy)
                .error(R.drawable.guy)
                .dontAnimate()
                .into(binding.image);
    }
}
