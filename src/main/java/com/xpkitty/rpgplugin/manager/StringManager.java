package com.xpkitty.rpgplugin.manager;

import com.xpkitty.rpgplugin.Rpg;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class StringManager {
    private final static int CENTER_PX = 154;

    public static void sendActionBar(Player player, String text) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
    }

    public static void sendCenteredMessage(Player player, String message){
        if(message == null || message.equals("")) player.sendMessage("");
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
                continue;
            }else if(previousCode == true){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                }else isBold = false;
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb.toString() + message);
    }

    public static void debugMessage(Player player, String string) {
        if(player.hasPermission("rpgpl.debug")) {
            player.sendMessage(ChatColor.RED + "[DEBUG] " + string);
        }
    }

    public static void sendAchievement(Rpg rpg, Player player, String path, String name) {
        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
        File file = rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile();

        if(!yamlConfiguration.contains("achievement_count")) {
            yamlConfiguration.createSection("achievement_count");
            yamlConfiguration.set("achievement_count", 0);
        }

        if(!yamlConfiguration.getBoolean("ACHIEVEMENTS." + path)) {
            int achievements = yamlConfiguration.getInt("achievement_count");
            yamlConfiguration.set("achievement_count", achievements+1);
            yamlConfiguration.set("ACHIEVEMENTS." + path, true);

            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE,1f,1f);

            player.sendMessage("");
            player.sendMessage(ChatColor.GOLD + "[ACHIEVEMENT UNLOCKED] " + name);
            player.sendMessage("");
        }

        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
