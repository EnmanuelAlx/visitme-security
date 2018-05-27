package gil.mota.visitme.visitmesecurity.views.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Observable;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.databinding.ActivityLoginBinding;
import gil.mota.visitme.visitmesecurity.databinding.ActivityMainBinding;
import gil.mota.visitme.visitmesecurity.models.Visit;

import gil.mota.visitme.visitmesecurity.viewModels.MainViewModel;
import gil.mota.visitme.visitmesecurity.views.adapters.PageAdapter;

public class MainActivity extends BindeableActivity implements TabLayout.OnTabSelectedListener,
                                        ViewPager.OnPageChangeListener, MainViewModel.Contract {
    public static final int UPDATE_VISITS = 777;
    private MainViewModel viewModel;
    private ViewPager pager;
    private TabLayout tabLayout;
    private TabLayout.Tab lastTab;
    private PageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setupObserver(viewModel);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof MainViewModel) {
            MainViewModel viewModel = (MainViewModel) observable;
        }
    }

    @Override
    public void initDataBinding() {
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainViewModel(this,this);
        binding.setViewModel(viewModel);
        initFragments(binding);
    }

    private void initFragments(ActivityMainBinding binding) {

        tabLayout = binding.tabs;
        pager = binding.content;
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.hashtag));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.edit_profile));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.off));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount() - 2);


        //Adding adapter to pager
        lastTab = tabLayout.getTabAt(0);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 3) {
            viewModel.signOut();
            lastTab.select();
        } else if(tab.getPosition() == 2) {
            lastTab.select();
            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            i.putExtra("edit", true);
            MainActivity.this.startActivity(i);
        }
        else {
            pager.setCurrentItem(tab.getPosition());
            lastTab = tab;
        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        tab.select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void createVisit(){
        Intent i = new Intent(this, CreateVisitActivity.class);
        startActivityForResult(i,UPDATE_VISITS);
    }




    @Override
    public void updateAlerts() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Visit createVisitFromData(Intent data) {
        return data.getParcelableExtra("visit");
    }

    @Override
    public void onBackPressed() {
        viewModel.signOut();
    }
}
