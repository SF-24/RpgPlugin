// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.command;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProfileCommand implements CommandExecutor {

    Rpg rpg;

    public ProfileCommand(Rpg rpg) {
        this.rpg = rpg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args[0].equalsIgnoreCase("set") && args.length==2) {

                boolean exist = rpg.getConnectListener().getPlayerProfileInstance(player).checkIfProfileExists(args[1]);
                if(exist) {
                    rpg.getConnectListener().getPlayerProfileInstance(player).setActiveProfile(args[1]);
                    player.sendMessage(ChatColor.WHITE + "Activated profile " + ChatColor.AQUA + args[1]);
                } else {
                    player.sendMessage(ChatColor.RED + "Profile " + args[1] + " does not exist");
                }


            } else if(args[0].equalsIgnoreCase("delete") && args.length==2) {

                boolean exist = rpg.getConnectListener().getPlayerProfileInstance(player).checkIfProfileExists(args[1]);
                if(exist) {
                    rpg.getConnectListener().getPlayerProfileInstance(player).deleteProfile(args[1]);
                    player.sendMessage(ChatColor.WHITE + "Deleted profile " + ChatColor.AQUA + args[1]);
                } else {
                    player.sendMessage(ChatColor.RED + "Profile " + args[1] + " does not exist");
                }


            } else if(args[0].equalsIgnoreCase("create") && args.length == 2) {
                boolean exist = rpg.getConnectListener().getPlayerProfileInstance(player).checkIfProfileExists(args[1]);
                if(!exist) {
                    rpg.getConnectListener().getPlayerProfileInstance(player).createProfile(args[1]);
                    player.sendMessage(ChatColor.WHITE + "Created profile " + ChatColor.AQUA + args[1]);
                } else {
                    player.sendMessage(ChatColor.RED + "Profile " + args[1] + " already exist");
                }
            } else if(args[0].equalsIgnoreCase("list")) {
                player.sendMessage(ChatColor.BOLD + "Listing player profiles:");

                for(String key : rpg.getConnectListener().getPlayerProfileInstance(player).getModifyProfileYaml().getConfigurationSection("profiles").getKeys(false)) {

                    player.sendMessage("- " + key + " [" + rpg.getConnectListener().getPlayerProfileInstance(player).getModifyProfileYaml().getString("profiles." + key + ".name") + "]");
                }
            } else if(args[0].equalsIgnoreCase("current")) {

                player.sendMessage("Your active profile is " + ChatColor.AQUA + rpg.getConnectListener().getPlayerProfileInstance(player).getActiveProfile());

            } else {
                player.sendMessage(ChatColor.RED + "ERROR! Incorrect command syntax");
            }
        } else {
            System.out.println("ERROR! You can only execute /profile command as a player");
        }

        return false;
    }
}
