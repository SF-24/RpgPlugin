// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

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
