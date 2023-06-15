package me.CraftyStudios.ConditionalNPCMovement.Events;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.Navigator;
import net.citizensnpcs.api.astar.pathfinder.Path;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveNPC {
    public List<Location> path = new ArrayList<>();

    public void moveNPCByPathfind(Integer id, Location loc) {
        NPC npc = CitizensAPI.getNPCRegistry().getById(id);
        npc.faceLocation(loc);
        npc.getNavigator().setTarget(loc);
    }

    public void moveNPCByDiscretePath(Integer id, Location loc) {
        NPC npc = CitizensAPI.getNPCRegistry().getById(id);
        npc.faceLocation(loc);

        Navigator n = npc.getNavigator();

        for (Location l : path) {
            n.setTarget(l);
            while (n.isNavigating()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
