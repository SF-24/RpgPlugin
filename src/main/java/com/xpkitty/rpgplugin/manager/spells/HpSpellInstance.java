package com.xpkitty.rpgplugin.manager.spells;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellType;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class HpSpellInstance {
    String name;
    String incantation;
    Rpg rpg;
    int id;

    private File spellFile;
    private YamlConfiguration modifySpellFile;

    public HpSpellInstance(Rpg rpg, String name,String incantation, int id) {
        this.name = name;
        this.rpg = rpg;
        this.id = id;
        this.incantation=incantation;
        initialize();
        generateTemplate();
    }


    public void generateTemplate() {

        makeSection("spell_name",name,"");
        makeSection("incantation",incantation,"");
        makeSection("spell_type", SpellType.JINX.getDisplay(),"");

        makeSection("volume", "20","int");

        makeSection("function","ENTITY_DAMAGE","");
        makeSection("val1","1","");
        makeSection("val2","","");
        makeSection("val3","","");
        makeSection("target","ENTITY","");
        makeSection("speed","2","int");
        makeSection("fly_distance","50","int");

        makeSection("particle.color.red","255","int");
        makeSection("particle.color.green","0","int");
        makeSection("particle.color.blue","0","int");
        makeSection("particle.size","1","int");
        makeSection("particle.count","5","int");
        makeSection("particle.type","REDSTONE","");
    }

    public void makeSection(String path, String value,String type) {
        int val=0;
        if(type.equalsIgnoreCase("int")||type.equalsIgnoreCase("integer")) {
            val = Integer.parseInt(value);
        }

        if(!modifySpellFile.contains(path)) {
            modifySpellFile.createSection(path);
            if(type.equalsIgnoreCase("int")||type.equalsIgnoreCase("integer")) {
                modifySpellFile.set(path,val);
            } else {
                modifySpellFile.set(path,value);
            }
        }
        try {
            modifySpellFile.save(getSpellFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSpell() {
        //TODO

        saveFile(modifySpellFile, spellFile);
    }









    public void initialize() {
        String path = rpg.getDataFolder() + File.separator + "Data" + File.separator + "Spells";
        spellFile = new File(path, id +".yml");
        File dir = spellFile.getParentFile();

        if(!dir.exists()) dir.mkdirs();
        if(!spellFile.exists()) {
            try { spellFile.createNewFile(); }
            catch (IOException e) {e.printStackTrace();}
        }

        modifySpellFile = YamlConfiguration.loadConfiguration(spellFile);


        try { modifySpellFile.save(spellFile); }
        catch (IOException e) { e.printStackTrace(); }
    }


    public void saveFile(YamlConfiguration yamlConfiguration, File file) {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[AnotherRpgPlugin] Saved Spell data file for id: " + id);
    }

    public File getSpellFile() { return spellFile; }
    public YamlConfiguration getModifySpellFile() { modifySpellFile = YamlConfiguration.loadConfiguration(spellFile); return modifySpellFile; }


}
