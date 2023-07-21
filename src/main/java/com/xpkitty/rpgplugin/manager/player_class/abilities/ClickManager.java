// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.player_class.abilities;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ClickManager {

    Rpg rpg;
    HashMap<UUID, ArrayList<ClickType>> clicks = new HashMap<>();
    private Cache<UUID, Long> activeTime = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS).build();
    private Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(100, TimeUnit.MILLISECONDS).build();

    private HashMap<UUID, ArrayList<ClickType>> setClicks = new HashMap<>();

    public ClickManager(Rpg rpg) {
        this.rpg = rpg;
    }






    public boolean hasActiveClicks(Player player) {
        if(clicks.containsKey(player.getUniqueId()) && activeTime.asMap().containsKey(player.getUniqueId())) {
            return true;
        }

        return false;
    }




    public void leftClick(Player player) {
        UUID uuid = player.getUniqueId();
        ArrayList<ClickType> clickTypes = new ArrayList<>();
        if(hasActiveClicks(player)) {
            clickTypes = clicks.get(uuid);

            if(clickTypes.size() > 5) {
                clickTypes = new ArrayList<>();
            }

        }
        activeTime.put(player.getUniqueId(), System.currentTimeMillis() + 5000 );
        clickTypes.add(ClickType.LEFT);
        clicks.put(uuid,clickTypes);

        onUpdate(player);
    }


    public void rightClick(Player player) {
        UUID uuid = player.getUniqueId();
        ArrayList<ClickType> clickTypes = new ArrayList<>();
        if(hasActiveClicks(player)) {
            clickTypes = clicks.get(uuid);

            if(clickTypes.size() > 5) {
                clickTypes = new ArrayList<>();
            }
        }
        if(!cooldown.asMap().containsKey(player.getUniqueId())) {
            activeTime.put(player.getUniqueId(), System.currentTimeMillis() + 5000 );
            cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 100 );
            clickTypes.add(ClickType.RIGHT);
            clicks.put(uuid,clickTypes);
            onUpdate(player);
        }
    }



    public void onUpdate(Player player) {
        ArrayList<ClickType> clickTypes = new ArrayList<>();

        if(clicks.containsKey(player.getUniqueId())) {
            clickTypes = clicks.get(player.getUniqueId());
        }
        String actionBar = "";
        int elements = 0;

        for(ClickType click: clickTypes) {
            if(elements < 1) {
                actionBar = String.valueOf(click.getAbbreviation());
            } else {
                actionBar = actionBar + "-" + click.getAbbreviation();
            }
            elements++;
        }
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a" + actionBar));
        testAbilities(player);
    }



    public ArrayList<ClickType> getKeys(Player player) {
        ArrayList<ClickType> clickTypes = new ArrayList<>();

        if(hasActiveClicks(player)) {
            clickTypes = clicks.get(player.getUniqueId());
            if(clickTypes.size() > 5) {
                clickTypes = new ArrayList<>();
            }
        }
       return clickTypes;
    }



    public String getKeysAsString(Player player) {
        ArrayList<ClickType> clickTypes = getKeys(player);
        int elements = 0;
        String clickList = "";

        for(ClickType click: clickTypes) {
            if(elements < 1) {
                clickList = String.valueOf(click.getAbbreviation());
            } else {
                clickList = clickList + click.getAbbreviation();
            }
            elements++;
        }
        return clickList;
    }



    public void testAbilities(Player player) {
        HashMap<String, AbilityType> combos = MiscPlayerManager.getPlayerCombos(player,rpg);

        for(String key : combos.keySet()) {
            if(key.equalsIgnoreCase(getKeysAsString(player))) {
                if(MiscPlayerManager.getAbilitiesList(player,rpg).contains(combos.get(key))) {
                    new RunAbility(player,rpg,combos.get(key));
                    clicks.remove(player.getUniqueId());
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§f§l" + combos.get(key).getName()));

                }
            }
        }
    }



    public void clearClicks(Player player) {
        clicks.remove(player.getUniqueId());
        activeTime.asMap().remove(player.getUniqueId());
    }


    public void addSetLeftClick(Player player) {
        ArrayList<ClickType> clickList = getSetClicks(player);

        clickList.add(ClickType.LEFT);

        setClicks.put(player.getUniqueId(),clickList);
    }

    public void addSetRightClick(Player player) {
        ArrayList<ClickType> clickList = getSetClicks(player);

        clickList.add(ClickType.RIGHT);

        setClicks.put(player.getUniqueId(),clickList);
    }

    public void resetSetClicks(Player player) {
        setClicks.put(player.getUniqueId(),new ArrayList<>());
    }

    public boolean saveSetClicks(Player player, AbilityType ability) {
        ArrayList<ClickType> clicks = getSetClicks(player);

        YamlConfiguration modifyYaml = rpg.getConnectListener().getPlayerDataInstance(player).getComboModifyYaml();
        File file = rpg.getConnectListener().getPlayerDataInstance(player).getComboFileYaml();

        if(!modifyYaml.contains("combos." + ability.name().toLowerCase(Locale.ROOT) + ".click")) {
            modifyYaml.createSection("combos." + ability.name().toLowerCase(Locale.ROOT) + ".click");
        }

        if(getSetClicksAsString(player)!=null) {
            modifyYaml.set("combos." + ability.name().toLowerCase(Locale.ROOT) + ".click",getSetClicksAsString(player));

            try {
                modifyYaml.save(file);
            } catch (IOException e) {
                e.printStackTrace();
                player.sendMessage(ChatColor.RED + "ERROR! Cannot save file.");
            }
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "Please set combo");
        }

        return false;
    }

    public ArrayList<ClickType> getSetClicks(Player player) {
        if(setClicks.containsKey(player.getUniqueId())) {
            return setClicks.get(player.getUniqueId());
        }

        return new ArrayList<>();
    }

    public String getSetClicksAsString(Player player) {

        String value = "";
        if(getSetClicks(player).size() > 0) {
            for(ClickType click : getSetClicks(player)) {
                if(click.equals(ClickType.LEFT)) {
                    value = value + "L";
                } else if(click.equals(ClickType.RIGHT)) {
                    value = value + "R";
                }
            }
            return value;
        }

        return null;
    }

    public ArrayList<String> getSetClicksAsStringList(Player player, String prefix) {
        ArrayList<String> clickStrings = new ArrayList<>();

        for(ClickType click : getSetClicks(player)) {
            if(click.equals(ClickType.LEFT)) {
                clickStrings.add(prefix + "Left");
            } else if(click.equals(ClickType.RIGHT)) {
                clickStrings.add(prefix + "Right");
            }
        }

        return clickStrings;
    }

    public ArrayList<String> getAbilityClicksAsString(Player player, String prefix, AbilityType ability) {

        YamlConfiguration modifyYaml = rpg.getConnectListener().getPlayerDataInstance(player).getComboModifyYaml();
        String value = "";

        if(modifyYaml.contains("combos." + ability.name().toLowerCase(Locale.ROOT) + ".click")) {
            value = modifyYaml.getString("combos."+ability.name().toLowerCase(Locale.ROOT)+".click");
            ArrayList<String> values = new ArrayList<>();

            for(char character : value.toCharArray()) {
                if(character == 'L') {
                    values.add(prefix + "Left");
                } else if(character == 'R') {
                    values.add(prefix + "Right");
                }
            }
            return values;
        }

        return null;
    }
}
