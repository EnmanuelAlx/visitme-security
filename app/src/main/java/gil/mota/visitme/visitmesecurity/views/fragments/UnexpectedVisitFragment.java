package gil.mota.visitme.visitmesecurity.views.fragments;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.FragmentUnexpectedVisitBinding;
import gil.mota.visitme.visitmesecurity.utils.FragmentPager;
import gil.mota.visitme.visitmesecurity.utils.Pnotify;
import gil.mota.visitme.visitmesecurity.viewModels.UnexpectedVisitViewModel;
import gil.mota.visitme.visitmesecurity.views.adapters.PageAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnexpectedVisitFragment extends Fragment implements UnexpectedVisitViewModel.Contract {


    private UnexpectedVisitViewModel viewModel;
    private FragmentUnexpectedVisitBinding binding;
    private FragmentPager pager;

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
        return binding.getRoot();

    }

    public void setPager(FragmentPager pager) {
        this.pager = pager;
    }

    @Override
    public void close() {
        if (pager != null) {
            pager.changePage(0);
            Pnotify.makeText(getActivity(),"Solicitud Enviada satisfactoriamente, en espera de respuesta", Toast.LENGTH_SHORT, Pnotify.INFO).show();
        }
    }
}
