package com.swingalytica.plugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();
        Player target;

        if (args.length > 0) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("§cPlayer not found or not online.");
                return;
            }
        } else {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cConsole must specify a player name: /fly <name>");
                return;
            }
            target = player;
        }

        boolean newState = !target.getAllowFlight();
        target.setAllowFlight(newState);
        target.setFlying(newState);

        target.sendMessage(newState ? "§aFlight mode enabled." : "§cFlight mode disabled.");

        if (sender != target) {
            sender.sendMessage("§7Flight mode " + (newState ? "enabled" : "disabled") + " for " + target.getName() + ".");
        }
    }
}