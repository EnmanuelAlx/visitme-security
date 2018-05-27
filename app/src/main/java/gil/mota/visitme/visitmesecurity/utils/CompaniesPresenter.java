package gil.mota.visitme.visitmesecurity.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.otaliastudios.autocomplete.RecyclerViewPresenter;

import java.util.ArrayList;
import java.util.List;

import gil.mota.visitme.visitmesecurity.models.User;
import gil.mota.visitme.visitmesecurity.useCases.GetCompanies;
import gil.mota.visitme.visitmesecurity.useCases.UseCase;
import gil.mota.visitme.visitmesecurity.viewModels.ItemUserViewModel;
import gil.mota.visitme.visitmesecurity.views.adapters.UserAdapter;

public class CompaniesPresenter extends RecyclerViewPresenter implements UseCase.Result {

    private UserAdapter adapter;
    private List<User> companies;
    private GetCompanies get;
    public CompaniesPresenter(Context context, ItemUserViewModel.Contract contract) {
        super(context);
        adapter = new UserAdapter(contract);
        companies = new ArrayList<>();
        adapter.setList(companies);
        get = new GetCompanies(this, companies);
    }

    @Override
    protected RecyclerView.Adapter instantiateAdapter() {
        return adapter;
    }

    @Override
    protected void onQuery(@Nullable CharSequence query) {
        companies.clear();
        get.setQuery(query != null ? query.toString() : "");
        get.run();
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess() {
        Log.i("QUERY","SHOW ITEMS:"+ companies.toString());
        adapter.notifyDataSetChanged();
    }
}
