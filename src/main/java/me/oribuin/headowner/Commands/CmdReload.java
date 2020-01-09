package me.oribuin.headowner.Commands;

import me.oribuin.headowner.HeadOwner;
import me.oribuin.headowner.persist.ColorU;
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
        if (sender instanceof Player) {

            FileConfiguration config = plugin.getConfig();

            Player player = (Player) sender;

            if (player.hasPermission("headowner.reload")) {
                plugin.reloadConfig();
                plugin.saveConfig();
                player.sendMessage(ColorU.cl(config.getString("reload")));
            } else {
                player.sendMessage(ColorU.cl(config.getString("no-permission")));
            }
        }

        return true;
    }

}