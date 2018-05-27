package gil.mota.visitme.visitmesecurity.views.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.widget.Toast;

import java.util.List;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.ActivityCreateVisitBinding;
import gil.mota.visitme.visitmesecurity.models.Community;
import gil.mota.visitme.visitmesecurity.models.Interval;
import gil.mota.visitme.visitmesecurity.models.Visit;
import gil.mota.visitme.visitmesecurity.utils.Pnotify;
import gil.mota.visitme.visitmesecurity.viewModels.CreateVisitViewModel;
import gil.mota.visitme.visitmesecurity.views.fragments.GuestDataFragment;
import gil.mota.visitme.visitmesecurity.views.fragments.SelectCommunityFragment;
import gil.mota.visitme.visitmesecurity.views.fragments.SelectVisitTypeFragment;

public class CreateVisitActivity extends BindeableActivity implements CreateVisitViewModel.Contract,
        SelectVisitTypeFragment.Contract,
        SelectCommunityFragment.Contract,
        GuestDataFragment.Contract {

    private CreateVisitViewModel viewModel;
    private Fragment[] steps;
    private int currentStep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        steps = new Fragment[]{new SelectVisitTypeFragment(),
                new SelectCommunityFragment(),
                new GuestDataFragment()};
        initDataBinding();
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof CreateVisitViewModel) {
            CreateVisitViewModel viewModel = (CreateVisitViewModel) observable;
        }
    }

    @Override
    public void initDataBinding() {
        ActivityCreateVisitBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_create_visit);
        viewModel = new CreateVisitViewModel(this);
        binding.setViewModel(viewModel);
        selectStep(0);
    }


    @Override
    public void selectStep(int step) {
        Fragment newFragment = getStepFragment(step);
        newFragment.setArguments(viewModel.getActualInfo());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        currentStep = step;
    }

    @Override
    public void onBackPressed() {
        if (currentStep == 0) {
            finish();
        } else {
            currentStep--;
            super.onBackPressed();
        }

    }

    private Fragment getStepFragment(int step) {
        return steps[step];
    }

    @Override
    public void prevStep() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void setError(String error) {
        Pnotify.makeText(this, error, Toast.LENGTH_SHORT, Pnotify.ERROR).show();
    }

    @Override
    public void finishSuccess(Visit visit) {
        Pnotify.makeText(this, "Creacion Satisfactoria", Toast.LENGTH_SHORT, Pnotify.INFO).show();
        Intent data = new Intent();
        data.putExtra("visit",visit);
        setResult(MainActivity.UPDATE_VISITS,data);
        finish();
    }

    @Override
    public void onSelectVisitType(String type) {
        viewModel.setVisitType(type);
    }

    @Override
    public void onSelectCommunity(Community community) {
        viewModel.setCommunity(community);
    }


    @Override
    public void onFillGuestData(String cedula, String name, String dayOfVisit, String partOfDay, int companions, List<Interval> intervals) {
        viewModel.fillGuestData(cedula, name, dayOfVisit,partOfDay, companions, intervals);
    }
}
