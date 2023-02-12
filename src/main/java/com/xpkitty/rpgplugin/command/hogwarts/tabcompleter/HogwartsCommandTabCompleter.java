package com.xpkitty.rpgplugin.command.hogwarts.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HogwartsCommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length==1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("list","query","perm","permission","perms","permissions","hufflepuff","ravenclaw","gryffindor","slytherin","give","take","houses","help","info"),new ArrayList<>());
        } else if(args.length==2) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList(""),new ArrayList<>());
        }

        return null;
    }
}
