package gil.mota.visitme.visitmesecurity.views.dialogs;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import gil.mota.visitme.visitmesecurity.R;

public class PhotoDialog {

    private Context context;
    private AlertDialog dialog;
    private File photo;


    public PhotoDialog(Context contxt, File photo) {
        this.context = contxt;
        this.photo = photo;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View viewInflated = LayoutInflater.from(context).inflate(R.layout.photo_dialog, null);

        builder.setView(viewInflated);

        fillView(viewInflated);

        dialog = builder.show();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    private void fillView(View view) {
        ImageView image = view.findViewById(R.id.image);
        ImageView exit = view.findViewById(R.id.exit);
        Glide.with(context)
                .load(photo)
                .dontAnimate()
                .placeholder(R.drawable.guy)
                .error(R.drawable.guy)
                .into(image);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
