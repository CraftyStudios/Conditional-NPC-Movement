package me.CraftyStudios.ConditionalNPCMovement.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BroadcastMovement {
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("broadcastmovement")) {
            if(args.length != 1) {
                sender.sendMessage("Usage: /broadcastmovement <NPC Name>");
                return true;
            }
        }
        return true;
    }
}