package com.todmonster.funappsstudios.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.todmonster.funappsstudios.R;

import java.util.List;

import static com.todmonster.funappsstudios.ui.settings.Preferences.BACKGROUND_SOUND;
import static com.todmonster.funappsstudios.ui.settings.Preferences.DARK_THEME;
import static com.todmonster.funappsstudios.ui.settings.Preferences.SMALL_SCREENS;
import static com.todmonster.funappsstudios.ui.settings.Preferences.SOUND_EFFECTS;
import static com.todmonster.funappsstudios.ui.settings.Preferences.VIBRATION;

/**
 * Created by Leandro on 17/07/2017.
 */

public class SettingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Settings> settingsList;

    private Context context;

    private LayoutInflater inflater;

    private SharedPreferences pref;


    public SettingsAdapter(Context context, List<Settings> settingsList) {
        this.context = context;
        this.settingsList = settingsList;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pref = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        //TODO: transforma isso em constante talvez
        // 0 - Theme (Theme)
        //
        // 1 - Label  (Songs and Vibration)
        // 2 - Switch (Background Song)
        // 3 - Switch (Sound Effects)
        // 4 - Switch (Vibration)
        //
        // 5 - Label  (Small Screens)
        // 6 - Switch (Optimized for Small Screens)
        //
        // 7 - Label (Rate us)
        // 8 - Rate  (Rate)

        switch (viewType) {

            case 0:
                View itemViewTheme  = inflater.inflate(R.layout.item_settings_theme, parent, false);
                viewHolder = new ViewHolder.Theme(itemViewTheme);
                break;

            case 1: case 5: case 7:
                View itemViewLabel  = inflater.inflate(R.layout.item_settings_label, parent, false);
                viewHolder = new ViewHolder.Label(itemViewLabel);
                break;

            case 8:
                View itemViewRate  = inflater.inflate(R.layout.item_settings_rate_us, parent, false);
                viewHolder = new ViewHolder.RateUs(itemViewRate);

                if (pref.getBoolean(Preferences.DARK_THEME.name(), false)) {
                    ( (ImageView) itemViewRate.findViewById(R.id.tod0)).setImageResource(R.mipmap.good_tod_settings_blue);
                    ( (ImageView) itemViewRate.findViewById(R.id.tod1)).setImageResource(R.mipmap.good_tod_settings_blue);
                    ( (ImageView) itemViewRate.findViewById(R.id.tod2)).setImageResource(R.mipmap.good_tod_settings_blue);
                    ( (ImageView) itemViewRate.findViewById(R.id.tod3)).setImageResource(R.mipmap.good_tod_settings_blue);
                    ( (ImageView) itemViewRate.findViewById(R.id.tod4)).setImageResource(R.mipmap.good_tod_settings_blue);
                }

                break;


            default:
                View itemViewSwitch = inflater.inflate(R.layout.item_settings_switch, parent, false);
                viewHolder = new ViewHolder.Switch(itemViewSwitch);
                break;

        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {

        // Theme
        case 0:
            ViewHolder.Theme hTheme = (ViewHolder.Theme) holder;
            hTheme.tvTitle   .setText(settingsList.get(position).getTitle()   );

            boolean isDark = pref.getBoolean(DARK_THEME.name(), false);
            hTheme.tvSubtitle.setText(isDark? "Dark" : "Light");
            break;

        // Labels
        case 1: case 5: case 7:
            ViewHolder.Label hLabel = (ViewHolder.Label) holder;
            hLabel.label.setText(settingsList.get(position).getTitle());
            break;

        // Rate Us
        case 8:
            ViewHolder.RateUs hRate = (ViewHolder.RateUs) holder;
            hRate.tvTitle.setText(settingsList.get(position).getTitle());
            break;

        // Holders with Switches
        default:
            ViewHolder.Switch h0 = (ViewHolder.Switch) holder;
            h0.tvTitle   .setText(settingsList.get(position).getTitle()   );
            h0.tvSubtitle.setText(settingsList.get(position).getSubtitle());

            switch (holder.getItemViewType()) {
                case 2:
                    h0.mySwitch.setChecked(pref.getBoolean(BACKGROUND_SOUND.name(), true));
                    break;

                case 3:
                    h0.mySwitch.setChecked(pref.getBoolean(SOUND_EFFECTS.name(), true));
                    break;

                case 4:
                    h0.mySwitch.setChecked(pref.getBoolean(VIBRATION.name(), true));
                    break;

                case 6:
                    h0.mySwitch.setChecked(pref.getBoolean(SMALL_SCREENS.name(), false));
                    break;
            }

        }


    }

    @Override
    public int getItemCount() {
        return settingsList.size();
    }



}
