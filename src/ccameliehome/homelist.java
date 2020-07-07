package ccameliehome;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class homelist implements CommandExecutor, Listener {

    public homelist(LilliHome plugin) {
	this.console = Bukkit.getServer().getConsoleSender();
	this.plugin = plugin;
    }

    ConsoleCommandSender console;
    private LilliHome plugin;

    private static String removeLastChar(String str, Player p) {
	if (str.length() >= 5) {
	    return str.substring(0, str.length() - 5);
	}
	return str;

    }

    HashMap<String, Integer> homeamount = new HashMap<String, Integer>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	Player p = (Player) sender;

	if (label.equalsIgnoreCase("homelist")) {

	    if (p.hasPermission("lillihome.homelist") || Files.config.getBoolean(".defaultpermissions") == true) {
		if (sender instanceof Player) {

		    File folder = new File("plugins/LilliHome/home/players/" + p.getUniqueId());
		    if (folder.exists()) {

			String[] fileNames = folder.list();

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < fileNames.length; i++) {
			    if (fileNames.length == i) {
				sb.append(fileNames[i]);
			    }
			    sb.append(fileNames[i]).append(ChatColor.GRAY + ", " + ChatColor.GOLD);
			    homeamount.put(p.getName(), i + 1);
			}

			String msg = sb.toString().trim().replace(".yml", "");
			if (!(removeLastChar(msg, p).length() <= 0)) {
			    if (Files.config.getString(".Language").contains("de")) {
				p.sendMessage(String.valueOf(LilliHome.prefix)
					+ Files.de.getString(".homelistmessage")
						.replace("%amount%", "" + homeamount.get(p.getName())).replace("&", "§")
					+ removeLastChar(msg, p));
			    } else {
				p.sendMessage(String.valueOf(LilliHome.prefix)
					+ Files.en.getString(".homelistmessage")
						.replace("%amount%", "" + homeamount.get(p.getName())).replace("&", "§")
					+ removeLastChar(msg, p));
			    }
			} else {
			    if (Files.config.getString(".Language").contains("de")) {
				p.sendMessage(String.valueOf(LilliHome.prefix)
					+ Files.de.getString(".nohomemessage").replace("&", "§"));
			    } else {
				p.sendMessage(String.valueOf(LilliHome.prefix)
					+ Files.en.getString(".nohomemessage").replace("&", "§"));
			    }
			}
		    } else {
			if (Files.config.getString(".Language").contains("de")) {
			    p.sendMessage(String.valueOf(LilliHome.prefix)
				    + Files.de.getString(".nohomemessage").replace("&", "§"));
			} else {
			    p.sendMessage(String.valueOf(LilliHome.prefix)
				    + Files.en.getString(".nohomemessage").replace("&", "§"));
			}
		    }

		} else {
		    p.sendMessage("You cannot send this command in a console!");
		}
	    } else {
		if (Files.config.getString(".Language").contains("de")) {
		    p.sendMessage(String.valueOf(LilliHome.prefix)
			    + Files.de.getString(".nopermissionmessage").replace("&", "§"));
		} else {
		    p.sendMessage(String.valueOf(LilliHome.prefix)
			    + Files.en.getString(".nopermissionmessage").replace("&", "§"));
		}
	    }

	}
	return false;
    }
}
