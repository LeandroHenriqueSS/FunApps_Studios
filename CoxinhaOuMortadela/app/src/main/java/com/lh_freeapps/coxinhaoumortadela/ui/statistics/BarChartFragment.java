package com.lh_freeapps.coxinhaoumortadela.ui.statistics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.lh_freeapps.coxinhaoumortadela.CoxinhaOuMortadela;
import com.lh_freeapps.coxinhaoumortadela.R;
import com.lh_freeapps.coxinhaoumortadela.data.model.DaoSession;
import com.lh_freeapps.coxinhaoumortadela.data.model.GlobalData;
import com.lh_freeapps.coxinhaoumortadela.data.model.User;
import com.lh_freeapps.coxinhaoumortadela.data.model.UserDao;
import com.lh_freeapps.coxinhaoumortadela.util.TextSizeUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BarChartFragment extends Fragment {

    private HorizontalBarChart chart;

    private int coxinhaPercent;
    private int superCoxinhaPercent;
    private int mortadelaPercent;
    private int superMortadelaPercent;
    private int coxinhaDeMortadelaPercent;
    private int totalUsers;


    public BarChartFragment() {
        // Required empty public constructor
    }


    /** Complement to the constructor */
    public Fragment setGlobalData(GlobalData globalData) {
        totalUsers                = globalData.getTotalUsers();
        coxinhaPercent            = Math.round(100*globalData.getNumCoxinha()            / (float) totalUsers);
        superCoxinhaPercent       = Math.round(100*globalData.getNumSuperCoxinha()       / (float) totalUsers);
        mortadelaPercent          = Math.round(100*globalData.getNumMortadela()          / (float) totalUsers);
        superMortadelaPercent     = Math.round(100*globalData.getNumSuperMortadela()     / (float) totalUsers);
        coxinhaDeMortadelaPercent = Math.round(100*globalData.getNumCoxinhaDeMortadela() / (float) totalUsers);

        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_bar_chart, container, false);

        chart = (HorizontalBarChart) inflate.findViewById(R.id.chart);
        chart.getDescription().setText(getContext().getString(R.string.verified_users) + totalUsers);


        String[] labels = new String[] {
                "Coxinha de Mortadela (" + coxinhaDeMortadelaPercent + "%)",
                "Super Mortadela ("      + superMortadelaPercent     + "%)",
                "Mortadela ("            + mortadelaPercent          + "%)",
                "Super Coxinha ("        + superCoxinhaPercent       + "%)",
                "Coxinha ("              + coxinhaPercent            + "%)"
        };


        // get my type
        DaoSession daoSession = ((CoxinhaOuMortadela) getActivity().getApplicationContext()).getDaoSession();
        UserDao UserDao = daoSession.getUserDao();
        List<User> users = UserDao.loadAll();


        // add '☆' before the label of my type
        if ( ! users.isEmpty() ) {
            int type = users.get(0).getResultType();
            labels[5-type] = "☆ " + labels[5-type];
        }


        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        chart.getXAxis().setTextSize(TextSizeUtility.getRecommendedTextSize(getContext()) - 12);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        chart.setFitBars(true);

        // set data
        setData();
        chart.setDrawBarShadow(true);

        // disable unnecessary functions
        chart.getLegend().setEnabled(false);
        chart.setHighlightFullBarEnabled(false);
        chart.setHighlightPerTapEnabled(false);
        chart.setHighlightPerDragEnabled(false);
        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);


        return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();
        chart.animateY(3000);
    }

    private void setData() {

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        // Coxinha de Mortadela
        ArrayList<BarEntry> entryList0 = new ArrayList<>();
        entryList0.add(new BarEntry(0, coxinhaDeMortadelaPercent));
        BarDataSet set0 = new BarDataSet(entryList0, "Coxinha de Mortala");
        set0.setColor(ContextCompat.getColor(getActivity(), R.color.coxinha_de_mortadela));
        dataSets.add(set0);

        // Super Mortadela
        ArrayList<BarEntry> entryList1 = new ArrayList<>();
        entryList1.add(new BarEntry(1, superMortadelaPercent));
        BarDataSet set1 = new BarDataSet(entryList1, "Super Mortadela");
        set1.setColor(ContextCompat.getColor(getActivity(), R.color.super_mortadela));
        dataSets.add(set1);

        // Mortadela
        ArrayList<BarEntry> entryList2 = new ArrayList<>();
        entryList2.add(new BarEntry(2, mortadelaPercent));
        BarDataSet set2 = new BarDataSet(entryList2, "Mortadela");
        set2.setColor(ContextCompat.getColor(getActivity(), R.color.mortadela));
        dataSets.add(set2);

        // Super Coxinha
        ArrayList<BarEntry> entryList3 = new ArrayList<>();
        entryList3.add(new BarEntry(3, superCoxinhaPercent));
        BarDataSet set3 = new BarDataSet(entryList3, "Super Coxinha");
        set3.setColor(ContextCompat.getColor(getActivity(), R.color.super_coxinha));
        dataSets.add(set3);

        // Coxinha
        ArrayList<BarEntry> entryList4 = new ArrayList<>();
        entryList4.add(new BarEntry(4, coxinhaPercent));
        BarDataSet set4 = new BarDataSet(entryList4, "Coxinha");
        set4.setColor(ContextCompat.getColor(getActivity(), R.color.coxinha));
        dataSets.add(set4);

        // set data
        BarData data = new BarData(dataSets);
        data.setValueFormatter(new PercentFormatter());
        chart.setData(data);
    }

}
