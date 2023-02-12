package com.xpkitty.rpgplugin.manager;

import java.util.ArrayList;
import java.util.Arrays;

public enum ResourcePack {

    NULL("https://www.dropbox.com/s/p9j9zr52c7oc1hz/NoResource.zip?dl=1",new ArrayList<>()),
    DEFAULT("https://www.dropbox.com/s/ap3l9czm6amyksy/KittensCraft%20RPG-0.19-release.zip?dl=1", new ArrayList<>(Arrays.asList("Hogwarts","Flat","Utumno"))),
    TATOOINE("https://www.dropbox.com/s/0bezvcscz0qi8qx/KittensCraft%20RPG%20DesertEssentials%200.17.7-release.zip?dl=1",new ArrayList<>(Arrays.asList("Tatooine","Bespin")));

    String url;
    ArrayList<String> worldName;

    ResourcePack(String url, ArrayList<String> worldName) {
        this.url = url;
        this.worldName = worldName;
    }

    public String getUrl() {return url;}
    public ArrayList<String> getWorlds() {return worldName;}
}
