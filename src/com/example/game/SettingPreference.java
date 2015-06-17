package com.example.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingPreference extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    public static final String BGM_SOUND_ON = "setting_sound_bgm";
    public static final String EFFECT_SOUND_ON = "setting_sound_effect";

    boolean bgmSoundOn, effectSoundOn;
    SharedPreferences settings;
    SharedPreferences.Editor settingsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_layout);

        CheckBoxPreference settingBGMSound = (CheckBoxPreference) findPreference("setting_sound_bgm");
        CheckBoxPreference settingEffectSound = (CheckBoxPreference) findPreference("setting_sound_effect");

        settingBGMSound.setOnPreferenceClickListener(this);
        settingEffectSound.setOnPreferenceClickListener(this);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        settingsEditor = settings.edit();

        bgmSoundOn = settings.getBoolean(BGM_SOUND_ON, true);
        effectSoundOn = settings.getBoolean(EFFECT_SOUND_ON, true);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("setting_sound_bgm")) {
            preference.setDefaultValue(!bgmSoundOn);
            settingsEditor.putBoolean(BGM_SOUND_ON, !bgmSoundOn);
        }

        if (preference.getKey().equals("setting_sound_effect")) {
            preference.setDefaultValue(!effectSoundOn);
            settingsEditor.putBoolean(EFFECT_SOUND_ON, !effectSoundOn);
        }

        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        settingsEditor.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingPreference.this, MenuActivity.class));
        finish();
    }
}
