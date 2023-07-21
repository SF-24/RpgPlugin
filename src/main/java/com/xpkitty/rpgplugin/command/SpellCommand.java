// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.command;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.spell_crafting.admin.AdminSpellManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpellCommand implements CommandExecutor {
    Rpg rpg;

    public SpellCommand(Rpg rpg) {
        this.rpg = rpg;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length==4&&args[0].equalsIgnoreCase("make")) {
            boolean success = false;

            try {
                Integer.parseInt(args[3]);
                success=true;
            } catch (Exception e) {
                sender.sendMessage("Wand movements have to be integers");
            }
            if(success) {
                String fixedName = args[1].replace('_',' ');
                String fixedIncantation = args[2].replace('_',' ');

                AdminSpellManager.addSpell(rpg,fixedName,fixedIncantation,args[3]);
                sender.sendMessage("Spell saved");
            }
        } else if(args.length==1 && args[0].equals("list")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;

                player.sendMessage(ChatColor.BOLD.toString() + ChatColor.GOLD + "Listing spells you know:");

                YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerSpellFile(player).getModifySpellFile();

                for(String key : yamlConfiguration.getConfigurationSection("spells").getKeys(false)) {
                    String path = "spells." + key + ".";

                    if(yamlConfiguration.getBoolean(path+"learned")) {
                        player.sendMessage(ChatColor.AQUA + key);
                    }
                }

                for(String key : yamlConfiguration.getConfigurationSection("spells").getKeys(false)) {
                    String path = "spells." + key + ".";

                    if(!yamlConfiguration.getBoolean(path+"learned")) {
                        player.sendMessage(ChatColor.DARK_RED + key);
                    }
                }


            } else {
                sender.sendMessage("Only a player can do this");
            }
        } else {
            if(sender instanceof Player) {
                Player player = ((Player) sender).getPlayer();
                player.sendMessage(ChatColor.RED + "Usage: /spell make <name> <incantation> <wand movements>");
            } else {
                System.out.println("Usage: /spell make <name> <incantation> <wand movements>");
                System.out.println("Usage: /spell list");
            }
        }

        return false;
    }
}
