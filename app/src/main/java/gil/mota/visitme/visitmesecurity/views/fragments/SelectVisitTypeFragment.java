package gil.mota.visitme.visitmesecurity.views.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.FragmentSelectVisitTypeBinding;
import gil.mota.visitme.visitmesecurity.utils.Pnotify;
import gil.mota.visitme.visitmesecurity.viewModels.SelectVisitTypeViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectVisitTypeFragment extends Fragment implements SelectVisitTypeViewModel.Contract {

    private FragmentSelectVisitTypeBinding binding;
    private SelectVisitTypeViewModel viewModel;
    private Contract contract;
    public SelectVisitTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_visit_type, container, false);
        viewModel = new SelectVisitTypeViewModel(this);
        viewModel.setArguments(getArguments());
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void removeBackgrounds() {
        binding.frequent.setBackground(null);
        binding.sporadic.setBackground(null);
        binding.scheduled.setBackground(null);
    }

    @Override
    public void setBackgroundTo(View v) {
        v.setBackground(getResources().getDrawable(R.drawable.primary_rectangle));
    }

    @Override
    public void setBackgroundTo(String type) {
        switch (type){
            case "SPORADIC":
                setBackgroundTo(binding.sporadic);
                break;
            case "SCHEDULED":
                setBackgroundTo(binding.scheduled);
                break;
            case "FREQUENT":
                setBackgroundTo(binding.frequent);
                break;
        }
    }

    @Override
    public void next() {
        if (this.contract!= null)
            contract.onSelectVisitType(viewModel.getSelected());
    }

    @Override
    public void setError(String err) {
        Pnotify.makeText(getActivity(),err, Toast.LENGTH_SHORT,Pnotify.ERROR).show();
    }


    public interface Contract {
        void onSelectVisitType(String type);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            contract = (Contract ) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MyInterface ");
        }
    }
}
