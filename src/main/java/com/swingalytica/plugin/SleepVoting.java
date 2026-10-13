package com.swingalytica.plugin;

import org.bukkit.GameRules;
import org.bukkit.World;
import org.bukkit.Bukkit;

public class SleepVoting {

    private final int percentage;

    public SleepVoting(int percentage) {
        this.percentage = percentage;
    }

    public void register() {
        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRules.PLAYERS_SLEEPING_PERCENTAGE, percentage);
        }
    }
}