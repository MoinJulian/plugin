package com.swingalytica.plugin.homes;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class HomeManager {

    private final JavaPlugin plugin;
    private final File file;
    private final FileConfiguration config;

    public HomeManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "homes.yml");
        if (!file.exists()) {
            plugin.getDataFolder().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void setHome(Player player, String name, Location loc) {
        String path = player.getUniqueId() + "." + name;
        config.set(path, loc);
        save();
    }

    public Location getHome(Player player, String name) {
        String path = player.getUniqueId() + "." + name;
        return (Location) config.get(path);
    }

    public boolean deleteHome(Player player, String name) {
        String path = player.getUniqueId() + "." + name;
        if (config.get(path) == null) return false;
        config.set(path, null);
        save();
        return true;
    }

    public Set<String> listHomes(Player player) {
        var section = config.getConfigurationSection(player.getUniqueId().toString());
        return section != null ? section.getKeys(false) : Collections.emptySet();
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}