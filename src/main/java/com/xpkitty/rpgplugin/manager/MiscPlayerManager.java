package com.xpkitty.rpgplugin.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerDataManager;
import com.xpkitty.rpgplugin.manager.player_class.ClassList;
import com.xpkitty.rpgplugin.manager.player_class.abilities.AbilityType;
import com.xpkitty.rpgplugin.manager.player_class.abilities.AttackType;
import com.xpkitty.rpgplugin.manager.player_groups.InviteManager;
import com.xpkitty.rpgplugin.manager.player_groups.parties.PartyManager;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import org.bukkit.*;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class MiscPlayerManager {

    public HashMap<String, PartyManager> partyMap = new HashMap<>();
    public HashMap<UUID, InviteManager> inviteManagers = new HashMap<>();
    
    public Cache<UUID, Long> spellCooldown = CacheBuilder.newBuilder().expireAfterWrite(500, TimeUnit.MILLISECONDS).build();
    public Cache<UUID, Long> teleportCooldown = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).build();
    public Cache<UUID, Long> damageActive = CacheBuilder.newBuilder().expireAfterWrite(25, TimeUnit.SECONDS).build();
    public Cache<UUID, Long> mainHandLightsaberEjectCoolDown = CacheBuilder.newBuilder().expireAfterWrite(100, TimeUnit.MILLISECONDS).build();


    public HashMap<UUID, AttackType> nextAttack = new HashMap<>();

    public boolean isOffHandLightsaberCoolDown(Player player) {
        return false;
    }

    public boolean isMainHandLightsaberCoolDown(Player player) {
        return mainHandLightsaberEjectCoolDown.asMap().containsKey(player.getUniqueId());
    }

    public void putPlayerInMainHandLightsaberCoolDown(Player player) {
        mainHandLightsaberEjectCoolDown.put(player.getUniqueId(),System.currentTimeMillis()+100);
    }

    public void removeInvite(Player who, Player inviter) {
        if(inviteManagers.containsKey(who.getUniqueId())) {
            InviteManager inviteManager = inviteManagers.get(who.getUniqueId());
            if(inviteManager.hasPartyInviteFromPlayer(inviter)) {
                inviteManager.removePartyInviteFromPlayer(inviter);
            }
        }
    }

    public void invitePlayer(Player who, Player inviter) {
       if(!inviteManagers.containsKey(who.getUniqueId())) {
           InviteManager inviteManager = new InviteManager();
           inviteManagers.put(who.getUniqueId(), inviteManager);
       }
       InviteManager inviteController = inviteManagers.get(who.getUniqueId());
       inviteController.addPartyInviteFromPlayer(inviter);
    }

    public boolean isInvitedBy(Player who, Player inviter) {
        if(!inviteManagers.containsKey(who.getUniqueId())) {
            return false;
        } else {
            InviteManager inviteManager = inviteManagers.get(who.getUniqueId());
            return inviteManager.hasPartyInviteFromPlayer(inviter);
        }
    }

    public boolean isPlayerInParty(Player player) {
        for(PartyManager party : partyMap.values()) {
            if (party.getPlayers().contains(player)) {
                return true;
            }
        }

        return false;
    }

    public String getPlayerParty(Player player) {
        for(PartyManager party : partyMap.values()) {
            if (party.getPlayers().contains(player)) {
                return party.getName();
            }
        }

        return null;
    }

    public void removeFromParty(Player player) {
        String name = getPlayerParty(player);
        getPartyInstance(name).removePlayer(player);

        if(getPartyInstance(name).getPlayers().size()<2) {
            for(Player playerVal: getPartyInstance(name).getPlayers()) {
                playerVal.sendMessage(ChatColor.RED + "Party was disbanded since there are not enough players in the party");
            }
            removeParty(name);
        }
    }

    public PartyManager getPartyInstance(String name) {
        if(partyMap.containsKey(name)) {
            return partyMap.get(name);
        }

        return null;
    }

    public void removeParty(String name) {
        partyMap.remove(name);
    }

    public void newParty(String name, ArrayList<Player> players, Player owner) {
        if(name == null) {
            name = owner.getDisplayName() + "'s party";
        }
        PartyManager party = new PartyManager();
        partyMap.put(name,party);

        party.create(players, owner, name);
    }


    public Cache<UUID, Long> getTeleportCooldown() {
        return teleportCooldown;
    }

    public Cache<UUID, Long> getSpellCooldown() {
        return spellCooldown;
    }





    public void teleportPlayerToSpawn(Player player, Rpg rpg) {

        if(player.getWorld().equals(Bukkit.getWorld("Flat"))) {
            if(player.getBedSpawnLocation() != null) {
                player.teleport(player.getBedSpawnLocation());
            } else {
                new MiscPlayerManager().teleportPlayer(rpg,player,"bree",rpg.getConfig(),false,false);
            }
        } else {
            new MiscPlayerManager().teleportPlayer(rpg,player,"bree",rpg.getConfig(),false,false);
            if(player.getBedSpawnLocation() != null) {
                player.teleport(player.getBedSpawnLocation());
            }
        }
    }

    public void testResourcePack(Player player, Rpg rpg, World world, PlayerDataManager playerDataManager) {

        boolean resourcePackFound = false;

        if(playerDataManager==null) {
            System.out.println("ERROR! playerdatamanager == null");
            playerDataManager = rpg.getConnectListener().getPlayerDataInstance(player);
        }

        if(playerDataManager!=null) {
            ResourcePack currentResource = playerDataManager.getPlayerResourcePack(player);


            //player.sendMessage("DEBUG: " + currentResource.name());
            for(ResourcePack resourcePack : ResourcePack.values()) {
                //player.sendMessage("DEBUG: " + currentResource + " | " + resourcePack);

                //player.sendMessage("WORLD: " + world);
                //player.sendMessage("Resource Pack world: " + resourcePack.getWorlds());

                if(resourcePack.getWorlds().contains(world.getName())) {
                    if(!currentResource.name().equals(resourcePack.name())) {
                        playerDataManager.setPlayerResourcePack(player,resourcePack);
                        resourcePackFound=true;
                    } else if(currentResource.name().equalsIgnoreCase(resourcePack.name())) {
                        resourcePackFound=true;
                    }
                    //player.sendMessage("[DEBUG]: " + currentResource + " | " + resourcePack);

                }
            }
            if(!resourcePackFound && !currentResource.equals(ResourcePack.NULL)) {
                player.setResourcePack(ResourcePack.NULL.getUrl());
            }
        }



    }

    public void teleportPlayer(Rpg rpg,Player player, String dataString, FileConfiguration fileConfiguration, boolean isScroll, boolean testParty) {
        Location location = new Location(Bukkit.getWorld(fileConfiguration.getString(dataString + "world")) ,
                fileConfiguration.getInt(dataString + "x") ,
                fileConfiguration.getInt(dataString + "y") ,
                fileConfiguration.getInt(dataString + "z") ,
                fileConfiguration.getInt(dataString + "yaw"),
                fileConfiguration.getInt(dataString + "pitch"));

        if(testParty) {
            if(rpg.getMiscPlayerManager().isPlayerInParty(player)) {
                if(rpg.getMiscPlayerManager().getPartyInstance(rpg.getMiscPlayerManager().getPlayerParty(player)).getOwner().getUniqueId().equals(player.getUniqueId())) {

                    for(Player partyPlayer : rpg.getMiscPlayerManager().getPartyInstance(rpg.getMiscPlayerManager().getPlayerParty(player)).getPlayers()) {
                        partyPlayer.teleport(location);
                        partyPlayer.sendMessage(ChatColor.WHITE + "Party has been teleported to " + ChatColor.translateAlternateColorCodes('&',fileConfiguration.getString(dataString + "format")) + ChatColor.translateAlternateColorCodes('&',fileConfiguration.getString(dataString + "name")));

                    }
                    if(isScroll) {
                        if(player.getInventory().getItemInMainHand().getAmount() > 1) {
                            int amount = player.getInventory().getItemInMainHand().getAmount()-1;
                            player.getInventory().getItemInMainHand().setAmount(amount);
                        } else {
                            player.getInventory().getItemInMainHand().setAmount(0);
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You are not party leader - only party leader can use teleport scrolls");
                }
            } else {
                player.teleport(location);
                player.sendMessage(ChatColor.WHITE + "You have been teleported to " + ChatColor.translateAlternateColorCodes('&',fileConfiguration.getString(dataString + "format")) + ChatColor.translateAlternateColorCodes('&',fileConfiguration.getString(dataString + "name")));
                if(isScroll) {
                    if(player.getInventory().getItemInMainHand().getAmount() > 1) {
                        int amount = player.getInventory().getItemInMainHand().getAmount()-1;
                        player.getInventory().getItemInMainHand().setAmount(amount);
                    } else {
                        player.getInventory().getItemInMainHand().setAmount(0);
                    }
                }
            }
        } else {
            player.teleport(location);
            player.sendMessage(ChatColor.WHITE + "You have been teleported to " + ChatColor.translateAlternateColorCodes('&',fileConfiguration.getString(dataString + "format")) + ChatColor.translateAlternateColorCodes('&',fileConfiguration.getString(dataString + "name")));
            if(isScroll) {
                if(player.getInventory().getItemInMainHand().getAmount() > 1) {
                    int amount = player.getInventory().getItemInMainHand().getAmount()-1;
                    player.getInventory().getItemInMainHand().setAmount(amount);
                } else {
                    player.getInventory().getItemInMainHand().setAmount(0);
                }
            }
        }


    }

    public static ArrayList<AbilityType> getAbilitiesList(Player player, Rpg rpg) {
        YamlConfiguration modifyYaml = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
        ArrayList<String> abilityStringList = (ArrayList<String>) modifyYaml.getStringList("abilities");
        ArrayList<AbilityType> abilityList = new ArrayList<>();

        for(AbilityType ability : AbilityType.values()) {
            if(abilityStringList.contains(ability.name().toLowerCase(Locale.ROOT))) {
                abilityList.add(ability);
            }
        }
        return abilityList;
    }


    public static int getAbilityScore(Rpg rpg, Player player, AbilityScores abilityScore) {

        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        String abilityScoreName = abilityScore.name().toUpperCase(Locale.ROOT);

        if(yamlConfiguration.contains("ability_scores." + abilityScoreName + ".value")) {
            System.out.println("[DEBUG] FETCHING YAML VALUE: ability_scores." + abilityScoreName + ".value ; for " + player.getName());
            return yamlConfiguration.getInt("ability_scores." + abilityScoreName + ".value");
        }
        return 0;
    }

    public static int getAbilityScoreModifier(Rpg rpg, Player player, AbilityScores abilityScore) {

        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        String abilityScoreName = abilityScore.name().toUpperCase(Locale.ROOT);

        if(yamlConfiguration.contains("ability_scores." + abilityScoreName + ".modifier")) {
            return yamlConfiguration.getInt("ability_scores." + abilityScoreName + ".modifier");
        }
        return -1;
    }

    public static ArrayList<AbilityType> getAbilities(Player player, Rpg rpg) {

        return getAbilitiesList(player,rpg);
    }

    public static int calculateAbilityScoreModifier(int value) {
        if(value > 0) {
            if (value % 2 == 0) {

                // If remainder is zero then this number is even
                return (value-10)/2;
            }

            else {

                // If remainder is not zero then this number is odd
                return (value-11)/2;
            }
        }

        return -5;
    }

    public static ClassList getPlayerClass(Player player, Rpg rpg) {

        YamlConfiguration modifyYaml = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        String className = modifyYaml.getString("player_class");

        if(className == null) {
            return null;
        }

        for(ClassList classList : ClassList.values()) {
            if(className.equalsIgnoreCase(classList.name())) {

             return classList;

            }
        }


        return null;
    }

    public static HashMap<String, AbilityType> getPlayerCombos(Player player, Rpg rpg) {
        YamlConfiguration comboConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getComboModifyYaml();
        HashMap<String, AbilityType> combos = new HashMap<>();

        for(AbilityType abilityType : AbilityType.values()) {
            String abilityName = abilityType.name().toLowerCase(Locale.ROOT);
            if(comboConfiguration.contains("combos." + abilityName)) {
                String clicks = comboConfiguration.getString("combos." + abilityName + ".click");
                combos.put(clicks,abilityType);
            }
        }

        return combos;
    }

    public static int calculateAbilityScoreCaps(int level, Rpg rpg) {
        String path1 = "ability-score-caps.";
        int abilityCap = 20;
        Configuration config = rpg.getConfigManager().getConfiguration();

        if (level >= 20) {
            abilityCap = config.getInt(path1 + "20");
        } else if (level >= 15) {
            abilityCap = config.getInt(path1 + "15");
        } else if (level >= 10) {
            abilityCap = config.getInt(path1 + "10");
        } else if (level >= 5) {
            abilityCap = config.getInt(path1 + "5");
        } else {
            switch (level) {
                case 4:
                    abilityCap = config.getInt(path1 + "4");
                    break;
                case 3:
                    abilityCap = config.getInt(path1 + "3");
                    break;
                case 2:
                    abilityCap = config.getInt(path1 + "2");
                    break;
                case 1:
                    abilityCap = config.getInt(path1 + "1");
                    break;
            }
        }
        return abilityCap;
    }

    public static String getCurrentPlayerProfile(Rpg rpg, Player player) {
        return rpg.getConnectListener().getPlayerProfileInstance(player).getActiveProfile();
    }

    public static void saveLocationToFile(Player player, Rpg rpg) {
        YamlConfiguration modifyYaml = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        if(!modifyYaml.contains("location")) {
            modifyYaml.createSection("location");
            modifyYaml.createSection("location.world");
            modifyYaml.createSection("location.x");
            modifyYaml.createSection("location.y");
            modifyYaml.createSection("location.z");
            modifyYaml.createSection("location.yaw");
            modifyYaml.createSection("location.pitch");
        }

        Location loc = player.getLocation();

        modifyYaml.set("location.world",loc.getWorld().getName());
        modifyYaml.set("location.x",loc.getX());
        modifyYaml.set("location.y",loc.getY());
        modifyYaml.set("location.z",loc.getZ());
        modifyYaml.set("location.yaw",loc.getYaw());
        modifyYaml.set("location.pitch",loc.getPitch());

        try {
            modifyYaml.save(rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location getSavedPlayerLocation(Player player, Rpg rpg) {
        YamlConfiguration modifyYaml = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        if(modifyYaml.contains("location")) {
            return new Location(Bukkit.getWorld(modifyYaml.getString("location.world")),
                    modifyYaml.getDouble("location.x"),
                    modifyYaml.getDouble("location.y"),
                    modifyYaml.getDouble("location.z"),
                    (float) modifyYaml.getDouble("location.yaw"),
                    (float) modifyYaml.getDouble("location.pitch"));
        }
        return null;
    }

    public static void loadPlayerLocation(Player player, Rpg rpg) {
        if(getSavedPlayerLocation(player,rpg) != null) {
            if(getSavedPlayerLocation(player,rpg).getY()>0) {
                player.teleport(getSavedPlayerLocation(player, rpg));
            }
        }
    }

    public static void saveEmptylayerInventory(Player player, Rpg rpg) {

        YamlConfiguration modifyYaml = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        ItemStack[] inv = player.getInventory().getContents();


        for(int i = 0; i < inv.length; i++){ // start iterating into the inv
            modifyYaml.set("inventory." + i, "empty");
        }

        try {
            modifyYaml.save(rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePlayerInventory(Player player, Rpg rpg) {

        YamlConfiguration modifyYaml = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        ItemStack[] inv = player.getInventory().getContents();


        for(int i = 0; i < inv.length; i++){ // start iterating into the inv
            ItemStack item = inv[i]; // getting the itemstack
            if(item == null) modifyYaml.set("inventory." + i, "empty"); // if it's a null itemstack, we save it as a string
            else modifyYaml.set("inventory." + i, item); // else, we save the itemstack
        }

        try {
            modifyYaml.save(rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadPlayerInventory(Player player, Rpg rpg) {

        YamlConfiguration modifyYaml = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        if(modifyYaml.contains("inventory")) {
            ConfigurationSection cs = modifyYaml.getConfigurationSection("inventory");
            if(cs == null) return;
            List<ItemStack> items = new ArrayList<>();
            for(String key : cs.getKeys(false)){
                Object object = cs.get(key);
                if(object instanceof ItemStack) items.add((ItemStack)object);
                else items.add(null);
            }
            ItemStack[] inv = items.toArray(new ItemStack[0]);
            player.getInventory().setContents(inv);
        } else {
            System.out.println("ERROR WITH INVENTORY!");
        }

    }



    public static void learnSpell(Player player, Rpg rpg, SpellList spell,boolean isScroll) {
        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
        String spellName = spell.name().toLowerCase(Locale.ROOT);

        if(yamlConfiguration.contains("spell_list")) {
            List<String> list = yamlConfiguration.getStringList("spell_list");

            if(list.size() < 1) {
                yamlConfiguration.createSection("active_spell");
                yamlConfiguration.createSection("active_spell_right");
                yamlConfiguration.createSection("active_spell_sneak");
                yamlConfiguration.createSection("active_spell_right_sneak");

                yamlConfiguration.set("active_spell",spell.name().toLowerCase(Locale.ROOT));
                yamlConfiguration.set("active_spell_right",spell.name().toLowerCase(Locale.ROOT));
                yamlConfiguration.set("active_spell_sneak",spell.name().toLowerCase(Locale.ROOT));
                yamlConfiguration.set("active_spell_right_sneak",spell.name().toLowerCase(Locale.ROOT));
            }

            if(!list.contains(spellName)) {
                list.add(spellName);
                yamlConfiguration.set("spell_list",list);
                Integer amount = player.getInventory().getItemInMainHand().getAmount() - 1;
                player.getInventory().getItemInMainHand().setAmount(amount);

                saveYaml(player,yamlConfiguration,rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile());

                player.sendMessage("You have learned new spell: " + ChatColor.AQUA + spell.getDisplay());
            } else {
                if(isScroll) {
                    player.sendMessage(ChatColor.RED + "You already know this spell");
                }

            }
        } else {
            yamlConfiguration.createSection("spell_list");

            yamlConfiguration.createSection("active_spell");
            yamlConfiguration.createSection("active_spell_right");
            yamlConfiguration.createSection("active_spell_sneak");
            yamlConfiguration.createSection("active_spell_right_sneak");

            yamlConfiguration.set("active_spell",spell.name().toLowerCase(Locale.ROOT));
            yamlConfiguration.set("active_spell_right",spell.name().toLowerCase(Locale.ROOT));
            yamlConfiguration.set("active_spell_sneak",spell.name().toLowerCase(Locale.ROOT));
            yamlConfiguration.set("active_spell_right_sneak",spell.name().toLowerCase(Locale.ROOT));

            List<String> list = new ArrayList<>();
            list.add(spellName);
            yamlConfiguration.set("spell_list",list);

            Integer amount = player.getInventory().getItemInMainHand().getAmount() - 1;
            player.getInventory().getItemInMainHand().setAmount(amount);

            saveYaml(player,yamlConfiguration,rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile());
            player.sendMessage("You have learned new spell: " + ChatColor.AQUA + spell.getDisplay());
        }
    }



    public static void learnAbility(Player player, Rpg rpg, AbilityType ability) {
        YamlConfiguration modifyYaml = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        player.sendMessage(ChatColor.WHITE + "You have learned new ability: " + ChatColor.AQUA + ability.getName());

        ArrayList<AbilityType> abilities = MiscPlayerManager.getAbilities(player,rpg);

        if(!abilities.contains(ability)) {
            ArrayList<String> abilitiesList = (ArrayList<String>) modifyYaml.getStringList("abilities");
            abilitiesList.add(ability.name().toLowerCase(Locale.ROOT));
            modifyYaml.set("abilities", abilitiesList);
        }
        try {
            modifyYaml.save(rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveYaml(Player player, YamlConfiguration yamlConfiguration, File file) {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNextAttack(AttackType attackType, Player player) {
        nextAttack.put(player.getUniqueId(),attackType);
        damageActive.put(player.getUniqueId(), System.currentTimeMillis() + 25000);
    }

    public AttackType getNextAttack(Player player) {
        if(nextAttack.containsKey(player.getUniqueId()) && damageActive.asMap().containsKey(player.getUniqueId())) {
            AttackType at = nextAttack.get(player.getUniqueId());
            return at;
        }
        return null;
    }

    public static boolean hasAvaliableSlot(Player player){
        Inventory inv = player.getInventory();
        ArrayList<Integer> ignoredSlots = new ArrayList<>();
        ignoredSlots.add(36);
        ignoredSlots.add(37);
        ignoredSlots.add(38);
        ignoredSlots.add(39);
        ignoredSlots.add(40);

        int slot = 0;

        for (ItemStack item: inv.getContents()) {

            if(!ignoredSlots.contains(slot)) {
                if(item == null) {
                    return true;
                } else if(item.getType().equals(Material.AIR)) {
                    return true;
                }
            }
            slot++;
        }
        return false;
    }

    public static void tempClearInventory(Rpg rpg, Player player) {
        savePlayerInventory(player,rpg);
        saveLocationToFile(player,rpg);

        player.getInventory().clear();
    }

    public static boolean getSneakAbilitySetting(Rpg rpg, Player player) {
        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        String path = "ability_config.sneak_ability_mode";
        if(yamlConfiguration.contains(path)) {
            return yamlConfiguration.getBoolean(path);
        }
        return true;
    }

    public static boolean getAutoCastSetting(Rpg rpg, Player player) {
        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        String path = "ability_config.auto_cast";
        if(yamlConfiguration.contains(path)) {
            return yamlConfiguration.getBoolean(path);
        }
        return false;
    }

    public static void setAutoCastSetting(Rpg rpg, Player player, boolean value) {
        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        String path = "ability_config.auto_cast";
        if(yamlConfiguration.contains(path)) {
            yamlConfiguration.createSection(path);
        }
        yamlConfiguration.set(path,value);
        try {
            yamlConfiguration.save(rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void setSneakAbilitySetting(Rpg rpg, Player player, boolean value) {
        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        String path = "ability_config.sneak_ability_mode";
        if(!yamlConfiguration.contains(path)) {
            yamlConfiguration.createSection(path);
        }
        yamlConfiguration.set(path,value);

        try {
            yamlConfiguration.save(rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLightsaberRightClickToggleSetting(Rpg rpg, Player player, boolean value) {
        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        String path = "ability_config.right_click_lightsaber_toggle_mode";
        if(!yamlConfiguration.contains(path)) {
            yamlConfiguration.createSection(path);
        }
        yamlConfiguration.set(path,value);

        try {
            yamlConfiguration.save(rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getLightsaberRightClickToggleSetting(Rpg rpg, Player player) {
        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();

        String path = "ability_config.right_click_lightsaber_toggle_mode";
        if(yamlConfiguration.contains(path)) {
            return yamlConfiguration.getBoolean(path);
        }
        return true;
    }
}
