package me.oribuin.headowner;

import me.oribuin.headowner.Commands.CmdReload;
import me.oribuin.headowner.listeners.HeadInteract;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HeadOwner extends JavaPlugin {

    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();

        getCommand("hdreload").setExecutor(new CmdReload(this));
        pm.registerEvents(new HeadInteract(this), this);

        this.saveDefaultConfig();
    }

    public void onDisable() {

    }
}
