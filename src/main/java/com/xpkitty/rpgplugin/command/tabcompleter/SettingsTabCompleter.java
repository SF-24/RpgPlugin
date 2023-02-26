package com.xpkitty.rpgplugin.command.tabcompleter;

import com.xpkitty.rpgplugin.manager.data.player_settings.settings_manager.SettingsList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingsTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<String> settings = new ArrayList<>();

        for(SettingsList element: SettingsList.values()) {
            if(element.isImplemented()) {
                settings.add(element.name().toLowerCase(Locale.ROOT));
            }
        }

        if(args.length==1) {
            return StringUtil.copyPartialMatches(args[0], settings ,new ArrayList<>());
        }

        return null;
    }

}
