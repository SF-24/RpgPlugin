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
