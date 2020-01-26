package xyz.oribuin.headowner.listeners;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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

        // If the action equals the one defined in config.yml, do the following
        if (event.getAction().equals(Action.valueOf(config.getString("action")))) {

            // if the clicked block is not null
            if (clickedBlock != null) {
                // Define the block type
                Material type = clickedBlock.getType();

                // If the clicked type is a Player Head:
                if (type.equals(Material.PLAYER_HEAD) || type.equals(Material.PLAYER_WALL_HEAD)) {
                    // Get the state of the clicked block in the Skull variable and get the owning player
                    Skull skull = (Skull) clickedBlock.getState();
                    OfflinePlayer owner = skull.getOwningPlayer();

                    /*
                     * If chat messages are enabled send "click-message" while replacing {player} with owner's name
                     * If the owner's name is null then replace it with "invalid-user" defined in config
                    */
                    if (config.getBoolean("chat-message", true)) {
                        if (owner != null && owner.getName() != null) {
                            player.sendMessage(ColorU.cl(config.getString("click-message").replaceAll("\\{player}", owner.getName())));
                        } else {
                            player.sendMessage(ColorU.cl(config.getString("click-message").replaceAll("\\{player}", config.getString("invalid-user"))));
                        }
                    }

                    // If there is no HolographicDisplays installed, do nothing
                    if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") == null) return;

                    // If holograms are enabled do the following
                    if (config.getBoolean("holograms.enabled", true)) {
                        // Get the location of the block clicked
                        Location location = clickedBlock.getLocation();

                        // If there is no hologram designed defined, do nothing
                        if (config.getStringList("holograms.format").isEmpty()) return;

                        // Set the hologram location to the center of the block as well as set the y axis of the hologram to the defined number in config.yml
                        location.setX(location.getX() + 0.500);
                        location.setY(location.getY() + config.getLong("holograms.y-axis"));
                        location.setZ(location.getZ() + 0.500);

                        // Create the hologram at the defined location above
                        Hologram hologram = HologramsAPI.createHologram(plugin, location);

                        /*
                         * For each line of holograms.format replace all{player} names and add the lines to the hologram
                         */
                        for (String line : config.getStringList("holograms.format")) {
                            if (owner != null && owner.getName() != null) {
                                hologram.appendTextLine(ColorU.cl(line).replaceAll("\\{player}", owner.getName()));
                            } else if (config.getString("invalid-user") != null) {
                                hologram.appendTextLine(ColorU.cl(line).replaceAll("\\{player}", config.getString("invalid-user")));
                            }
                        }

                        // Define the duration time and multiply it by 20 (20 = 1s so if duration is 10 it would be 10 x 20 = 10s
                        int duration = config.getInt("holograms.duration") * 20;
                        // If auto deletion is enabled, delete the hologram once timer is up
                        if (config.getBoolean("holograms.auto-delete", true)) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, hologram::delete, duration);
                        }
                    }
                }
            }
        }
    }
}
