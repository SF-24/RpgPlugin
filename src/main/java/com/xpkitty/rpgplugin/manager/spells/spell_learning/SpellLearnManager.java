package com.xpkitty.rpgplugin.manager.spells.spell_learning;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.DiceRollManager;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerSpellFile;
import com.xpkitty.rpgplugin.manager.data.spell_data.MainSpellData;
import com.xpkitty.rpgplugin.manager.hud.EnergyManager;
import com.xpkitty.rpgplugin.manager.skills.PlayerSkillManager;
import com.xpkitty.rpgplugin.manager.skills.PlayerSkills;
import com.xpkitty.rpgplugin.manager.spells.enum_list.HpSpellsList;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellCastType;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellCastTypeForLearnedSpell;
import com.xpkitty.rpgplugin.manager.spells.spell_ui.SpellIcon;
import com.xpkitty.rpgplugin.manager.spells.wand.SpellChecker;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.*;

public class SpellLearnManager {

    public static void loadCastTest(Rpg rpg, Player player, List<Integer> wandMovement, Vector dir, boolean isQuickCasted) {

        String spell = "stupefy";
        boolean isHardCoded = false;
        boolean foundSpell = false;

        for(HpSpellsList element : HpSpellsList.values()) {
            if(element.getWandMovement().equals(wandMovement)) {
                isHardCoded=true;
                spell=element.name().toLowerCase(Locale.ROOT);
                foundSpell=true;
                break;
            }
        }

        if(!foundSpell) {
            MainSpellData mainSpellData = rpg.getMainSpellData();
            YamlConfiguration yml = mainSpellData.getModifyYaml();

            for(String str : yml.getConfigurationSection("spells").getKeys(false)) {
                List<Integer> intList = yml.getIntegerList("spells."+str+".movement");
                if(intList.equals(wandMovement)) {
                    foundSpell=true;
                    spell=yml.getString("spells."+str+".incantation");
                }
            }
        }

        if(foundSpell) {
            attemptCast(rpg,player,spell,isHardCoded,wandMovement,dir, isQuickCasted);
        }


    }

    public static void loadCastTest(Rpg rpg, Player player, int id, boolean isQuickCasted) {

        String spell = "stupefy";
        boolean isHardCoded = false;
        boolean foundSpell = false;

        List<Integer> wandMovement = Collections.emptyList();

        for(HpSpellsList element : HpSpellsList.values()) {
            if(element.getNumericId() == id) {
                isHardCoded=true;
                spell=element.name().toLowerCase(Locale.ROOT);
                foundSpell=true;
                wandMovement = element.getWandMovement();
                break;
            }
        }

        if(!foundSpell) {
            MainSpellData mainSpellData = rpg.getMainSpellData();
            YamlConfiguration yml = mainSpellData.getModifyYaml();

            for(String str : yml.getConfigurationSection("spells").getKeys(false)) {
                int v = Integer.parseInt(str);

                if(v == id) {
                    foundSpell=true;
                    spell=yml.getString("spells."+v+".incantation");
                    wandMovement = yml.getIntegerList("spells."+v+".movement");
                }
            }
        }


        if(foundSpell) {
            attemptCast(rpg,player,spell,isHardCoded,wandMovement,player.getLocation().getDirection(), isQuickCasted);
        }


    }



    public static void attemptCast(Rpg rpg, Player player, String spell, boolean isHardCoded, List<Integer> wandMovement, Vector dir, boolean isQuickCasted) {


        PlayerSpellFile playerSpellFile = rpg.getConnectListener().getPlayerSpellFile(player);

        boolean custom = !isHardCoded;

        if(playerSpellFile!=null) {

            YamlConfiguration yamlConfiguration = playerSpellFile.getModifySpellFile();

            String path1 = "spells."+spell.toLowerCase(Locale.ROOT);

            // GENERATE SPELL DATA IN YAML SPELL DATA FILE FOR PLAYER
            if(!yamlConfiguration.contains(path1)) {
                yamlConfiguration.createSection(path1);

                String path = "spells."+spell.toLowerCase(Locale.ROOT)+".";
                yamlConfiguration.createSection(path+"learned");
                yamlConfiguration.set(path+"learned",false);

                yamlConfiguration.createSection(path+"level");
                yamlConfiguration.set(path+"level",1);
                yamlConfiguration.createSection(path+"learning_progress");
                yamlConfiguration.set(path+"learning_progress",0);
                yamlConfiguration.createSection(path+"progress");
                yamlConfiguration.set(path+"progress",0);

                yamlConfiguration.createSection(path+"slot");
                yamlConfiguration.set(path+"slot",0);
                yamlConfiguration.createSection(path+"bar");
                yamlConfiguration.set(path+"bar",0);

                yamlConfiguration.createSection(path+"custom");
                yamlConfiguration.set(path+"custom",custom);
                yamlConfiguration.createSection(path+"player_made");
                yamlConfiguration.set(path+"player_made",false);
            }
            try {
                yamlConfiguration.save(playerSpellFile.getSpellFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // DECLARE VARIABLES



            //player INT and WIS modifier
            int primarySpellCastingMod = MiscPlayerManager.getAbilityScoreModifier(rpg,player,PlayerSkills.SPELL_CASTING.getBaseAbilityScore());
            int secondarySpellCastingMod = MiscPlayerManager.getAbilityScoreModifier(rpg,player,PlayerSkills.SPELL_CASTING.getSecondaryAbilityScore());

            //if player has learned spell
            boolean learnedSpell = yamlConfiguration.getBoolean(path1+".learned");

            // Required spell energy - unset
            int requiredEnergy = 0;

            //create new random
            Random random = new Random();

            // spell progress variable:
            // if player has learned spell run it
            // otherwise set spell progress to learning spell progress
            int currentSpellProgress = 0;
            if(learnedSpell) {
                attemptCastLearnedSpell(rpg,player,spell,isHardCoded,wandMovement,dir, isQuickCasted);
                return;
            } else {
                currentSpellProgress = yamlConfiguration.getInt(path1+".learning_progress");
            }

            //Calculate spell progress modifier
            int spellProgressModifier = MiscPlayerManager.calculateAbilityScoreModifier(currentSpellProgress);

            //Generate random number from 0 to 20
            int castRoll = DiceRollManager.getSpellLearnDiceRoll();
            int originalCastRoll = castRoll;

            //Add INT and WIS modifier to castRoll
            castRoll+=(primarySpellCastingMod*2)+secondarySpellCastingMod;

            //Calculate spell difficulty, incantation and name
            String incantation = "";
            String name = "";
            int spellDifficulty = 0;
            if(isHardCoded) {
                for(HpSpellsList element : HpSpellsList.values()) {
                    if(element.getWandMovement().equals(wandMovement)) {
                        spellDifficulty=element.getXpToLearn();
                        incantation=element.getDisplay();
                        name=element.getDescription();
                        requiredEnergy=element.getManaCost();
                    }
                }
            } else {
                MainSpellData mainSpellData = rpg.getMainSpellData();
                YamlConfiguration mainSpellDataFile = mainSpellData.getModifyYaml();

                for(String element : mainSpellDataFile.getConfigurationSection("spells").getKeys(false)) {
                    String mainPath = "spells." + element + ".";

                    List<Integer> intList = mainSpellDataFile.getIntegerList(mainPath+"movement");

                    if(intList.equals(wandMovement)) {
                        spellDifficulty=mainSpellDataFile.getInt(mainPath+"exp.learn");
                        incantation=mainSpellDataFile.getString(mainPath+"incantation");
                        name=mainSpellDataFile.getString(mainPath+"name");

                        // get required spell energy (mana)
                        requiredEnergy = yamlConfiguration.getInt(mainPath+"energy_cost");
                    }
                }
            }

            // SPELL EFFECT AND FUNCTION

            // Test for spell effect
            SpellCastType spellCastType = SpellCastType.FIZZLES;

            if(castRoll<spellDifficulty) {

                //SPELL FAILS
                if(castRoll<spellDifficulty-10) {
                    spellCastType=SpellCastType.FAILS;
                }
                //SEMI_SUCCESS
                if(castRoll>=spellDifficulty-2) {
                    spellCastType=SpellCastType.SEMI_SUCCESS;
                }
            } else {
                //SUCCESS
                spellCastType=SpellCastType.SUCCESS;
            }



            if(currentSpellProgress>=spellDifficulty) {
                spellCastType=SpellCastType.SUCCESS;
            }

            if(requiredEnergy>0) {
                if (requiredEnergy < EnergyManager.getEnergyCount(rpg, player)) {
                    spellCastType = SpellCastType.FAILS;
                }
                EnergyManager.takeEnergy(rpg, player, requiredEnergy);
            }


            // DEBUG LEARN SPELL
            System.out.println("");
            System.out.println("ATTEMPTING TO CAST SPELL:");
            System.out.println("mana: " + requiredEnergy + " | " + playerSpellFile.getCurrentEnergy());
            System.out.println("original cast roll: " + originalCastRoll);
            System.out.println("cast roll: " + castRoll + " | " + spellDifficulty);
            System.out.println("");


            // ELSE SPELL FIZZLES




            // ON SPELL SUCCESS LEARN SPELL
            if(spellCastType.equals(SpellCastType.SUCCESS)) {
                SpellChecker.CheckHpSpell(player,rpg,wandMovement,dir,castRoll);
                learnSpell(rpg,player,spell,incantation,name,wandMovement,isHardCoded);
                return;
            }

            // CALCULATE SPELL PROGRESS EXP TO GIVE PLAYER
            int addToRand = spellCastType.getMinimumExp();
            int addToLimit = spellCastType.getMaximumExp();

            int randExp = random.nextInt(addToLimit+1-addToRand)+addToRand;

            // SEND TEXT MESSAGE
            player.sendMessage(ChatColor.BOLD.toString() + ChatColor.BLUE + "+" + randExp + " EXP for spell " + name);

            // GIVE PLAYER SPELL PROGRESS EXP
            int expToSet = randExp+currentSpellProgress;
            yamlConfiguration.set(path1+".learning_progress",expToSet);

            // SAVE PLAYER YML SPELL DATA FILE
            try {
                yamlConfiguration.save(playerSpellFile.getSpellFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // SPELL EFFECT WHEN NOT SUCCESS
            fizzleSpell(rpg,player,spell,incantation,isHardCoded,spellCastType);

        } else {
            // ON ERROR
            System.out.println("ERROR! " + player.getName() + "'s PlayerSpellFile is NULL");
        }
    }


    public static void attemptCastLearnedSpell(Rpg rpg, Player player, String spell, boolean isHardCoded, List<Integer> wandMovement, Vector dir, boolean isQuickCasted) {
        spell=spell.toLowerCase(Locale.ROOT);

        // Get player spell file
        PlayerSpellFile playerSpellFile = rpg.getConnectListener().getPlayerSpellFile(player);
        YamlConfiguration playerSpellYaml = playerSpellFile.getModifySpellFile();

        // Declare variables

        // Spell name and incantation - unset
        String spellName = "";
        String incantation = "";

        // Required spell energy - unset
        int requiredEnergy = 0;

        // Required dice roll to cast spell
        int castDifficulty = 15;

        // max spell level - unset
        int maxLevel = 1;

        // leveling xp - unset
        List<Integer> levelingXp = new ArrayList<>();

        // spell difficulty - unset
        int learnDifficulty = 0;

        //player INT and WIS modifier
        int primarySpellCastingMod = MiscPlayerManager.getAbilityScoreModifier(rpg,player,PlayerSkills.SPELL_CASTING.getBaseAbilityScore());
        int secondarySpellCastingMod = MiscPlayerManager.getAbilityScoreModifier(rpg,player,PlayerSkills.SPELL_CASTING.getSecondaryAbilityScore());

        //player spell-casting skill
        int playerSpellCastingSkillMod = PlayerSkillManager.getPlayerSkillMod(rpg, player, PlayerSkills.SPELL_CASTING);

        // Set variables - varies if spell is hard coded or not
        if(isHardCoded) {
            // if spell is hard coded
            for(HpSpellsList element : HpSpellsList.values()) {
                if(wandMovement.equals(element.getWandMovement())) {

                    // get spell name and incantation
                    incantation = element.getDisplay();
                    spellName = element.getDescription();

                    // get required spell energy (mana)
                    requiredEnergy = element.getManaCost();

                    // get minimum dice roll to cast
                    castDifficulty = element.getCastDifficulty();

                    // get maximum spell level
                    maxLevel = element.getMaximumLevel();

                    // get leveling EXP
                    levelingXp = element.getLevelingXp();

                    // get spell difficulty
                    learnDifficulty = element.getXpToLearn();
                }
            }
        } else {

            // if spell is not hard coded
            YamlConfiguration yamlConfiguration = rpg.getMainSpellData().getModifyYaml();

            for(String id : yamlConfiguration.getConfigurationSection("spells").getKeys(false)) {
                // get wand movement and compare to spell wand movement
                List<Integer> intList = yamlConfiguration.getIntegerList("spells."+id+".movement");

                // if is same spell
                if(intList.equals(wandMovement)) {
                    String path = "spells." + id + ".";

                    // get incantation and spell name
                    incantation = yamlConfiguration.getString(path+"incantation");
                    spellName = yamlConfiguration.getString(path+"name");

                    // get roll requirement to cast spell
                    castDifficulty = yamlConfiguration.getInt(path+"difficulty");

                    // get required spell energy (mana)
                    requiredEnergy = yamlConfiguration.getInt(path+"energy_cost");

                    // get max spell level
                    maxLevel = yamlConfiguration.getInt(path+"exp.max_level");

                    // get EXP per level
                    if(maxLevel>1 && yamlConfiguration.contains(path+"exp.level_up")) {
                        for(int i = 2; i <= maxLevel; i++) {
                            levelingXp.add(i-2, yamlConfiguration.getInt("exp.level_up."+i));
                        }
                    }

                    // get spell difficulty
                    learnDifficulty = yamlConfiguration.getInt(path+"exp.learn");
                }
            }
        }

        // Get current spell progress
        String path = "spells." + spell + ".";


        // get player's level of spell and spell EXP
        int spellLevel = playerSpellYaml.getInt(path+"level");
        int xp = playerSpellYaml.getInt(path+"progress");

        // create variable for earned spell xp
        int gainedExp = 0;

        // spell result
        SpellCastType result = SpellCastType.FIZZLES;
        SpellCastTypeForLearnedSpell learnedSpellResult = SpellCastTypeForLearnedSpell.FIZZLED;

        // create new random
        Random random = new Random();

        // Roll virtual dice and add modifiers
        int spellCastRoll = DiceRollManager.getSpellDiceRoll();


        spellCastRoll+= primarySpellCastingMod*2 + secondarySpellCastingMod;


        spellCastRoll+=spellLevel*3;


        spellCastRoll+=playerSpellCastingSkillMod;

        int energyBonus = EnergyManager.getEnergyCount(rpg,player)-requiredEnergy;
        if(energyBonus>0) {
            spellCastRoll+=energyBonus;
        } else if(energyBonus<0) {
            spellCastRoll-=energyBonus*2;
        }

        if(!isQuickCasted) {
            spellCastRoll+=5;
        }

        System.out.println("Player spell cast dice roll: " + (spellCastRoll));

        // calculate spell cast type
        if(spellCastRoll>=castDifficulty) {
            if(EnergyManager.getEnergyCount(rpg,player)>=requiredEnergy) {
                result = SpellCastType.SUCCESS;

                for (SpellCastTypeForLearnedSpell element : SpellCastTypeForLearnedSpell.values()) {
                    if (spellCastRoll >= element.getRequiredRoll() + castDifficulty) {
                        learnedSpellResult = element;
                    }
                }
            }
            EnergyManager.takeEnergy(rpg,player,requiredEnergy);
        }

        // CALCULATE SPELL PROGRESS EXP TO GIVE PLAYER
        int addToRand = learnedSpellResult.getMinExp();
        int addToLimit = learnedSpellResult.getMaxExp();
        gainedExp = random.nextInt(addToLimit+1-addToRand)+addToRand;


        // FIZZLE SPELL IF NOT SUCCESS
        if(!result.equals(SpellCastType.SUCCESS)) {
            fizzleSpell(rpg,player,spell,incantation,isHardCoded,result);
            return;
        }

        if(spellCastRoll<15) spellCastRoll=15;

        // cast spell if cast is successful
        SpellChecker.CheckHpSpell(player,rpg,wandMovement,dir,spellCastRoll);

        // GIVE PLAYER SPELL EXP
        givePlayerLearnedSpellExp(rpg,player,isHardCoded,spell,spellName,incantation,gainedExp,maxLevel,levelingXp);

    }


    public static void learnSpell(Rpg rpg, Player player, String spell, String incantation, String spellName, List<Integer> wandMovements, boolean isHardCoded) {

        PlayerSpellFile playerSpellFile = rpg.getConnectListener().getPlayerSpellFile(player);
        YamlConfiguration yamlConfiguration = playerSpellFile.getModifySpellFile();
        String path = "spells."+spell.toLowerCase(Locale.ROOT);

        if(!yamlConfiguration.getBoolean(path + ".learned")) {
            yamlConfiguration.set(path+".learned",true);
            yamlConfiguration.set(path+".level",1);
        }

        //player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText("§l§b" + "Spell Learned: " + "§r§l§6" + spellName));

        ItemStack spellIcon = SpellIcon.getSpellIconItem(rpg,incantation);

        player.sendMessage("");
        player.sendMessage(ChatColor.AQUA + "Spell learned: " + ChatColor.GOLD + ChatColor.BOLD + spellName);
        player.sendMessage();

        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.5f);
        player.sendMessage(ChatColor.AQUA+ "Spell learned: " + ChatColor.GOLD + spellName);

        try {
            yamlConfiguration.save(playerSpellFile.getSpellFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void fizzleSpell(Rpg rpg, Player player, String spell, String incantation, boolean isHardCoded, SpellCastType spellCastType) {

        if(spellCastType.equals(SpellCastType.FIZZLES)) {
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS,1.0f,1.0f);
            player.spigot().sendMessage(ChatMessageType.CHAT, TextComponent.fromLegacyText("§c§l" + "Your spell fizzled"));
        } else if(spellCastType.equals(SpellCastType.FAILS)) {
            player.spigot().sendMessage(ChatMessageType.CHAT, TextComponent.fromLegacyText("§c§l" + "Your spell failed"));
        } else if(spellCastType.equals(SpellCastType.SEMI_SUCCESS)) {
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS,1.0f,1.0f);
            player.spigot().sendMessage(ChatMessageType.CHAT, TextComponent.fromLegacyText("§c§l" + "Your spell fizzled"));
        }

        for(Player player2 : Bukkit.getOnlinePlayers()) {
            if(player.getLocation().distance(player2.getLocation()) <= 20) {
                player2.sendMessage("<" + player.getDisplayName() + "> " + incantation);
            }
        }
    }

    public static void givePlayerLearnedSpellExp(Rpg rpg, Player player, boolean isHardCoded, String spell, String name, String incantation, int amount, int maxSpellLevel, List<Integer> levelingXp) {
        if(amount>0) {
            PlayerSpellFile playerSpellFile = rpg.getConnectListener().getPlayerSpellFile(player);

            if (playerSpellFile != null) {


                // get player yaml file
                YamlConfiguration yamlConfiguration = playerSpellFile.getModifySpellFile();

                // set path variable
                String path = "spells." + spell + ".";

                // Get player spell xp and level
                int xp = yamlConfiguration.getInt(path + "progress");
                int startingLevel = yamlConfiguration.getInt(path + "level");
                int level = yamlConfiguration.getInt(path + "level");

                if (maxSpellLevel > 1 && level < maxSpellLevel) {

                    // add amount to spell learning xp
                    xp += amount;

                    // calculate new lvl and xp

                    int index = level - 1;

                    if (index < 0) index = 0;

                    player.sendMessage(String.valueOf(levelingXp) + " index " + index);

                    int xpTNL = levelingXp.get(index);


                    if (xp >= xpTNL) {
                        level++;
                        xp = xp - xpTNL;
                        player.sendMessage(startingLevel + " -> " + level);


                    }
                    System.out.println("level:" + level + " maximum:" + maxSpellLevel);


                    // SEND TEXT MESSAGE
                    player.sendMessage(ChatColor.BOLD.toString() + ChatColor.BLUE + "+" + amount + " EXP for spell " + name);

                    if (level > startingLevel) {
                        // Player level up sound
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.5f);

                        // send player level up message
                        if (level > startingLevel + 1) {
                            player.spigot().sendMessage(ChatMessageType.CHAT, TextComponent.fromLegacyText("§l§b" + "Spell Leveled Up: " + "§r§l§6" + name + " §l§b| §r§9" + startingLevel + "§r§c->§r§9" + level));
                        } else {
                            player.spigot().sendMessage(ChatMessageType.CHAT, TextComponent.fromLegacyText("§l§b" + "Spell Leveled Up: " + "§r§l§6" + name));
                        }
                    }

                    // update values
                    yamlConfiguration.set(path + "progress", xp);
                    yamlConfiguration.set(path + "level", level);

                    // save spell data file
                    try {
                        yamlConfiguration.save(playerSpellFile.getSpellFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}


