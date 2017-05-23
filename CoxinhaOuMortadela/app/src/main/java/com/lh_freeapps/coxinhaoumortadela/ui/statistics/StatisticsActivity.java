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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leandro on 04/05/2017.
 */

public class StatisticsActivity extends AppCompatActivity implements StatisticsContract.View {

    private StatisticsContract.Presenter presenter;

    private BottomSheetBehavior bottomSheetBehavior;

    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        bottomSheetBehavior.setPeekHeight(0); // hide sheet

        ((TextView)findViewById(R.id.tv_info)).setTextSize(TextSizeUtility.getRecommendedTextSize(this) - 9);

        // inject mvp-presenter
        presenter = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build()
                .getStatisticsPresenter();


        // callback to get data from either server or database
        presenter.callGetGlobalData();

        // show progress bar (spinner)
        spinner = (ProgressBar) findViewById(R.id.progress_spinner);
        spinner.setVisibility(View.VISIBLE);
    }


    @Override
    public void onGetGlobalDataSucceed(GlobalData globalData) {
        spinner.setVisibility(View.GONE);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager, globalData);

        TabLayout tlCharts = (TabLayout) findViewById(R.id.tl_charts);
        tlCharts.setupWithViewPager(viewPager);
    }

    @Override
    public void onGetGlobalDataFailed(String error) {
        spinner.setVisibility(View.GONE);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        onBackPressed();
    }


    private void setupViewPager(ViewPager viewPager, GlobalData globalData) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PieChartFragment().setGlobalData(globalData), getResources().getString(R.string.general));
        adapter.addFragment(new BarChartFragment().setGlobalData(globalData), getResources().getString(R.string.specific));
        viewPager.setAdapter(adapter);
    }


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


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
