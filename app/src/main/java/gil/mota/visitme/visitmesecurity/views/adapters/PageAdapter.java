package gil.mota.visitme.visitmesecurity.views.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import gil.mota.visitme.visitmesecurity.models.Visit;
import gil.mota.visitme.visitmesecurity.utils.FragmentPager;
import gil.mota.visitme.visitmesecurity.views.fragments.DashboardFragment;
import gil.mota.visitme.visitmesecurity.views.fragments.UnexpectedVisitFragment;
import gil.mota.visitme.visitmesecurity.views.fragments.VisitSuccessFragment;
import gil.mota.visitme.visitmesecurity.views.fragments.VisitsFragment;

/**
 * Created by mota on 15/4/2018.
 */

public class PageAdapter extends FragmentStatePagerAdapter implements FragmentPager {

    //integer to count number of tabs
    private int tabCount;
    private VisitsFragment visits;
    private DashboardFragment alerts;
    private int visitFragmentSelected;
    private VisitSuccessFragment visitsSuccess;
    private UnexpectedVisitFragment unexpectedVisit;

    public PageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        visitFragmentSelected = 0;
        this.tabCount = tabCount;
        initVisitFragments();
        alerts = new DashboardFragment();
    }

    private void initVisitFragments() {
        visits = new VisitsFragment();
        visits.setPager(this);
        visitsSuccess = new VisitSuccessFragment();
        visitsSuccess.setPager(this);
        unexpectedVisit = new UnexpectedVisitFragment();
        unexpectedVisit.setPager(this);
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        Log.i("VISIT FLOW","GET ITEM " + position + " " + visitFragmentSelected);
        switch (position) {
            case 0:
                return getVisitFragmentSelected();
            case 1:
                return alerts;
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    private Fragment getVisitFragmentSelected() {
        switch (visitFragmentSelected)
        {
            case 0:
                return visits;
            case 1:
                return visitsSuccess;
            case 2:
                return unexpectedVisit;
            default:
                return visits;
        }

    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public void changePage(int page) {
        Log.i("VISIT FLOW", "CHANGING PAGE TO " + page);
        this.visitFragmentSelected = page;
        this.notifyDataSetChanged();
    }

    @Override
    public void setArgumentsToCurrentPage(Bundle bundle) {
        Fragment f = this.getVisitFragmentSelected();
        f.setArguments(bundle);
    }



}