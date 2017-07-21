package com.lh_freeapps.coxinhaoumortadela.ui.quiz;

import android.content.Context;
import android.content.Intent;

import com.lh_freeapps.coxinhaoumortadela.data.model.Sentences;
import com.lh_freeapps.coxinhaoumortadela.ui.base.BaseView;
import com.lh_freeapps.coxinhaoumortadela.ui.result.ResultActivity;

/**
 * Created by Leandro on 28/04/2017.
 */

public class QuizPresenter implements QuizContract.Presenter {

    private QuizContract.View quizView;
    private Context context;

    private int currentSentenceNumber;
    private int maxSentenceNumber;

    private int numCoxinhas;
    private int numMortadelas;


    private static QuizPresenter presenter = new QuizPresenter();


    public static QuizPresenter getInstance(BaseView view) {
        presenter.quizView = (QuizContract.View) view;
        presenter.context  = (Context) view;

        // update view
        presenter.quizView.updateSentence(
                presenter.currentSentenceNumber+1,
                presenter.maxSentenceNumber,
                Sentences.getSentence(presenter.currentSentenceNumber));

        return presenter;
    }


    private QuizPresenter(){
        maxSentenceNumber = Sentences.getMaxSentenceNumber();
    }


    private void nextSentence() {
        currentSentenceNumber++;

        if (currentSentenceNumber == maxSentenceNumber)
            startResultActivity();

        else
            quizView.updateSentence(currentSentenceNumber+1, maxSentenceNumber, Sentences.getSentence(currentSentenceNumber));

    }


    @Override
    public void onClickYesButton() {
        // avoid multiple clicks bug
        if (currentSentenceNumber == maxSentenceNumber)
            return;

        // YES pressed for a coxinha sentence
        if (Sentences.isCoxinha(currentSentenceNumber))
            numCoxinhas++;
        else
            numMortadelas++;


        nextSentence();
    }


    @Override
    public void onClickNoButton() {
        // avoid multiple clicks bug
        if (currentSentenceNumber == maxSentenceNumber)
            return;

        // NO pressed for a coxinha sentence
        if (Sentences.isCoxinha(currentSentenceNumber))
            numMortadelas++;
        else
            numCoxinhas++;


        nextSentence();
    }


    @Override
    /** Restart quiz values */
    public void cleanData() {
        presenter = new QuizPresenter();
    }


    private void startResultActivity() {
        // calculate levels
        int coxinhaLevel   = (int) Math.ceil(100*numCoxinhas   / (float) maxSentenceNumber);
        int mortadelaLevel = (int) Math.ceil(100*numMortadelas / (float) maxSentenceNumber);

        Intent intent = new Intent(context, ResultActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("COXINHA_LEVEL"  , coxinhaLevel  );
        intent.putExtra("MORTADELA_LEVEL", mortadelaLevel);
        intent.putExtra("ON_THE_CLOUD"   , false);

        cleanData();
        context.startActivity(intent);
    }


}
