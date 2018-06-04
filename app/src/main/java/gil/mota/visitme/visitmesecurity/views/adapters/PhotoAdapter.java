package gil.mota.visitme.visitmesecurity.views.adapters;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.File;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.PhotoItemBinding;
import gil.mota.visitme.visitmesecurity.views.adapters.holders.BaseViewHolder;
import gil.mota.visitme.visitmesecurity.views.adapters.holders.PhotoViewHolder;
import gil.mota.visitme.visitmesecurity.views.adapters.holders.UserViewHolder;

public class PhotoAdapter extends BaseRecyclerAdapter<File>{
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PhotoItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.photo_item,
                parent, false);
        return new PhotoViewHolder(binding);
    }

}
