package gil.mota.visitme.visitmesecurity.views.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

import gil.mota.visitme.visitmesecurity.MyApplication;
import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.FragmentUnexpectedVisitBinding;
import gil.mota.visitme.visitmesecurity.utils.FragmentPager;
import gil.mota.visitme.visitmesecurity.utils.Functions;
import gil.mota.visitme.visitmesecurity.utils.Pnotify;
import gil.mota.visitme.visitmesecurity.viewModels.UnexpectedVisitViewModel;
import gil.mota.visitme.visitmesecurity.views.adapters.PhotoAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnexpectedVisitFragment extends Fragment implements UnexpectedVisitViewModel.Contract, DialogInterface.OnClickListener {


    private static final int SELECT_MULTIPLE_IMAGES = 1111;
    private UnexpectedVisitViewModel viewModel;
    private FragmentUnexpectedVisitBinding binding;
    private FragmentPager pager;
    private PhotoAdapter photoAdapter;

    public UnexpectedVisitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_unexpected_visit, container, false);
        viewModel = new UnexpectedVisitViewModel(this);
        binding.setViewModel(viewModel);
        setupAdapter(binding);
        return binding.getRoot();

    }

    private void setupAdapter(FragmentUnexpectedVisitBinding binding) {
        photoAdapter = new PhotoAdapter();
        photoAdapter.setList(viewModel.getPhotos());
        binding.photos.setAdapter(photoAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.photos.setLayoutManager(manager);
    }

    public void setPager(FragmentPager pager) {
        this.pager = pager;
    }

    @Override
    public void close() {
        if (pager != null) {
            Pnotify.makeText(MyApplication.getInstance(), "Solicitud Enviada satisfactoriamente", Toast.LENGTH_SHORT, Pnotify.INFO).show();
            pager.changePage(0);
        }
    }

    @Override
    public void showPictureSelector() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, SELECT_MULTIPLE_IMAGES);
    }

    @Override
    public void askIfGiveAccess(DialogInterface.OnClickListener yes, DialogInterface.OnClickListener no) {
        Functions.showAskDialog(getActivity(), "El Residente no posee aplicacion o no esta disponible para responder, ¿Desea conceder el acceso a este visitante?", yes, no);
    }

    @Override
    public void photosChanged() {
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String error) {
        Pnotify.makeText(getActivity(), error, Toast.LENGTH_SHORT, Pnotify.ERROR).show();
    }

    @Override
    public void loading(boolean b) {
        binding.loader.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("GET PHOTO", "" + data.toString());
        if (saveImage(data))
            Functions.showAskDialog(getActivity(), "¿Desea tomar otra foto?", this);
    }

    private boolean saveImage(Intent data) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        Uri tempUri = getImageUri(MyApplication.getInstance(), photo);

        if (tempUri == null) {
            onError("La Aplicacion no tiene permisos para guardar imagenes");
            return false;
        }

        File finalFile = new File(getRealPathFromURI(tempUri));
        viewModel.addPhotoToList(finalFile);
        return true;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        showPictureSelector();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
            return Uri.parse(path);
        } catch (Exception e) {
            return null;
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}
