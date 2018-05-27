package gil.mota.visitme.visitmesecurity.views.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.FragmentDashboardBinding;
import gil.mota.visitme.visitmesecurity.databinding.FragmentVisitsBinding;
import gil.mota.visitme.visitmesecurity.models.Alert;
import gil.mota.visitme.visitmesecurity.utils.Pnotify;
import gil.mota.visitme.visitmesecurity.viewModels.DashboardViewModel;
import gil.mota.visitme.visitmesecurity.viewModels.TabedListViewModel;
import gil.mota.visitme.visitmesecurity.viewModels.VisitsViewModel;
import gil.mota.visitme.visitmesecurity.views.adapters.AlertAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements Observer, TabLayout.OnTabSelectedListener, TabedListViewModel.Interactor {

    private FragmentDashboardBinding binding;
    private DashboardViewModel viewModel;
    private TabLayout tabLayout;
    private AlertAdapter adapter;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        viewModel = new DashboardViewModel(this);
        binding.setViewModel(viewModel);
        setupObserver(viewModel);
        setupTabs();
        setupListView();
        return binding.getRoot();
    }

    private void setupTabs() {
        tabLayout = binding.tabs;
        tabLayout.addTab(tabLayout.newTab().setText("ALERTAS"));
        tabLayout.addTab(tabLayout.newTab().setText("INFORMACION"));
        tabLayout.addTab(tabLayout.newTab().setText("OTRAS"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(this);
    }

    private void setupObserver(Observable observable)
    {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof DashboardViewModel)
        {
            DashboardViewModel viewModel = (DashboardViewModel) observable;
        }
    }


    private void setupListView()
    {
        adapter = new AlertAdapter();
        adapter.setHasStableIds(true);
        binding.list.setAdapter(adapter);
        binding.list.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
            }
        });
    }

    public void changeList(List list){
        adapter.setList((List<Alert>)list);
        binding.swipe.setRefreshing(false);
    }

    @Override
    public void loading(boolean loading) {
        binding.swipe.setRefreshing(loading);
    }

    @Override
    public void showError(String error) {
        Pnotify.makeText(getContext(),error, Toast.LENGTH_SHORT, Pnotify.ERROR).show();
        binding.swipe.setRefreshing(false);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewModel.changeTab(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
