package gil.mota.visitme.visitmesecurity.views.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.FragmentVisitsBinding;
import gil.mota.visitme.visitmesecurity.models.Visit;
import gil.mota.visitme.visitmesecurity.utils.FragmentPager;
import gil.mota.visitme.visitmesecurity.utils.Pnotify;
import gil.mota.visitme.visitmesecurity.viewModels.TabedListViewModel;
import gil.mota.visitme.visitmesecurity.viewModels.VisitsViewModel;
import gil.mota.visitme.visitmesecurity.views.adapters.PageAdapter;
import gil.mota.visitme.visitmesecurity.views.adapters.VisitAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisitsFragment extends Fragment implements Observer, VisitsViewModel.Contract {

    private FragmentVisitsBinding binding;
    private VisitsViewModel viewModel;
    private FragmentPager pager;

    public VisitsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_visits, container, false);
        viewModel = new VisitsViewModel(this);
        binding.setViewModel(viewModel);
        setupObserver(viewModel);

        return binding.getRoot();
    }



    private void setupObserver(Observable observable)
    {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof VisitsViewModel)
        {
            VisitsViewModel appViewModel = (VisitsViewModel) observable;
        }
    }


    @Override
    public void goToVisitSuccesfull(Visit visit) {
        if(pager != null)
        {
            Log.i("VISIT FLOW","CHANGING PAGE "+visit);
            Bundle bundle = new Bundle();
            bundle.putParcelable("visit",visit);
            pager.changePage(1);
            pager.setArgumentsToCurrentPage(bundle);
        }
    }

    @Override
    public void goToUnexpectedVisit() {
        if(pager != null)
        {
            Log.i("VISIT FLOW","CHANGING PAGE UNEXPECTED");
            pager.changePage(2);
        }
    }

    @Override
    public void onError(String error) {
        Pnotify.makeText(getActivity(), error,Toast.LENGTH_SHORT, Pnotify.ERROR).show();
    }

    public void setPager(FragmentPager pager) {
        this.pager = pager;
    }
}
