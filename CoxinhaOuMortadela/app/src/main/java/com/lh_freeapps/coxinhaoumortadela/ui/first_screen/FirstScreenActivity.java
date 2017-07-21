package com.lh_freeapps.coxinhaoumortadela.ui.first_screen;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import com.lh_freeapps.coxinhaoumortadela.R;
import com.lh_freeapps.coxinhaoumortadela.di.component.DaggerActivityComponent;
import com.lh_freeapps.coxinhaoumortadela.di.module.ActivityModule;
import com.lh_freeapps.coxinhaoumortadela.util.HighlighterOnTouchListener;
import com.lh_freeapps.coxinhaoumortadela.util.TextSizeUtility;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.ButterKnife;

public class FirstScreenActivity extends Activity implements FirstScreenContract.View {

    FirstScreenContract.Presenter presenter;

    @BindView(R.id.bt_start_quiz) Button btStartQuiz;
    @BindView(R.id.bt_my_result)  Button btResult;
    @BindView(R.id.bt_statistics) Button btStatistics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        ButterKnife.bind(this);

        // set ImageButton highlighter on touch
        btStatistics.setOnTouchListener(HighlighterOnTouchListener.getInstance());
        btStartQuiz .setOnTouchListener(HighlighterOnTouchListener.getInstance());
        btResult    .setOnTouchListener(HighlighterOnTouchListener.getInstance());


        // set text size
        int textSize = TextSizeUtility.getRecommendedTextSize(this) -2;
        btStatistics.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        btStartQuiz .setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        btResult    .setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);


        // inject mvp-presenter
        presenter = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build()
                .getFirstScreenPresenter();

    }

    @OnClick(R.id.bt_start_quiz)
    public void showQuiz(View view) {
        presenter.startQuizActivity();
    }

    @OnClick(R.id.bt_my_result)
    public void showResult(View view) {
        presenter.startResultActivity();
    }

    @OnClick(R.id.bt_statistics)
    public void showStatistics(View view) {
        presenter.startStatisticsActivity();
    }


}
