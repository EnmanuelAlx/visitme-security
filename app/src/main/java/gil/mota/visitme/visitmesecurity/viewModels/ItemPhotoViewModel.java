package gil.mota.visitme.visitmesecurity.viewModels;

import android.content.Context;
import android.view.View;

import java.io.File;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.views.dialogs.PhotoDialog;

public class ItemPhotoViewModel extends Observable{


    private final Context context;
    private File photo;

    public ItemPhotoViewModel(File photo, Context context) {
        this.photo = photo;
        this.context = context;
    }

    public void abrir(View view)
    {
        PhotoDialog dialog = new PhotoDialog(context,photo);
        dialog.show();
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }
}
