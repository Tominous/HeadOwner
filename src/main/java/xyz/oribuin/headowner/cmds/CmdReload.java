package xyz.oribuin.headowner.cmds;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import xyz.oribuin.headowner.HeadOwner;
import xyz.oribuin.headowner.persist.ColorU;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CmdReload implements CommandExecutor {
    HeadOwner plugin;

    public CmdReload(HeadOwner instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = plugin.getConfig();
        if (sender instanceof Player) {
            Player player = (Player) sender;

            /*
             * If the user does not have permission to use the reload command.
             * Print "no-permission" message to the player.
             */
            if (!player.hasPermission("headowner.reload")) {
                player.sendMessage(ColorU.cl(config.getString("no-permission")));
                return true;
            }
        }

        // Reload the config.
        plugin.reloadConfig();

        //Delete all Holograms on reload
        if (HologramsAPI.getHolograms(plugin).size() > 0) {
            HologramsAPI.getHolograms(plugin).forEach(Hologram::delete);
        }

        // Notify Console that the plugin was reloaded.
        Bukkit.getConsoleSender().sendMessage(ColorU.cl(config.getString("prefix") + " &fReloaded " + plugin.getDescription().getName() + " (&b" + plugin.getDescription().getVersion() + "&f)"));
        return true;
    }

}