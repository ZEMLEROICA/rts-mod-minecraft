package com.zemleroica.rtsmod.data;

import java.util.List;

public class Faction {
    public String name;
    public String baseMobName;
    public String centralMobName;
    public List<String> unitPool;
    public int teamColor;

    public Faction(String name, String baseMobName, String centralMobName, List<String> unitPool, int teamColor) {
        this.name = name;
        this.baseMobName = baseMobName;
        this.centralMobName = centralMobName;
        this.unitPool = unitPool;
        this.teamColor = teamColor;
    }

    public Faction() {}
}