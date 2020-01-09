package me.oribuin.headowner.listeners;

import me.oribuin.headowner.HeadOwner;
import me.oribuin.headowner.persist.ColorU;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class HeadInteract implements Listener {
    HeadOwner plugin;

    public HeadInteract(HeadOwner instance) {
        plugin = instance;
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        FileConfiguration config = plugin.getConfig();
        Player player = event.getPlayer();

        if (event.getHand() != EquipmentSlot.HAND)
            return;

        Block clickedBlock = event.getClickedBlock();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Material type = clickedBlock.getType();
            if (type.equals(Material.PLAYER_HEAD) || type.equals(Material.PLAYER_WALL_HEAD)) {
                Skull skull = (Skull) clickedBlock.getState();
                OfflinePlayer owner = skull.getOwningPlayer();

                if (owner != null && owner.getName() != null) {
                    player.sendMessage(ColorU.cl(config.getString("click-message").replaceAll("\\{player}", owner.getName())));
                } else {
                    player.sendMessage(ColorU.cl(config.getString("click-message").replaceAll("\\{player}", config.getString("unknown"))));
                }
            }
        }
    }
}
