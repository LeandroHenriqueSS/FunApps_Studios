package com.todmonster.funappsstudios.ui.settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leandro on 17/07/2017.
 */

public class Settings {

    /* required */
    private String title;

    /* optional */
    private String subtitle;
    private boolean switchON;


    public static List<Settings> getSettingsList() {

        List<Settings> settings = new ArrayList<>(6);

        // 0 - Theme (Theme)

        // 1 - Label  (Songs and Vibration)
        // 2 - Switch (Background Song)
        // 3 - Switch (Sound Effects)
        // 4 - Switch (Vibration)

        // 5 - Label  (Small Screens)
        // 6 - Switch (Small Screens)

        // 7 - Label (Rate us)
        // 8 - Rate  (Rate)

        Settings sTheme = new Settings();
        sTheme.setTitle("Theme");
        sTheme.setSubtitle("Light");
        settings.add(sTheme);

        Settings sLabel01 = new Settings();
        sLabel01.setTitle("Songs & Vibration");
        settings.add(sLabel01);

        Settings sBackground = new Settings();
        sBackground.setTitle("Background Sound");
        sBackground.setSubtitle("Background sound will be played");
        sBackground.setSwitchON(true);
        settings.add(sBackground);

        Settings sEffect = new Settings();
        sEffect.setTitle("Sound Effects");
        sEffect.setSubtitle("Sound effects will be played");
        sEffect.setSwitchON(true);
        settings.add(sEffect);

        Settings sVibration = new Settings();
        sVibration.setTitle("Vibration");
        sVibration.setSubtitle("Touch on monsters will generate vibration");
        sVibration.setSwitchON(true);
        settings.add(sVibration);

        Settings sLabel02 = new Settings();
        sLabel02.setTitle("Small Screens");
        settings.add(sLabel02);

        Settings sOptimized = new Settings();
        sOptimized.setTitle("Optimized for Small Screens");
        sOptimized.setSubtitle("Less cells and borders will be used in the grid");
        sOptimized.setSwitchON(false);
        settings.add(sOptimized);

        Settings sLabel03 = new Settings();
        sLabel03.setTitle("Rate Us");
        settings.add(sLabel03);

        // TODO: outra coisa alem de label
        Settings sLabel04 = new Settings();
        sLabel04.setTitle("Rate us with 5 stars!");
        settings.add(sLabel04);

        return settings;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public boolean isSwitchON() {
        return switchON;
    }

    public void setSwitchON(boolean switchON) {
        this.switchON = switchON;
    }
}
