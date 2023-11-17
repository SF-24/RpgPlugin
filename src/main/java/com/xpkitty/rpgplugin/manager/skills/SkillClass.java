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
package com.xpkitty.rpgplugin.manager.skills;

public class SkillClass {

    // variable
    int level;
    int xp;

    PlayerSkills skill;

    public SkillClass(PlayerSkills skill) {
        this.skill=skill;
    }

    public int getLevel() {
        return level;
    }
    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp=xp;
    }
    public void setLevel(int level) {
        this.level=level;
    }
}
