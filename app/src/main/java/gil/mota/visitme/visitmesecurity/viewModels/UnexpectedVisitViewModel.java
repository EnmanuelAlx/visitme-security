package gil.mota.visitme.visitmesecurity.viewModels;

import android.databinding.ObservableField;
import android.view.View;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.useCases.RequestAccess;
import gil.mota.visitme.visitmesecurity.useCases.UseCase;

public class UnexpectedVisitViewModel extends Observable implements UseCase.Result {

    public ObservableField<String> name, identification,
                                   residentIdentification, residentDetail;
    private Contract contract;
    private RequestAccess requestAccess;
    private ArrayList<File> photos;

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

    }

    @Override
    public void onSuccess() {
        contract.loading(false);
        contract.close();

    }

    public void addPhotoToList(File photo) {
        photos.add(photo);
        contract.photosChanged();
    }

    public ArrayList<File> getPhotos() {
        return photos;
    }

    public interface Contract {
        void close();
        void showPictureSelector();

        void photosChanged();

        void onError(String error);

        void loading(boolean b);
    }
}
