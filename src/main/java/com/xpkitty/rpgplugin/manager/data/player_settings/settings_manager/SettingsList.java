// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.data.player_settings.settings_manager;

public enum SettingsList {

    LIGHTSABER_SWITCH_SETTING(true, SettingType.ENUM),
    ABILITY_SNEAK_SETTING(false, SettingType.BOOLEAN),
    LIGHTSABER_TURN_ON_SETTING(false, SettingType.ENUM);


    boolean isImplemented;
    SettingType settingType;

    SettingsList(boolean isImplemented, SettingType settingType) {
        this.isImplemented=isImplemented;
        this.settingType=settingType;
    }

    public boolean isImplemented() {return isImplemented;}
    public SettingType getSettingType() {return settingType;}
}
