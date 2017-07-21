package com.lh_freeapps.coxinhaoumortadela.ui.statistics;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.lh_freeapps.coxinhaoumortadela.R;
import com.lh_freeapps.coxinhaoumortadela.data.model.GlobalData;
import com.lh_freeapps.coxinhaoumortadela.util.TextSizeUtility;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class PieChartFragment extends Fragment {

    private Typeface tfLight;

    private PieChart chart;

    private int coxinhaPercentage;
    private int mortadelaPercentage;


    private String TAG = "mTAG " + getClass().getSimpleName();

    public PieChartFragment() {
        // Required empty public constructor
        Log.d(TAG, "PieChartFragment()");
    }


    /** Complement to the constructor */
    public Fragment setGlobalData(GlobalData globalData) {

        Log.d(TAG, "setGlobalData: " + globalData.getNumCoxinha());

        coxinhaPercentage = Math.round(100*(globalData.getNumCoxinha() + globalData.getNumSuperCoxinha()) /
                (float) (globalData.getTotalUsers() - globalData.getNumCoxinhaDeMortadela()));

        mortadelaPercentage = Math.round(100*(globalData.getNumMortadela() + globalData.getNumSuperMortadela()) /
                (float) (globalData.getTotalUsers() - globalData.getNumCoxinhaDeMortadela()));


        Log.d("TAGG setGlobalData", "" + coxinhaPercentage);


        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        View inflate = inflater.inflate(R.layout.fragment_pie_chart, container, false);

//        if (savedInstanceState  != null) {
//            coxinhaPercentage   = savedInstanceState.getInt("COXINHA");
//            mortadelaPercentage = savedInstanceState.getInt("MORTADELA");
//        }

        Log.d(TAG, "onCreateView: " + coxinhaPercentage);


        tfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");


        // set elements
        chart = (PieChart) inflate.findViewById(R.id.chart);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setCenterTextTypeface(tfLight);
        chart.setCenterText(generateCenterSpannableText());
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        // enable extras and items
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setDrawHoleEnabled(true);
        chart.setDrawCenterText(true);

        //set data
        setData();

        // set legend
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelTypeface(tfLight);
        chart.setEntryLabelTextSize(TextSizeUtility.getRecommendedTextSize(getContext()) - 10);

        return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        chart.spin(2300, 0f, 360f, Easing.EasingOption.EaseInOutQuad);
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Coxinha\nou\nMortadela\n");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.pie_yellow)), 0, 7, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, 10, 0);
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.pie_red)), 10, s.length(), 0);
        return s;
    }

    private void setData() {

        Log.d(TAG, "setData: " + coxinhaPercentage);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(coxinhaPercentage  , "Coxinha"  , null));
        entries.add(new PieEntry(mortadelaPercentage, "Mortadela", null));

        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(getActivity(), R.color.pie_yellow));
        colors.add(ContextCompat.getColor(getActivity(), R.color.pie_red));
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatterAdapter());
        data.setValueTextSize(TextSizeUtility.getRecommendedTextSize(getContext()) - 10);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);

        chart.invalidate();
        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        chart.spin(2300, 0f, 360f, Easing.EasingOption.EaseInOutQuad);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("COXINHA"  , coxinhaPercentage);
        outState.putInt("MORTADELA", mortadelaPercentage);
    }


    /** Make PieChart shows percentage values */
    private class PercentFormatterAdapter extends PercentFormatter {

        public PercentFormatterAdapter() {
            mFormat = new DecimalFormat("###,###,##0");
        }

        // IValueFormatter
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value) + "%";
        }

        // IAxisValueFormatter
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mFormat.format(value) + "%";
        }

    }


}
