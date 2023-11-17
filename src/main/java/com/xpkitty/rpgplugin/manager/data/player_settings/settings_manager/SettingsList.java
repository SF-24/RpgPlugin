/*
 *     Copyright (C) 2023 Sebastian Frynas
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Contact: sebastian.frynas@outlook.com
 *
 */
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
