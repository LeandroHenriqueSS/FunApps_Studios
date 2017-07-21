package com.lh_freeapps.coxinhaoumortadela.ui.result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lh_freeapps.coxinhaoumortadela.R;
import com.lh_freeapps.coxinhaoumortadela.ui.first_screen.FirstScreenActivity;
import com.lh_freeapps.coxinhaoumortadela.util.HighlighterOnTouchListener;
import com.lh_freeapps.coxinhaoumortadela.util.TextSizeUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultActivity extends Activity implements ResultContract.View {

    private ResultContract.Presenter presenter;

    @BindView(R.id.tvPercentage)  TextView tvPercentage;
    @BindView(R.id.tvDescription) TextView tvDescription;

    @BindView(R.id.result_img)    ImageView ivResultImg;
    @BindView(R.id.result_line)   ImageView ivResultLine;

    @BindView(R.id.ll_result_bar) LinearLayout llResultBar;
    @BindView(R.id.ll_result)     LinearLayout llResult;

    @BindView(R.id.bt_back)       ImageButton ibBack;
    @BindView(R.id.bt_share)      ImageButton ibShare;
    @BindView(R.id.bt_facebook)   ImageButton ibFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        // get extras
        int coxinhaLevel   = getIntent().getIntExtra("COXINHA_LEVEL"  , -1);
        int mortadelaLavel = getIntent().getIntExtra("MORTADELA_LEVEL", -1);
        boolean isOnCould  = getIntent().getBooleanExtra("IS_ON_CLOUD", false);

        // set ImageButton listeners
        ibBack    .setOnTouchListener(HighlighterOnTouchListener.getInstance());
        ibShare   .setOnTouchListener(HighlighterOnTouchListener.getInstance());
        ibFacebook.setOnTouchListener(HighlighterOnTouchListener.getInstance());

        // set text size
        int textSize = TextSizeUtility.getRecommendedTextSize(this);
        tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize - 4);
        tvPercentage .setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize + 8);

        // get mvp-presenter
        presenter = new ResultPresenter(this);
        presenter.setUserParams(coxinhaLevel, mortadelaLavel, isOnCould);

        // set data dynamically
        ivResultImg.setImageResource(presenter.getImageID());
        tvDescription.setText(presenter.getDescription());
        tvPercentage .setText(presenter.getPercentage());
    }


    @Override
    protected void onResume() {
        super.onResume();

        tvPercentage .setVisibility(View.GONE);
        presenter.showResult(llResultBar, ivResultLine, getWindowManager());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, FirstScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public void showPercentage() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1){
            tvPercentage.setAlpha(0f);
            tvPercentage.setVisibility(View.VISIBLE);
            tvPercentage.animate().alpha(1f).setDuration(600);
        } else  {
            tvPercentage.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.bt_back)
    public void backHome(View view) {
        onBackPressed();
    }

    @OnClick({R.id.bt_share, R.id.bt_facebook})
    public void shareResult(View view) {
        presenter.shareResult(llResult, view);
    }


}
