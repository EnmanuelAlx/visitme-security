package gil.mota.visitme.visitmesecurity.views.fragments;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.FragmentVisitSuccessBinding;
import gil.mota.visitme.visitmesecurity.models.Visit;
import gil.mota.visitme.visitmesecurity.utils.FragmentPager;
import gil.mota.visitme.visitmesecurity.viewModels.VisitSuccessViewModel;
import gil.mota.visitme.visitmesecurity.views.adapters.PageAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisitSuccessFragment extends Fragment implements VisitSuccessViewModel.Contract {


    private VisitSuccessViewModel viewModel;
    private FragmentVisitSuccessBinding binding;
    private Visit visit;
    private FragmentPager pager;

    public VisitSuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_visit_success, container, false);
        visit = getArguments().getParcelable("visit");
        viewModel = new VisitSuccessViewModel(this, visit);
        binding.setViewModel(viewModel);
        return binding.getRoot();

    }

    @Override
    public void changeImageGuest(String url) {
        Glide.with(getActivity()).load(url).placeholder(R.drawable.guy).error(R.drawable.guy).into(binding.guestImage);
    }

    @Override
    public void changeImageResident(String url) {
        Glide.with(getActivity()).load(url).placeholder(R.drawable.guy).error(R.drawable.guy).into(binding.residentImage);
    }

    @Override
    public void close() {
        if (pager != null) {
            pager.changePage(0);
        }
    }

    public void setPager(FragmentPager pager) {
        this.pager = pager;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        visit = args.getParcelable("visit");
        viewModel.setVisit(visit);
    }
}
