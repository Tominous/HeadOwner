package xyz.oribuin.headowner;

import xyz.oribuin.headowner.Commands.CmdReload;
import xyz.oribuin.headowner.hooks.Metrics;
import xyz.oribuin.headowner.listeners.HeadInteract;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.oribuin.headowner.persist.ColorU;

public class HeadOwner extends JavaPlugin {

    public void onEnable() {
        /*
         * Variable Defining
         */

        PluginManager pm = Bukkit.getPluginManager();

        /*
         * Command Registering
         */
        getCommand("hdreload").setExecutor(new CmdReload(this));

        /**
         * Event Registering
         */

        pm.registerEvents(new HeadInteract(this), this);

        /*
         * BStats Metrics
         */

        int pluginId = 6349;
        xyz.oribuin.headowner.hooks.Metrics metrics = new Metrics(this, pluginId);

        /*
         * Create the config.yml
         */

        this.saveDefaultConfig();

        /*
         * Startup Message
         */

        this.getServer().getConsoleSender().sendMessage(ColorU.cl(
                "\n\n&e******************\n" +
                        "\n&6Plugin: &f" + this.getDescription().getName() +
                        "\n&6Author: &f" + this.getDescription().getAuthors().get(0) +
                        "\n&6Version: &f" + this.getDescription().getVersion() +
                        "\n\n&e******************"
        ));
    }
}
