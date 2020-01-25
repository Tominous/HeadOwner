package xyz.oribuin.headowner.listeners;

import xyz.oribuin.headowner.HeadOwner;
import xyz.oribuin.headowner.persist.ColorU;
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

        // If the interact event isn't using main hand, do nothing
        if (event.getHand() != EquipmentSlot.HAND)
            return;

        // If the user does not have permission to use HeadOwner, do nothing

        if (!event.getPlayer().hasPermission("headowner.use"))
            return;

        // Define the clicked block
        Block clickedBlock = event.getClickedBlock();
        /*
         * If the action done is the one defined in Action:
         * If the block type interacted with is a Player Head do the following:
         *
         * Define the Skull's state and the owning Player
         * If the plugin can get the owning player and the name is not Null:
         * Print the message defined in the config showing who's head it is
         * If the owner is null or the name is null, Replace {player} with Unknown
         */
        if (event.getAction().equals(Action.valueOf(config.getString("action")))) {
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
