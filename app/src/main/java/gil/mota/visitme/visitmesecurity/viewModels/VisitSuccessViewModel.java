package gil.mota.visitme.visitmesecurity.viewModels;

import android.content.DialogInterface;
import android.databinding.ObservableField;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.models.Visit;
import gil.mota.visitme.visitmesecurity.useCases.AddPhotoToVisit;
import gil.mota.visitme.visitmesecurity.useCases.RequestAccess;

public class VisitSuccessViewModel extends Observable implements RequestAccess.Result{
    private Contract contract;
    private Visit visit;
    private ArrayList<File> photos;
    private AddPhotoToVisit requestAccess;
    public ObservableField<String> name, residentName, residentDetail, identification, residentImage;
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


    public VisitSuccessViewModel(Contract contract) {
        this.contract = contract;
        name = new ObservableField<>("");
        residentName = new ObservableField<>("");
        residentDetail = new ObservableField<>("");
        identification = new ObservableField<>("");
        residentImage = new ObservableField<>("");
        photos = new ArrayList<>();
        requestAccess = new AddPhotoToVisit(this);
        setVisit(visit);
    }

    public void onError(String error) {
        contract.loading(false);
        contract.onError(error);
        requestAccess.reset();
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
        if (visit != null) {
            name.set(visit.getGuest().getName());
            residentName.set(visit.getResident().getName());
            identification.set(visit.getGuest().getIdentification());
            residentDetail.set(visit.getResident().getIdentification());
            residentImage.set(visit.getResident().getImage());
            contract.changeImageGuest(visit.getGuest().getImage());
            contract.changeImageResident(visit.getResident().getImage());
        }
    }

    public void addPhotoToList(File photo) {
        photos.clear();
        photos.add(photo);
    }

    public ArrayList<File> getPhotos() {
        return photos;
    }

    public void addPhoto(View view) {
        contract.showPictureSelector();
    }

    public void requestAccess(View view) {
        try {
            requestAccess.setParams(identification.get());
            requestAccess.setVisit(identification.get());
            requestAccess.setPhotos(photos);
            requestAccess.run();
            contract.loading(true);
        } catch (Exception e) {
            onError("Ocurrio un error inesperado, intente mas tarde");
        }
    }

    @Override
    public void onResidentDeviceNotFound() {
        requestAccess.changeToGive();
        contract.askIfGiveAccess(giveAccessListener, noAccessListener);
    }

    @Override
    public void onSuccess() {
        contract.loading(false);
        contract.close();
        requestAccess.reset();
    }

    public interface Contract {
        void askIfGiveAccess(DialogInterface.OnClickListener yes, DialogInterface.OnClickListener no);

        void changeImageGuest(String url);

        void changeImageResident(String url);

        void showPictureSelector();

        void close();

        void loading(boolean b);

        void onError(String error);

    }

}
