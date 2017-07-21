package com.lh_freeapps.coxinhaoumortadela.ui.statistics;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lh_freeapps.coxinhaoumortadela.R;
import com.lh_freeapps.coxinhaoumortadela.data.model.GlobalData;
import com.lh_freeapps.coxinhaoumortadela.di.component.DaggerActivityComponent;
import com.lh_freeapps.coxinhaoumortadela.di.module.ActivityModule;
import com.lh_freeapps.coxinhaoumortadela.util.TextSizeUtility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leandro on 04/05/2017.
 */

public class StatisticsActivity extends AppCompatActivity implements StatisticsContract.View {

    private StatisticsContract.Presenter presenter;

    private Fragment pieChartFragment;
    private Fragment barChartFragment;

    private BottomSheetBehavior bottomSheetBehavior;

    @BindView(R.id.progress_spinner) ProgressBar spinner;

    @BindView(R.id.tv_info) TextView tvInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);


        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        bottomSheetBehavior.setPeekHeight(0); // hide sheet
        tvInfo.setTextSize(TextSizeUtility.getRecommendedTextSize(this) - 9);


        // inject mvp-presenter
        presenter = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build()
                .getStatisticsPresenter();


        // if the fragments were not set yet, create them from server/database
        if (getSupportFragmentManager().getFragments().size() == 0) {
            spinner.setVisibility(View.VISIBLE);
            presenter.callGetGlobalData();

        // otherwise, no need to recreate them
        } else {
            pieChartFragment = getSupportFragmentManager().getFragments().get(0);
            barChartFragment = getSupportFragmentManager().getFragments().get(1);

            setupViewPager(pieChartFragment, barChartFragment);
        }

    }


    @Override
    public void onGetGlobalDataSucceed(GlobalData globalData) {
        spinner.setVisibility(View.GONE);

        pieChartFragment = new PieChartFragment().setGlobalData(globalData);
        barChartFragment = new BarChartFragment().setGlobalData(globalData);

        setupViewPager(pieChartFragment, barChartFragment);
    }


    @Override
    public void onGetGlobalDataFailed(String error) {
        spinner.setVisibility(View.GONE);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        onBackPressed();
    }


    private void setupViewPager(Fragment pieChartFragment, Fragment barChartFragment) {

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tlCharts  = (TabLayout) findViewById(R.id.tl_charts);
        tlCharts.setupWithViewPager(viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(pieChartFragment, getResources().getString(R.string.general));
        adapter.addFragment(barChartFragment, getResources().getString(R.string.specific));
        viewPager.setAdapter(adapter);
    }


    /** button on BarChartFragment*/
    public void onClickShowInfo(View view) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


    @Override
    public void onBackPressed() {

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }

        super.onBackPressed();
    }


    class ViewPagerAdapter extends FragmentPagerAdapter implements Serializable {
        private final List<Fragment> mFragmentList      = new ArrayList<>();
        private final List<String>   mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) { super(manager); }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) { return mFragmentTitleList.get(position); }
    }

}
