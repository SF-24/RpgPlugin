/*
 *     Copyright (C) 2023 Sebastian Frynas
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Contact: sebastian.frynas@outlook.com
 *
 */
package com.xpkitty.rpgplugin.command.hogwarts;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.hogwarts.HogwartsHouseList;
import com.xpkitty.rpgplugin.manager.hogwarts.HogwartsHouseManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class HousePointCommand implements CommandExecutor {
    Rpg rpg;
    boolean giveHousePoints = false;
    boolean takeHousePoints = false;
    boolean takeOwnHousePoints = false;


    public HousePointCommand(Rpg rpg) {
        this.rpg=rpg;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(player.hasPermission("rpgpl.points.give.*")) {
                giveHousePoints=true;
            }
            if(player.hasPermission("rpgpl.points.take.*")) {
                takeOwnHousePoints=true;
                takeHousePoints=true;
            } else if(player.hasPermission("rpgpl.points.take.own")) {
                takeOwnHousePoints=true;
            }
        } else {
            giveHousePoints=true;
            takeHousePoints=true;
            takeOwnHousePoints=true;
        }


        if(args.length==1 && args[0].equalsIgnoreCase("list")) {
            sender.sendMessage(ChatColor.WHITE + "Displaying house points status");

            HashMap<String, Integer> houses = new HashMap<>();

            for(HogwartsHouseList element : HogwartsHouseList.values()) {
                int points = HogwartsHouseManager.getHousePoints(rpg, element);
                if(rpg.getHogwartsDataManager().getModifyYaml().contains("hogwarts." + element.name().toLowerCase(Locale.ROOT))) {
                    points = (int) rpg.getHogwartsDataManager().getModifyYaml().get("hogwarts." + element.name().toLowerCase(Locale.ROOT));
                }
                houses.put(element.getPrefix() + element.getDisplay() + " " + element.getPrefix() + ChatColor.BOLD, points);
            }
            LinkedHashMap<String, Integer> sortedPoints =     houses.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> null, // or throw an exception
                            LinkedHashMap::new));;
            for (String s: sortedPoints.keySet()) {
                sender.sendMessage(s + sortedPoints.get(s));
            }

        } else if(args.length==1 && ((args[0].equalsIgnoreCase("query") || args[0].equalsIgnoreCase("permission") || args[0].equalsIgnoreCase("permissions") || args[0].equalsIgnoreCase("perms") || args[0].equalsIgnoreCase("perm")))) {
            sender.sendMessage(ChatColor.GREEN + "You can view command help with /hp help [page]");
            sender.sendMessage(ChatColor.GREEN + "You can list houses with /hp houses");
            sender.sendMessage(ChatColor.GREEN + "You can open the permission menu with /hp query or /hp perms");
            sender.sendMessage(ChatColor.GREEN + "You can list house points with /hp list");
            if(giveHousePoints) {
                sender.sendMessage(ChatColor.GREEN + "You can give house points with /hp give <house> <amount> or /hp <house> <amount more than 0>");
            } else {
                sender.sendMessage(ChatColor.RED + "You cannot give house points");
            }
            if(takeHousePoints) {
                sender.sendMessage(ChatColor.GREEN + "you can take house points from all 4 houses with /hp take <house> <amount> or /hp <house> <amount less than 0>");
            } else if(takeOwnHousePoints) {
                sender.sendMessage(ChatColor.GREEN + "you can take house points from only your own house with /hp take <house> <amount> or /hp <house> <amount less than 0>");
            } else {
                sender.sendMessage(ChatColor.RED + "You cannot take house points");
            }
        } else if(args.length==1 && args[0].equalsIgnoreCase("houses")) {
            sender.sendMessage("There are for Hogwarts houses:");

            for(HogwartsHouseList element : HogwartsHouseList.values()) {
                sender.sendMessage(element.getPrefix() + " " + element.getDisplay());
            }

            sender.sendMessage(" ");
            sender.sendMessage("You can use only the 1st letter of the house name as an abbreviation in a command");
        } else if(args.length==0 || ((args.length==2 || args.length==1) && (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("help")))) {
            int page = 1;

            if(args.length>1) {
                try {
                    page = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "COMMAND USAGE:" + ChatColor.DARK_RED + "/hp help [page]" + ChatColor.RED + " or " + ChatColor.DARK_RED + "/hp info [page]");
                }
            }

            int pageLimit = 2;
            if (page > pageLimit || page < 1) {
                sender.sendMessage(ChatColor.RED + "Page does not exist, select a number from 1 to " + pageLimit);
            }


            sender.sendMessage("HOUSE POINT COMMAND HELP PAGE " + page + ":");

            if (page == 1) {
                sendCommandHelp("/hp help [page]", "open this menu", sender);
                sendCommandHelp("/hp info [page]", "alias to /hp help", sender);
                sendCommandHelp("/hp list", "list house points", sender);
                sendCommandHelp("/hp query", "see what perms you have for this command", sender);
                sendCommandHelp("/hp permissions", "alias to /hp query", sender);
                sendCommandHelp("/hp permission", "alias to /hp query", sender);
                sendCommandHelp("/hp perms", "alias to /hp query", sender);
                sendCommandHelp("/hp perm", "alias to /hp query", sender);
                sendCommandHelp("/hp houses", "list hogwarts houses", sender);
            } else if (page == 2) {
                sendCommandHelp("/hp give <house> <amount>", "give house points", sender);
                sendCommandHelp("/hp give <r|g|h|s> <amount>", "alias to command above", sender);
                sendCommandHelp("/hp take <house> <amount>", "take house points", sender);
                sendCommandHelp("/hp take <r|g|h|s> <amount>", "alias to command above", sender);
                sendCommandHelp("/hp <house> <amount>", "give|take house points", sender);
                sendCommandHelp("/hp <r|g|h|s> <amount>", "alias to /hp <house>", sender);
            }
        } else if(args!=null && (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("take"))) {
            if(args.length==3) {
                int amount = 0;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "specify a number");
                    return false;
                }

                if(args[0].equalsIgnoreCase("take")) {
                    amount *= -1;
                }

                String s = modifyHousePointCount(args[1],amount,sender);
                sender.sendMessage(s);
            } else {
                sender.sendMessage(ChatColor.RED + "ERROR! Type /hp help <page> for more info about this command");
            }
        } else {

            if(args.length==2) {
                int amount = 0;
                try {
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "specify a number");
                    return false;
                }

                String s = modifyHousePointCount(args[0],amount,sender);
                sender.sendMessage(s);
            } else {
                sender.sendMessage(ChatColor.RED + "ERROR! Type /hp help <page> for more info about this command");
            }

        }
        return false;
    }

    public String modifyHousePointCount(String houseId, int amount, CommandSender sender) {

        HogwartsHouseList house = null;
        int revisedAmount = amount;
        String houseDisplay = "";
        String operationDisplayPresent = "";
        String operationDisplayPast = "";
        String pointWord = "points";
        boolean negative = false;

        for(HogwartsHouseList element : HogwartsHouseList.values()) {
            if(houseId.equalsIgnoreCase(element.name().substring(0,1)) || houseId.equalsIgnoreCase(element.name())) {
                houseDisplay = element.getDarkPrefix() + element.getDisplay();
                house = element;
            }
        }
        if(house == null) {
            return ChatColor.RED + "Could not identify Hogwarts house, please specify a house name or the first letter of a Hogwarts house";
        }

        int housePointsInt = HogwartsHouseManager.getHousePoints(rpg,house);
        int newHousePointsInt = housePointsInt+amount;




        if(newHousePointsInt<0) {
            newHousePointsInt=0;
            negative=true;
            revisedAmount=housePointsInt;
        }

        if(amount == 0) {
            return ChatColor.RED + "Specify a number that is not 0";
        }

        if(revisedAmount == 1 || revisedAmount == -1) {
            pointWord = "point";
        }
        if(amount>0) {
            operationDisplayPresent="give";
            operationDisplayPast="given";

            if(!giveHousePoints) {
                return ChatColor.RED + "You do not have permission to give house points";
            }
        } else if(amount<0) {
            operationDisplayPresent="take";
            operationDisplayPast="taken";
            boolean ownHouse = false;

            if(sender!=null) {
                if (sender instanceof Player) {
                    HogwartsHouseList playerHouse = HogwartsHouseManager.getHogwartsHouse(rpg, ((Player) sender));
                    if (house.equals(playerHouse)) {
                        ownHouse = true;
                    }
                }
            }

            if(!(takeOwnHousePoints&&ownHouse) && !takeHousePoints ) {
                if(ownHouse) {
                    sender.sendMessage(ChatColor.RED + "You can only take house points form your own house");
                } else {
                    sender.sendMessage(ChatColor.RED + "You cannot take house points");
                }
            }
        }

        HogwartsHouseManager.setHousePoints(rpg,house,newHousePointsInt);

        if(negative) {
            if (housePointsInt > 0) {
                return ChatColor.RED + "Could not take " + amount + " " + pointWord + " from " + houseDisplay + ChatColor.RED + ", taken " + housePointsInt + " instead.";
            } else {
                return houseDisplay + ChatColor.RED + "does not have any points to take";
            }
        }

        return operationDisplayPast + " " + amount + " " + pointWord + " from " + houseDisplay;
    }

    public static void sendCommandHelp(String command, String description, CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + command + " " + ChatColor.WHITE + description);
    }
}
