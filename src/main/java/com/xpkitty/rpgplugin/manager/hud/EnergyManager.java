package com.xpkitty.rpgplugin.manager.hud;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerSpellFile;
import org.bukkit.entity.Player;

public class EnergyManager {

    public static void giveEnergy(Rpg rpg, Player player, int count) {
        PlayerSpellFile playerSpellFile = rpg.getConnectListener().getPlayerSpellFile(player);
        int energy = playerSpellFile.getCurrentEnergy();
        int baseEnergy = playerSpellFile.getBaseEnergy();

        energy+=count;
        if(energy>baseEnergy) {
            energy=baseEnergy;
        }
        playerSpellFile.setCurrentEnergy(energy);
    }

    public static void takeEnergy(Rpg rpg, Player player, int count) {
        PlayerSpellFile playerSpellFile = rpg.getConnectListener().getPlayerSpellFile(player);
        int energy = playerSpellFile.getCurrentEnergy();

        energy-=count;
        if(energy<0) {
            energy=0;
        }
        playerSpellFile.setCurrentEnergy(energy);
    }

    public static int getEnergyCount(Rpg rpg, Player player) {
        PlayerSpellFile playerSpellFile = rpg.getConnectListener().getPlayerSpellFile(player);
        return playerSpellFile.getCurrentEnergy();
    }

    public static String getEnergyHudText(Player player, int count) {
        for(EnergyIcons energyIcons : EnergyIcons.values()) {
            if(energyIcons.getCount() == count) {
                return EnergyIcons.PREFIX.getDisplay() + energyIcons.getDisplay();
            }
        }
        return null;
    }
}
