package com.xpkitty.rpgplugin.manager.player_class;

public enum ArmourList {

    CLOTHES("Clothes","Clothes",0,0,0),
    LIGHT_ARMOUR("Light Armour","Light Armour",0,0,0),
    MEDIUM_ARMOUR("Medium Armour","Medium Armour",13,0,0),
    HEAVY_ARMOUR("Heavy Armour","Heavy Armour",15,0,0);

    String display,id;
    Integer str,dex,con;

    ArmourList(String display, String id, Integer str, Integer con, Integer dex) {
        this.display=display;
        this.id=id;
        this.str=str;
        this.con=con;
        this.dex=dex;
    }

    public String getDisplay() {return display;}
    public String getId() {return id;}
    public Integer getStr() {return str;}
    public Integer getCon() {return con;}
    public Integer getDex() {return dex;}
}
