// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.data.database_data;

public enum DatabaseFileContents {

    USE_SQL("false","boolean"),
    PASSWORD_ENCRYPTED("false","boolean"),
    USERNAME_ENCRYPTED("false","boolean"),
    HOST("","String"),
    PORT("0","int"),
    DATABASE("","String"),
    USERNAME("","String"),
    PASSWORD("","String");

    String value, valueType;

    DatabaseFileContents(String value, String valueType) {
        this.value = value;
        this.valueType = valueType;
    }

    public String getValue() {return value;}
    public String getValueType() {return valueType;}
}
