package com.swingalytica.plugin.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class HoneyStickyPiston implements Listener {

    private final JavaPlugin plugin;
    private final NamespacedKey key;

    public HoneyStickyPiston(JavaPlugin plugin) {
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, "sticky_piston_honey");
    }

    public void register() {
        ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.STICKY_PISTON));
        recipe.addIngredient(Material.PISTON);
        recipe.addIngredient(Material.HONEY_BOTTLE);
        Bukkit.addRecipe(recipe);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        // Spieler, die schon online sind (z. B. nach /reload)
        Bukkit.getOnlinePlayers().forEach(p -> p.discoverRecipe(key));
    }

    public void unregister() {
        Bukkit.removeRecipe(key);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().discoverRecipe(key);
    }
}