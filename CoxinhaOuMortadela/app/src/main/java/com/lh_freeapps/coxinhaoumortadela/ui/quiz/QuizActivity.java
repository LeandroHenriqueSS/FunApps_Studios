package com.lh_freeapps.coxinhaoumortadela.ui.quiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lh_freeapps.coxinhaoumortadela.R;
import com.lh_freeapps.coxinhaoumortadela.di.component.DaggerActivityComponent;
import com.lh_freeapps.coxinhaoumortadela.di.module.ActivityModule;
import com.lh_freeapps.coxinhaoumortadela.util.HighlighterOnTouchListener;
import com.lh_freeapps.coxinhaoumortadela.util.TextSizeUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class QuizActivity extends Activity implements QuizContract.View {

    private QuizContract.Presenter presenter;

    @BindView(R.id.quizCurrentSentence) TextView tvSentenceNumber;
    @BindView(R.id.quizText)            TextView tvSentence;

    @BindView(R.id.bt_no)  ImageButton ibNo;
    @BindView(R.id.bt_yes) ImageButton ibYes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        // set text size
        int textSize = TextSizeUtility.getRecommendedTextSize(this);
        tvSentence      .setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tvSentenceNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize - 4);

        // set ImageButton highlighter on touch
        ibNo .setOnTouchListener(HighlighterOnTouchListener.getInstance());
        ibYes.setOnTouchListener(HighlighterOnTouchListener.getInstance());

        // inject mvp-presenter
        presenter = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build()
                .getQuizPresenter();

    }

    @Override
    public void updateSentence(int currentSentenceNumber, int maxSentenceNumber, String sentence) {
        tvSentenceNumber.setText( currentSentenceNumber + " / " + maxSentenceNumber);
        tvSentence.setText(sentence);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.cleanData();
    }

    @OnClick(R.id.bt_yes)
    public void yesButton(View view) {
        presenter.onClickYesButton();
    }


    @OnClick(R.id.bt_no)
    public void noButton(View view) {
        presenter.onClickNoButton();
    }
}
