// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.data.database_data;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Locale;

public class DatabaseConfigManager {

    Rpg rpg;

    YamlConfiguration yamlConfiguration;
    File file;

    public DatabaseConfigManager(Rpg rpg) {
        this.rpg=rpg;
        InitiateFiles();
    }

    private void InitiateFiles() {

        File databaseConfig = new File(rpg.getDataFolder() + File.separator + "database_config.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(databaseConfig);

        if(!databaseConfig.exists()) {
            try {
                databaseConfig.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(DatabaseFileContents databaseFileContents : DatabaseFileContents.values()) {
            if(!yamlConfiguration.contains(databaseFileContents.name().toLowerCase(Locale.ROOT))) {
                yamlConfiguration.createSection(databaseFileContents.name().toLowerCase(Locale.ROOT));

                if(databaseFileContents.getValueType().equalsIgnoreCase("String")) {
                    yamlConfiguration.set(databaseFileContents.name().toLowerCase(Locale.ROOT),databaseFileContents.getValue());
                } else if(databaseFileContents.getValueType().equalsIgnoreCase("boolean")) {
                    if(databaseFileContents.getValue().equalsIgnoreCase("false")) {
                        yamlConfiguration.set(databaseFileContents.name().toLowerCase(Locale.ROOT),false);
                    } else if(databaseFileContents.getValue().equalsIgnoreCase("true")) {
                        yamlConfiguration.set(databaseFileContents.name().toLowerCase(Locale.ROOT),true);
                    }
                } else if(databaseFileContents.getValueType().equalsIgnoreCase("int")) {
                    int val = Integer.parseInt(databaseFileContents.getValue());
                    yamlConfiguration.set(databaseFileContents.name().toLowerCase(Locale.ROOT),val);
                }
            }
        }

        this.file = databaseConfig;
        this.yamlConfiguration = yamlConfiguration;

        if(yamlConfiguration.getString("password").length()>0 && (yamlConfiguration.getBoolean("use_sql") && !yamlConfiguration.getBoolean("password_encrypted"))) {
            System.out.println("");
            System.out.println("PASSWORD IS NOT ENCRYPTED - ENCRYPTING");
            System.out.println("");

            String pass = yamlConfiguration.getString("password");
            String encrypted = Base64.getEncoder().encodeToString(pass.getBytes());

            yamlConfiguration.set("password", encrypted);
            yamlConfiguration.set("password_encrypted", true);

        }

        if(yamlConfiguration.getString("username").length()>0 && (yamlConfiguration.getBoolean("use_sql") && !yamlConfiguration.getBoolean("username_encrypted"))) {
            System.out.println("");
            System.out.println("USERNAME IS NOT ENCRYPTED - ENCRYPTING");
            System.out.println("");

            String pass = yamlConfiguration.getString("username");
            String encrypted = Base64.getEncoder().encodeToString(pass.getBytes());

            yamlConfiguration.set("username", encrypted);
            yamlConfiguration.set("username_encrypted", true);
        }




        try {
            yamlConfiguration.save(databaseConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getDatabaseYamlConfiguration() {return yamlConfiguration;}
    public File getDatabaseYamlFile() {return file;}

}
