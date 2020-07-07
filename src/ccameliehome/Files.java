package ccameliehome;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Files implements CommandExecutor {

    public static File deFile;
    public static FileConfiguration de;
    public static File enFile;
    public static FileConfiguration en;
    public static File configFile;
    public static FileConfiguration config;

    public static void x(LilliHome f) {
	if (!f.getDataFolder().exists()) {
	    f.getDataFolder().mkdirs();
	}

	deFile = new File(f.getDataFolder(), "de.yml");
	if (!deFile.exists()) {
	    f.saveResource("de.yml", false);
	}
	de = YamlConfiguration.loadConfiguration(deFile);

	enFile = new File(f.getDataFolder(), "en.yml");
	if (!enFile.exists()) {
	    f.saveResource("en.yml", false);
	}
	en = YamlConfiguration.loadConfiguration(enFile);

	configFile = new File(f.getDataFolder(), "config.yml");
	if (!configFile.exists()) {
	    f.saveResource("config.yml", false);
	}
	config = YamlConfiguration.loadConfiguration(configFile);

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	Player p = (Player) sender;
	if (cmd.getName().equalsIgnoreCase("lillihome")) {
	    if (p.isOp() || p.hasPermission("lillihome.admin.reload")) {
		if (args.length == 1) {
		    if (args[0].contains("reload")) {
			de = YamlConfiguration.loadConfiguration(deFile);
			en = YamlConfiguration.loadConfiguration(enFile);
			config = YamlConfiguration.loadConfiguration(configFile);
			p.sendMessage(LilliHome.prefix + ChatColor.GRAY + "Reload complete");
		    }

		} 
	    }else {
		if (Files.config.getString(".Language").contains("de")) {
		    p.sendMessage(
			    String.valueOf(LilliHome.prefix) + Files.de.getString(".nopermissionmessage").replace("&", "§"));
		} else {
		    p.sendMessage(
			    String.valueOf(LilliHome.prefix) + Files.en.getString(".nopermissionmessage").replace("&", "§"));
		}
	    }
	}else {
	    p.sendMessage(LilliHome.prefix + ChatColor.GRAY + "/lillihome reload");
	}
	return false;
    }
}