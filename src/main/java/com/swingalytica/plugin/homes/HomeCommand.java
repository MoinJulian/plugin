package com.swingalytica.plugin.homes;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements BasicCommand {

    private final HomeManager homeManager;
    private final String action;

    public HomeCommand(HomeManager homeManager, String action) {
        this.homeManager = homeManager;
        this.action = action;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command.");
            return;
        }

        String name = args.length > 0 ? args[0] : "home";

        switch (action) {
            case "sethome" -> {
                homeManager.setHome(player, name, player.getLocation());
                player.sendMessage("§aHome '" + name + "' set.");
            }
            case "home" -> {
                Location loc = homeManager.getHome(player, name);
                if (loc == null) {
                    player.sendMessage("§cNo home found with the name '" + name + "'.");
                    return;
                }
                player.teleport(loc);
                player.sendMessage("§aTeleported to '" + name + "'.");
            }
            case "delhome" -> {
                boolean removed = homeManager.deleteHome(player, name);
                player.sendMessage(removed ? "§aHome '" + name + "' deleted." : "§cHome not found.");
            }
            case "homes" -> {
                var homes = homeManager.listHomes(player);
                player.sendMessage(homes.isEmpty() ? "§7You have no homes." : "§7Your homes: " + String.join(", ", homes));
            }
        }
    }
}