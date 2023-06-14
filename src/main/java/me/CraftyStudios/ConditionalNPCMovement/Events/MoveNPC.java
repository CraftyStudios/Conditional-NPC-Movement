package me.CraftyStudios.ConditionalNPCMovement.Events;

import net.citizensnpcs.api.CitizensAPI;
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

    }
}
