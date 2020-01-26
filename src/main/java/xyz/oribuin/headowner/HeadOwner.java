package xyz.oribuin.headowner;

import org.bukkit.Color;
import xyz.oribuin.headowner.cmds.CmdReload;
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
         * Dependency checks
         */

        if (pm.getPlugin("HolographicDisplays") == null)
            this.getServer().getConsoleSender().sendMessage(ColorU.cl("&7[&bHeadOwner&7] &fHolographicDisplays is not installed, therefor Holograms will not work."));


        /*
         * Command Registering
         */
        getCommand("horeload").setExecutor(new CmdReload(this));

        /*
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
                        "\n&6Website: &f" + this.getDescription().getWebsite() +
                        "\n\n&e******************"
        ));
    }
}
