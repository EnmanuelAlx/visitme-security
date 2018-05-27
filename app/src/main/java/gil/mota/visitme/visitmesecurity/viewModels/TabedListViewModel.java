package gil.mota.visitme.visitmesecurity.viewModels;

import java.util.List;
import java.util.Observable;

import gil.mota.visitme.visitmesecurity.useCases.UseCase;

/**
 * Created by mota on 19/4/2018.
 */

public abstract class TabedListViewModel<T> extends Observable implements UseCase.Result {
    protected Interactor interactor;
    protected int currentTab;

    public TabedListViewModel(Interactor interactor) {
        this.interactor = interactor;
        currentTab = 0;
        init();
        runGetByCurrentTab();
    }

    public void refresh() {
        refreshByCurrentTab();
        runGetByCurrentTab();
    }

    public void changeTab(int position) {
        currentTab = position;
        List<T> currentList = getListByCurrentTab();
        if (currentList.size() > 0)
            changeListByCurrentTab();
        else
            runGetByCurrentTab();
    }

    private void changeListByCurrentTab() {
        List<T> toChange = getListByCurrentTab();
        interactor.changeList(toChange);
    }

    protected abstract List<T> getListByCurrentTab();

    protected abstract void runGetByCurrentTab();

    protected abstract void refreshByCurrentTab();

    protected abstract void init();

    @Override
    public void onError(String error) {
        interactor.showError(error);
    }

    @Override
    public void onSuccess() {
        changeListByCurrentTab();
    }

    public interface Interactor {
        void changeList(List visit);
        void loading(boolean loading);
        void showError(String error);
    }
}
