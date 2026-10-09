package com.swingalytica.plugin;

import com.swingalytica.plugin.commands.EnderchestCommand;
import com.swingalytica.plugin.commands.FlyCommand;
import com.swingalytica.plugin.homes.HomeCommand;
import com.swingalytica.plugin.homes.HomeManager;
import com.swingalytica.plugin.recipes.HoneyStickyPiston;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    private HoneyStickyPiston honeyStickyPiston;

    @Override
    public void onEnable() {
        HomeManager homeManager = new HomeManager(this);

        honeyStickyPiston = new HoneyStickyPiston(this);
        honeyStickyPiston.register();

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("fly", "Toggles flight", new FlyCommand());
            commands.register("ec", "Opens enderchest", new EnderchestCommand());

            commands.register("sethome", "Sets a home point", new HomeCommand(homeManager, "sethome"));
            commands.register("home", "Teleports to a home point", new HomeCommand(homeManager, "home"));
            commands.register("delhome", "Deletes a home point", new HomeCommand(homeManager, "delhome"));
            commands.register("homes", "Lists your homes", new HomeCommand(homeManager, "homes"));
        });
    }

    @Override
    public void onDisable() {
        if (honeyStickyPiston!= null) {
            honeyStickyPiston.unregister();
        }
    }
}