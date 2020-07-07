package ccameliehome;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LilliHome extends JavaPlugin implements Listener, CommandExecutor {
    private ConsoleCommandSender console = getServer().getConsoleSender();
    public static String prefix = ChatColor.GRAY + "[" + ChatColor.RED + "LilliHome" + ChatColor.GRAY + "] ";
    public static Plugin pl;
    
    public static LilliHome plugin;
    private static LilliHome instance;
    static boolean defaultpermission = true;

    public static LilliHome getInstance() {
	return instance;
    }

    public void onEnable() {
	instance = this;
	System.out.println(String.valueOf(prefix) + "loaded!");

	getCommand("home").setExecutor(new home(this));
	getCommand("sethome").setExecutor(new sethome(this));
	getCommand("delhome").setExecutor(new delhome(this));
	getCommand("homelist").setExecutor(new homelist(this));
	getCommand("lillihome").setExecutor(new Files());

	Files.x(this);
	PluginManager pl = Bukkit.getPluginManager();
	pl.registerEvents(this, this);
	this.console.sendMessage(String.valueOf(prefix) + "loaded and welcome!");
    }

    public void onDisable() {
	this.console.sendMessage(String.valueOf(prefix) + "bye bye :)");
    }

   

}