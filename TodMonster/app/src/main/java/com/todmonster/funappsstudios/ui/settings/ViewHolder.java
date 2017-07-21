package com.todmonster.funappsstudios.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.todmonster.funappsstudios.R;

import static com.todmonster.funappsstudios.ui.settings.Preferences.*;

/**
 * Class with all types of ViewHolders used
 * TODO: study how instance of static class work
 */

public class ViewHolder {

    public static class Label extends RecyclerView.ViewHolder {

        public TextView label;

        public Label(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.label);
        }
    }

    public static class Theme extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle;
        public TextView tvSubtitle;

        private LayoutInflater inflater;
        private Context context;


        public Theme(View itemView) {
            super(itemView);
            context = itemView.getContext();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;

            itemView.setOnClickListener(this);

            tvTitle    = (TextView) itemView.findViewById(R.id.title);
            tvSubtitle = (TextView) itemView.findViewById(R.id.subtitle);
        }

        @Override
        public void onClick(View v) {

            View view = inflater.inflate(R.layout.dialog_two_radio_button, null);
            SharedPreferences pref = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);

            new AlertDialog.Builder(context)
                    .setTitle("Theme")
                    .setView(view)
                    .setNegativeButton("CANCEL", (dialog, which) -> {})
                    .setPositiveButton("OK"    , (dialog, which) -> {

                        RadioButton rbLight = (RadioButton) view.findViewById(R.id.rb_light);
                        RadioButton rbDark  = (RadioButton) view.findViewById(R.id.rb_dark);

                        Intent refresh = new Intent(context, SettingsActivity.class);
                        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        if (rbDark.isChecked() && tvSubtitle.getText().equals("Light")) {
                            pref.edit().putBoolean(DARK_THEME.name(), true).apply();
                            tvSubtitle.setText("Dark");

                            // Refresh main activity upon close of dialog box
                            // obs: better if recreate()
                            context.startActivity(refresh);

                        }

                        else if (rbLight.isChecked() && tvSubtitle.getText().equals("Dark")) {
                            pref.edit().putBoolean(DARK_THEME.name(), false).apply();
                            tvSubtitle.setText("Light");
                            context.startActivity(refresh);
                        }


                    })
                    .show();

        }
    }

    public static class Switch extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle;
        public TextView tvSubtitle;
        public android.widget.Switch mySwitch;

        public Context context;

        public Switch(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            tvTitle    = (TextView) itemView.findViewById(R.id.title);
            tvSubtitle = (TextView) itemView.findViewById(R.id.subtitle);
            mySwitch   = (android.widget.Switch)   itemView.findViewById(R.id.my_switch);

        }


        @Override
        public void onClick(View v) {
            mySwitch.setChecked( ! mySwitch.isChecked() );
            SharedPreferences pref = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);

            switch (getItemViewType()) {

                case 2:
                    pref.edit().putBoolean(BACKGROUND_SOUND.name(), mySwitch.isChecked()).apply();
                    break;

                case 3:
                    pref.edit().putBoolean(SOUND_EFFECTS.name()   , mySwitch.isChecked()).apply();
                    break;

                case 4:
                    pref.edit().putBoolean(VIBRATION.name()       , mySwitch.isChecked()).apply();
                    break;

                case 6:
                    pref.edit().putBoolean(SMALL_SCREENS.name()   , mySwitch.isChecked()).apply();
                    break;
            }

        }
    }



    public static class RateUs extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle;
        private Context context;

        public RateUs(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            tvTitle    = (TextView) itemView.findViewById(R.id.title);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Later", Toast.LENGTH_LONG).show();
        }
    }


}
