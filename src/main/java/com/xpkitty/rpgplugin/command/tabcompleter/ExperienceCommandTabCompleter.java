// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.command.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExperienceCommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length==1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("list","give","set","give_party_per_player","give_party_split"),new ArrayList<>());
        } else if(args.length==2) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList(""),new ArrayList<>());
        }

        return null;
    }
}
