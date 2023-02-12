package com.xpkitty.rpgplugin.manager.data.player_settings.settings_data;

import com.xpkitty.rpgplugin.manager.data.player_settings.settings_manager.LightsaberSwitchSetting;

public class SettingsData {

    boolean shiftClickForAbility;
    LightsaberSwitchSetting lightsaberSwitchSetting;

    public SettingsData(boolean shiftClickForAbility, LightsaberSwitchSetting lightsaberSwitchSetting) {
        this.shiftClickForAbility=shiftClickForAbility;
        this.lightsaberSwitchSetting=lightsaberSwitchSetting;
    }


    public void setAbilityShiftClick(boolean setting) {
        shiftClickForAbility = setting;
    }

    public void setLightsaberSwitchSetting(LightsaberSwitchSetting lightsaberSwitchSetting) {
        this.lightsaberSwitchSetting =  lightsaberSwitchSetting;
    }

    public boolean isShiftClickForAbility() { return shiftClickForAbility; }
    public LightsaberSwitchSetting getLightsaberSwitchSetting() { return lightsaberSwitchSetting; }

}
