package com.todmonster.funappsstudios.ui.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.todmonster.funappsstudios.R;
import com.todmonster.funappsstudios.model.scenario.Scenario;
import com.todmonster.funappsstudios.model.scenario.TodMonster;
import com.todmonster.funappsstudios.model.user.User;
import com.todmonster.funappsstudios.ui.settings.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.todmonster.funappsstudios.ui.settings.Preferences.BACKGROUND_SOUND;
import static com.todmonster.funappsstudios.ui.settings.Preferences.SOUND_EFFECTS;
import static com.todmonster.funappsstudios.ui.settings.Preferences.VIBRATION;


public class GameActivity extends AppCompatActivity implements View.OnTouchListener {

    @BindView(R.id.monsters_view) GameGridView gameGridView;

    @BindView(R.id.time_penality) TextView tvTimePenalty;
    @BindView(R.id.time)          TextView tvCurrentTime;
    @BindView(R.id.currentLevel)  TextView tvLvl;

    @BindView(R.id.heart_1) ImageView heart_1;
    @BindView(R.id.heart_2) ImageView heart_2;
    @BindView(R.id.heart_3) ImageView heart_3;


    private Scenario scenario;

    private User user = new User();

    private List<TodMonster> todMonsters = new ArrayList<>();

    private Timer timer;

    private Handler handler = new Handler();

    private SharedPreferences pref;

    // waiting the user answer a message
    private boolean waitingMessage;


    //MediaPlayer for background music and sound effects
    private MediaPlayer backgroundSound;
    private MediaPlayer lvlAccomplishedSound;
    private MediaPlayer hitSound;
    private MediaPlayer missSound;



    @Override
    public void onCreate(final Bundle state) {
        super.onCreate(state);

        pref = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        if (pref.getBoolean(Preferences.DARK_THEME.name(), false))
            setTheme(R.style.FullscreenDarkTheme);

        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        boolean smallScreen = pref.getBoolean(Preferences.SMALL_SCREENS.name(), false);
        scenario = new Scenario(smallScreen);

        // GameGrid should be in Scenario
        if (smallScreen)
            ((ViewGroup.MarginLayoutParams) gameGridView.getLayoutParams()).setMargins(10, 10, 10, 10);

        if (pref.getBoolean(Preferences.DARK_THEME.name(), false))
            gameGridView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBackgroundDark));


        hitSound = MediaPlayer.create(this, R.raw.splat);
        missSound = MediaPlayer.create(this, R.raw.miss);
        lvlAccomplishedSound = MediaPlayer.create(this, R.raw.success);
        backgroundSound = MediaPlayer.create(this, R.raw.background);
        backgroundSound.setLooping(true);

        // find the dots view
        gameGridView.setTodMonsters(todMonsters);
        gameGridView.setOnTouchListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();

        if (pref.getBoolean(BACKGROUND_SOUND.name(), true))
            backgroundSound.start();

        // move monsters every one second
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> moveMonsters());
                runOnUiThread(() -> updateTime());
            }
        }, 0, 1000);


        populateMonsters();

    }


    @Override
    public boolean onTouch(final View v, final MotionEvent evt) {

        // check for ACTION_DOWN only
        if (evt.getAction() != MotionEvent.ACTION_DOWN)
            return false;


        for (TodMonster tm : todMonsters) {

            // check if the user hit the correct line
            boolean lineMatched = evt.getX() > tm.getLocation().getLeft() &&
                    evt.getX() < tm.getLocation().getRight();

            // check if the user hit the correct column
            boolean columnMatched = evt.getY() > tm.getLocation().getTop() &&
                    evt.getY() < tm.getLocation().getBottom();

            // if the user has hit the cell with a vulnerable monster, kill it
            if (lineMatched && columnMatched && tm.isVulnerable()) {

                if (pref.getBoolean(SOUND_EFFECTS.name(), true)) {
                    try {
                        hitSound.reset();
                        AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.hit_2);
                        hitSound.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                        hitSound.prepare();
                        hitSound.start();
                    } catch (Exception e) { e.printStackTrace(); }
                }

                if (pref.getBoolean(VIBRATION.name(), true))
                    ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(80);


//                tm.setAlive(false);
                todMonsters.remove(tm); //TODO: teste
                scenario.killMonster();
                gameGridView.invalidate();

                // No more monsters? Ask if the user wants to proceed to the next lvl
                if (scenario.getNumMonstersAlive() == 0) {

                    if (pref.getBoolean(SOUND_EFFECTS.name(), true))
                        lvlAccomplishedSound.start();

                    waitingMessage = true;

                    new AlertDialog.Builder(this)
                            .setTitle("Next Level")
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog, which) -> {

                                user.setScore(user.getScore() + scenario.getNumMonsterKilled());
                                scenario.nextLevel();
                                populateMonsters();
                                gameGridView.invalidate();
                                //new RecordDB(getApplicationContext()).insertRecord();

                                updateLevel();
                                updateTime();
                                waitingMessage = false;

                            }).show();

                }

                break;
            }

            // if the user has hit the cell with a non vulnerable monster, discount time
            if (lineMatched && columnMatched && tm.isVulnerable() == false) {

                if (pref.getBoolean(SOUND_EFFECTS.name(), true)) {
                    try {
                        missSound.reset();
                        AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.miss);
                        missSound.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                        missSound.prepare();
                        missSound.start();
                    } catch (Exception e) { e.printStackTrace(); }
                }


                gameGridView.setPenalty(true, tm);
                gameGridView.invalidate();

                updateTime();
                tvTimePenalty.setVisibility(View.VISIBLE);
                handler.postDelayed(() -> tvTimePenalty.setVisibility(View.GONE), 1000);
                break;
            }

        }

        return false;
    }

    private void askForContinue() {
        waitingMessage = true;

        new AlertDialog.Builder(gameGridView.getContext())
                .setMessage("Do you wanna try again?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                    waitingMessage = false;
                    scenario.resetValues();
                    todMonsters.clear();
                    runOnUiThread(() -> populateMonsters());
                })
                .setNegativeButton("No",(dialog, which) -> {
                    waitingMessage = false;
                    alertGameOver();
                })
                .show();

    }

    private void alertGameOver() {
        waitingMessage = true;

        new AlertDialog.Builder(gameGridView.getContext())
                .setTitle("Game Over")
                .setMessage("Final score: " + user.getScore())
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                    waitingMessage = false;
                    finish();
                })
                .show();

    }


    @Override
    public void onPause() {
        super.onPause();
        backgroundSound.pause();
        timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // release medias
        hitSound.release();
        missSound.release();
        lvlAccomplishedSound.release();
        backgroundSound.release();
    }

    private void moveMonsters() {
        Random rand = new Random();
        TodMonster newTod = new TodMonster();

        // amount of attempts a monster tried a new location
        int attempt = 0;

        for (int i = 0; i < todMonsters.size(); i++) {
            TodMonster oldTod = todMonsters.get(i);

            // the lowest and highest line a monster can move
            int lowestLine  = (oldTod.getLine() == 0) ? oldTod.getLine() : oldTod.getLine() - 1;
            int highestLine = (oldTod.getLine() == GameGridView.NUM_VERTICAL_CELLS - 1) ? oldTod.getLine() : oldTod.getLine() + 1;

            // the lowest and highest column a monster can move
            int lowestColumn  = (oldTod.getColumn() == 0) ? oldTod.getColumn() : oldTod.getColumn() - 1;
            int highestColumn = (oldTod.getColumn() == (GameGridView.NUM_HORIZONTAL_CELLS - 1)) ? oldTod.getColumn() : oldTod.getColumn() + 1;

            newTod.setLine  (rand.nextInt(highestLine   - lowestLine   + 1) + lowestLine  );
            newTod.setColumn(rand.nextInt(highestColumn - lowestColumn + 1) + lowestColumn);


            // If achieved the maximum number of attempts, stay in the same place
            if (attempt == 5) {
                attempt = 0;
                continue;
            }

            // If already exist a monster in my next location, try to find another location again
            if (todMonsters.contains(newTod)) {
                attempt++;
                i--;
                continue;
            }

            attempt = 0;
            todMonsters.get(i).setLine(newTod.getLine());
            todMonsters.get(i).setColumn(newTod.getColumn());
            todMonsters.get(i).setVulnerable(new Random().nextBoolean());
        }

        // draw the monster
        gameGridView.invalidate();
    }

    /** update the current time and the views associated to it */
    private void updateTime() {

        if (waitingMessage || scenario.getRemainingTime() == 0)
            return;

        scenario.decRemainingTime();

        String curTime = scenario.getRemainingTime() < 10 ? "0" : "";
        curTime       += scenario.getRemainingTime();
        tvCurrentTime.setText(curTime);


        if (scenario.getRemainingTime() > 10) {
            tvCurrentTime.setTextColor(Color.WHITE);
        } else if (scenario.getRemainingTime() <= 5) {
            tvCurrentTime.setTextColor(Color.RED);
        } else {
            tvCurrentTime.setTextColor(Color.rgb(255,165,0)); // orange
        }

        // If the current time is zero, ask if the user want to try again
        if (scenario.getRemainingTime() == 0) {
            user.setLives(user.getLives() - 1);
            user.setScore(user.getScore() + scenario.getNumMonsterKilled());

            if (user.getLives() == 0) {
                heart_1.setImageResource(R.drawable.no_live);

//                RecordDB recordDB = new RecordDB(getApplicationContext());
//                recordDB.insertRecord(new Record(user.getScore()));

                alertGameOver();
                return;
            }

            if (user.getLives() == 1)
                heart_2.setImageResource(R.drawable.no_live);

            if (user.getLives() == 2)
                heart_3.setImageResource(R.drawable.no_live);


            askForContinue();

        }

    }


    /** update the TextView that represents the current scenario */
    private void updateLevel() {
        String curLvl = scenario.getCurrentLevel() < 10 ? "0" : "";
        curLvl       += scenario.getCurrentLevel();
        tvLvl.setText(curLvl);
    }


    // TODO: it's important that most heavy logic be calculated while the Dialog is shown
    /** populate monsters in the view */
    private void populateMonsters() {
        Random rand    = new Random();

        for (int i = 0; i < scenario.getTotalMonsters(); i++) {

            TodMonster tod = new TodMonster();
            tod.setLine  (rand.nextInt(GameGridView.NUM_VERTICAL_CELLS  ));
            tod.setColumn(rand.nextInt(GameGridView.NUM_HORIZONTAL_CELLS));

            // If there is a monster in this location, try again
            if (todMonsters.contains(tod))
                i--;
            else
                todMonsters.add(tod);

        }
    }


}
