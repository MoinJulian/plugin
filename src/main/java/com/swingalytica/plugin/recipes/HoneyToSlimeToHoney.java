package com.swingalytica.plugin.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class HoneyToSlimeToHoney implements Listener {

    private final JavaPlugin plugin;
    private final NamespacedKey honey_to_slime_key;
    private final NamespacedKey slime_to_honey_key;

    public HoneyToSlimeToHoney(JavaPlugin plugin) {
        this.plugin = plugin;
        this.honey_to_slime_key = new NamespacedKey(plugin, "honey_to_slime");
        this.slime_to_honey_key = new NamespacedKey(plugin, "slime_to_honey");
    }

    public void register() {
        // Honey to Slime
        ShapelessRecipe honey_to_slime_recipe = new ShapelessRecipe(
                honey_to_slime_key,
                new ItemStack(Material.SLIME_BLOCK)
        );
        honey_to_slime_recipe.addIngredient(Material.HONEY_BLOCK);
        Bukkit.addRecipe(honey_to_slime_recipe);

        // Slime to Honey
        ShapelessRecipe slime_to_honey_recipe = new ShapelessRecipe(
                slime_to_honey_key,
                new ItemStack(Material.HONEY_BLOCK)
        );
        slime_to_honey_recipe.addIngredient(Material.SLIME_BLOCK);
        Bukkit.addRecipe(slime_to_honey_recipe);

        // Register listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        // Unlock recipes for online players
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.discoverRecipe(honey_to_slime_key);
            player.discoverRecipe(slime_to_honey_key);
        });
    }

    public void unregister() {
        Bukkit.removeRecipe(honey_to_slime_key);
        Bukkit.removeRecipe(slime_to_honey_key);

        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().discoverRecipe(honey_to_slime_key);
        event.getPlayer().discoverRecipe(slime_to_honey_key);
    }
}