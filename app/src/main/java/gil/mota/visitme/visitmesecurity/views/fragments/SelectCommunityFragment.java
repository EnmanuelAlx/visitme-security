package gil.mota.visitme.visitmesecurity.views.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.FragmentSelectCommunityBinding;
import gil.mota.visitme.visitmesecurity.models.Community;
import gil.mota.visitme.visitmesecurity.utils.Pnotify;
import gil.mota.visitme.visitmesecurity.viewModels.SelectCommunityViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectCommunityFragment extends Fragment implements SelectCommunityViewModel.Contract {

    private FragmentSelectCommunityBinding binding;
    private SelectCommunityViewModel viewModel;
    private Contract contract;
    private ArrayAdapter<Community> adapter;
    public SelectCommunityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_community, container, false);
        viewModel = new SelectCommunityViewModel(this);
        binding.setViewModel(viewModel);
        adapter = new ArrayAdapter<Community>(getActivity(), android.R.layout.simple_spinner_item,viewModel.getCommunities());
        binding.spinner.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onSelectCommunity() {
        contract.onSelectCommunity(viewModel.getCommunity());
    }

    @Override
    public void setError(String err) {
        Pnotify.makeText(getActivity(),err, Toast.LENGTH_SHORT,Pnotify.ERROR).show();
    }

    @Override
    public Community getSelected() {
        return (Community) binding.spinner.getSelectedItem();
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

    public interface Contract
    {
        void onSelectCommunity(Community community);
    }
}
