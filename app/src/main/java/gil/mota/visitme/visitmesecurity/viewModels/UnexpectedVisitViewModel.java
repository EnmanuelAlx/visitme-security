package gil.mota.visitme.visitmesecurity.viewModels;

import android.content.DialogInterface;
import android.databinding.ObservableField;
import android.view.View;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.useCases.RequestAccess;
import gil.mota.visitme.visitmesecurity.useCases.UseCase;
import gil.mota.visitme.visitmesecurity.utils.Functions;

public class UnexpectedVisitViewModel extends Observable implements RequestAccess.Result {

    public ObservableField<String> name, identification,
            residentIdentification, residentDetail;
    private Contract contract;
    private RequestAccess requestAccess;
    private ArrayList<File> photos;
    private DialogInterface.OnClickListener giveAccessListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            requestAccess.run();
            contract.loading(true);
        }
    };


    private DialogInterface.OnClickListener noAccessListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            contract.loading(false);
            contract.close();
        }
    };

    public UnexpectedVisitViewModel(Contract contract) {
        this.contract = contract;
        photos = new ArrayList<>();
        requestAccess = new RequestAccess(this);
        name = new ObservableField<>();
        identification = new ObservableField<>();
        residentIdentification = new ObservableField<>();
        residentDetail = new ObservableField<>();
    }

    public void addPhoto(View view) {
        contract.showPictureSelector();
    }

    public void requestAccess(View view) {
        try {
            requestAccess.setParams(name.get(), identification.get(), residentIdentification.get(), residentDetail.get());
            requestAccess.setPhotos(photos);
            requestAccess.run();
            contract.loading(true);
        } catch (Exception e) {
            onError("Ocurrio un error inesperado, intente mas tarde");
        }

    }

    @Override
    public void onError(String error) {
        contract.loading(false);
        contract.onError(error);
        requestAccess.reset();
    }

    @Override
    public void onSuccess() {
        contract.loading(false);
        contract.close();
        requestAccess.reset();
    }

    public void addPhotoToList(File photo) {
        photos.add(photo);
        contract.photosChanged();
    }

    public ArrayList<File> getPhotos() {
        return photos;
    }

    @Override
    public void onResidentDeviceNotFound() {
        requestAccess.changeToGive();
        contract.askIfGiveAccess(giveAccessListener, noAccessListener);
    }

    public interface Contract {
        void close();

        void showPictureSelector();

        void askIfGiveAccess(DialogInterface.OnClickListener yes, DialogInterface.OnClickListener no);

        void photosChanged();

        void onError(String error);

        void loading(boolean b);
    }
}
