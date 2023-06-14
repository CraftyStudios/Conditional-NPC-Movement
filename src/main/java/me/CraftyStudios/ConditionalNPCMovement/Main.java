package me.CraftyStudios.ConditionalNPCMovement;

import me.CraftyStudios.ConditionalNPCMovement.Events.CreateDiscretePath;
import me.CraftyStudios.ConditionalNPCMovement.utils.Logger;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {
    CreateDiscretePath cdp = new CreateDiscretePath();

    List<Location> path = new ArrayList<>();
    List<NPCPath> paths = new ArrayList<>();
    @Override
    public void onEnable() {
        cdp.createPathWand();
        try {
            Class.forName("net.citizensnpcs.api.CitizensAPI");
        } catch (ClassNotFoundException e) {
            Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------");
            Logger.log(Logger.LogLevel.ERROR, "Citizens is not installed!");
            Logger.log(Logger.LogLevel.ERROR, "Please install Citizens before using this plugin!");
            Logger.log(Logger.LogLevel.ERROR, "https://www.spigotmc.org/resources/citizens.13811/");
            Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------");
            Logger.log(Logger.LogLevel.INFO, "Need support?");
            Logger.log(Logger.LogLevel.INFO, "https://discord.gg/r6rmzHbPGT");
            Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------");
        Logger.log(Logger.LogLevel.SUCCESS, "Loading Conditional NPC Movement...");
        Logger.log(Logger.LogLevel.SUCCESS, "Citizens found!");
        Logger.log(Logger.LogLevel.SUCCESS, "Loaded!");
        Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------");
        Logger.log(Logger.LogLevel.INFO, "Join our discord!");
        Logger.log(Logger.LogLevel.INFO, "https://discord.gg/r6rmzHbPGT");
        Logger.log(Logger.LogLevel.INFO, "Request a feature or report a bug!");
        Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------");

        ConfigurationSection npcConfig = getConfig().getConfigurationSection("npcs");
        for (String npcName : npcConfig.getKeys(false)) {
            int npcId = npcConfig.getInt(npcName + ".id");
            NPC npc = CitizensAPI.getNPCRegistry().getById(npcId);

            ConfigurationSection targetConfig = npcConfig.getConfigurationSection(npcName + ".destinations");
            List<Location> path = new ArrayList<>(); // Create a new path list for each NPC
            for (String targetName : targetConfig.getKeys(false)) {
                World world = getServer().getWorld(targetConfig.getString(targetName + ".world"));
                double x = targetConfig.getDouble(targetName + ".x");
                double y = targetConfig.getDouble(targetName + ".y");
                double z = targetConfig.getDouble(targetName + ".z");
                float yaw = (float) targetConfig.getDouble(targetName + ".yaw");
                float pitch = (float) targetConfig.getDouble(targetName + ".pitch");
                Location target = new Location(world, x, y, z, yaw, pitch);
                path.add(target);
        if(npc == null) {
            Logger.log(Logger.LogLevel.ERROR, "NPC with ID " + npcId + " does not exist!");
        }
            }
            paths.add(new NPCPath(npc, path));
        }
        new NPCPathTask().runTaskTimer(this, 0L, 20L);
    }

    private class NPCPath {
        private NPC npc;
        private List<Location> path;
        private int currentIndex;

        public NPCPath(NPC npc, List<Location> path) {
            this.npc = npc;
            this.path = path;
            this.currentIndex = 0;
        }

        public NPC getNPC() {
            return npc;
        }

        public Location getNextLocation() {
            if (currentIndex >= path.size()) {
                return null;
            }
            Location next = path.get(currentIndex);
            currentIndex++;
            return next;
        }

        public boolean hasFinished() {
            return currentIndex >= path.size();
        }
    }

    private class NPCPathTask extends BukkitRunnable {
        @Override
        public void run() {
            for (NPCPath path : paths) {
                Location next = path.getNextLocation();
                if (next != null) {
                    path.getNPC().faceLocation(next);
                    path.getNPC().getNavigator().setTarget(next);
                }else if (path.hasFinished()) {
                    path.getNPC().getNavigator().cancelNavigation();
                }
            }
         }
    }
    @Override
    public void onDisable() {
        Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------");
        Logger.log(Logger.LogLevel.SUCCESS, "Unloaded Conditional NPC Movement");
        Logger.log(Logger.LogLevel.SUCCESS, "See you later!");
        Logger.log(Logger.LogLevel.OUTLINE, "------------------------------------");
    }
}
