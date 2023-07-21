// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.command;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.player_settings.settings_manager.SettingType;
import com.xpkitty.rpgplugin.manager.data.player_settings.settings_manager.SettingsList;
import com.xpkitty.rpgplugin.manager.data.player_settings.settings_manager.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class SettingsCommand implements CommandExecutor {
    Rpg rpg;

    public SettingsCommand(Rpg rpg) {
        this.rpg=rpg;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length==0) {
                player.sendMessage(ChatColor.AQUA.toString() + "Displaying full list of settings:");

                for(SettingsList element : SettingsList.values()) {
                    ChatColor color = ChatColor.RED;

                    if(element.isImplemented()) color = ChatColor.GREEN;
                    player.sendMessage(color + element.name().toLowerCase(Locale.ROOT));
                }
            } else if(args.length == 1) {
                String element = args[0];

                if(SettingsManager.checkStringIsSetting(element)) {
                    player.sendMessage(ChatColor.GREEN + "Displaying possible settings");
                } else {
                    player.sendMessage(ChatColor.RED + "[AnotherRpgPlugin] This string is not a setting :( ERROR");
                }
            }


        } else {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a Player. :D");
        }

        return false;
    }
}
