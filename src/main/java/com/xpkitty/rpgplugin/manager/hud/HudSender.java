package com.xpkitty.rpgplugin.manager.hud;

import com.xpkitty.rpgplugin.Rpg;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class HudSender {

    public static void sendHud(Rpg rpg, Player player, HudType hudType) {
        int energy = EnergyManager.getEnergyCount(rpg, player);
        String energyText = EnergyManager.getEnergyHudText(player, energy);


        TextComponent hudText = new TextComponent(energyText);

        if (energyText != null) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, hudText);
        }
    }

}
