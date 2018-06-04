package gil.mota.visitme.visitmesecurity.views.adapters.holders;

import android.view.View;

import com.bumptech.glide.Glide;

import java.io.File;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.PhotoItemBinding;
import gil.mota.visitme.visitmesecurity.viewModels.ItemPhotoViewModel;

public class PhotoViewHolder extends BaseViewHolder<File> {

    private PhotoItemBinding binding;
    public PhotoViewHolder(PhotoItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    public void onBind() {
        if (binding.getViewModel() == null)
            binding.setViewModel(new ItemPhotoViewModel(item, itemView.getContext()));
        else
            binding.getViewModel().setPhoto(item);

        Glide.with(itemView.getContext())
                .load(item)
                .placeholder(R.drawable.guy)
                .error(R.drawable.guy)
                .dontAnimate()
                .into(binding.image);
    }
}
