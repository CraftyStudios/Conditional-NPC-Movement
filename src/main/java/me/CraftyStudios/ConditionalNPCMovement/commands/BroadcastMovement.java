package me.CraftyStudios.ConditionalNPCMovement.commands;

import me.CraftyStudios.ConditionalNPCMovement.Events.MoveNPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastMovement {
    static MoveNPC mn = new MoveNPC();
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equals("bmpathfind")) {
            Player p = (Player) sender;
            Integer id = Integer.parseInt(args[0]);
            String locx = args[1];
            String locy = args[2];
            String locz = args[3];
            String world = args[4];

            double x = Double.parseDouble(locx);
            double y = Double.parseDouble(locy);
            double z = Double.parseDouble(locz);

            World w = Bukkit.getWorld(world);

            if (args[4] == null) {
                w = Bukkit.getWorld(p.getWorld().getUID());
            }

            Location loc = new Location(w, x, y, z);
            mn.moveNPCByDiscretePath(id, loc);
            return true;
        }

        if (cmd.getName().equals("bmauto")) {
            Player p = (Player) sender;
            int id = Integer.parseInt(args[0]);
            String locx = args[1];
            String locy = args[2];
            String locz = args[3];
            String world = args[4];

            double x = Double.parseDouble(locx);
            double y = Double.parseDouble(locy);
            double z = Double.parseDouble(locz);

            World w = Bukkit.getWorld(world);

            if (args[3] == null) {
                w = Bukkit.getWorld(p.getWorld().getUID());
            }

            Location loc = new Location(w, x, y, z);
            mn.moveNPCByPathfind(id, loc);
        }
        return false;
    }
}