// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.command;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class AchievementCommand implements CommandExecutor {

    Rpg rpg;

    public AchievementCommand(Rpg rpg) {
        this.rpg = rpg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack handItem = player.getInventory().getItemInMainHand();
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bookMeta = (BookMeta) book.getItemMeta();
            bookMeta.setAuthor("Gandalf");
            bookMeta.setTitle("Achievements");
            StringBuilder achievements = new StringBuilder();


            YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

            for(String string : yamlConfiguration.getConfigurationSection("ACHIEVEMENTS").getKeys(false)) {

                String line;


                System.out.println("[DEBUG] Checking if player " + player + " has achievement: " + string);

                if(yamlConfiguration.getBoolean("ACHIEVEMENTS." + string)) {
                    switch(string) {
                        case "KILL_GOBLIN":
                            line = " Goblin Slayer";
                            break;
                        case "KILL_UNDEAD":
                            line = " Bane of the Undead";
                            break;
                        case "EAT_ENT_STEW":
                            line = " Invigoration";
                            break;
                        case "DRINK_ATHELAS":
                            line = " Healing drink!";
                            break;
                        case "DRINK_MITUVOR":
                            line = " Moruvor";
                            break;
                        default:
                            line = "";
                    }
                    achievements.append(line).append("\n");
                }


            }
            bookMeta.addPage(ChatColor.BOLD + "Achievements:\n\n" + ChatColor.RESET + achievements);
            book.setItemMeta(bookMeta);

            player.getInventory().setItemInMainHand(book);
            player.openBook(player.getInventory().getItemInMainHand());
            player.getInventory().setItemInMainHand(handItem);
        }

        return false;
    }
}
