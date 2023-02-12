package com.xpkitty.rpgplugin.command.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileCommandTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length==1) {
            return StringUtil.copyPartialMatches(args[0],Arrays.asList("list","set","current","create","delete"), new ArrayList<>());
        }


        return null;
    }
}
