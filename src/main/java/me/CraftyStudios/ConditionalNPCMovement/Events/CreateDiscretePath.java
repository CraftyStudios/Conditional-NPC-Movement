package me.CraftyStudios.ConditionalNPCMovement.Events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.Listener;

import java.util.List;

public class CreateDiscretePath implements Listener {
    MoveNPC mn = new MoveNPC();
    ItemStack pathCreator;

    public void createPathWand() {
        pathCreator = new ItemStack(Material.BLAZE_ROD);
        ItemMeta pathCreatorMeta = pathCreator.getItemMeta();
        pathCreatorMeta.setDisplayName("§ePath Creator");
        List<String> pathCreatorLore = pathCreatorMeta.getLore();
        pathCreatorLore.add("§7Right click a block to create a path");
        pathCreatorMeta.setLore(pathCreatorLore);
        pathCreator.setItemMeta(pathCreatorMeta);
    }

    public void givePathWand(Player p) {
        p.getInventory().addItem(pathCreator);
    }

    @EventHandler
    public void listenForRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        Action a = e.getAction();
        Block b = e.getClickedBlock();
        Location l = b.getLocation();

        if (a == Action.RIGHT_CLICK_BLOCK && item == pathCreator) {
            mn.path.add(l);
        }
    }
}
