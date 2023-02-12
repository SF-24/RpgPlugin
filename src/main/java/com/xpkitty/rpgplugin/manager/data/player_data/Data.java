package com.xpkitty.rpgplugin.manager.data.player_data;

import java.time.LocalDate;
import java.util.Date;

public class Data {

    private String playerName;
    private int level;
    private int xp;
    private int unusedSkillPoints;
    private Date lastLogin;

    public Data(String playerName, int level, int xp, Date lastLogin, int unusedSkillPoints) {
        this.playerName = playerName;
        this.level = level;
        this.xp = xp;
        this.lastLogin = lastLogin;
        this.unusedSkillPoints = unusedSkillPoints;
    }

    public String getPlayerName() { return playerName; }
    public int getLevel() { return level; }
    public int getXp() { return xp; }
    public int getUnusedSkillPoints() { return unusedSkillPoints; }
    public Date getLastLogin() { return lastLogin; }

}
