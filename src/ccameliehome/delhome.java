package ccameliehome;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class delhome implements CommandExecutor, Listener {
    public delhome(LilliHome plugin) {
	this.console = Bukkit.getServer().getConsoleSender();
	this.plugin = plugin;
    }

    ConsoleCommandSender console;
    @SuppressWarnings("unused")
    private LilliHome plugin;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	Player p = (Player) sender;
	if (cmd.getName().equalsIgnoreCase("delhome")) {
	    if (p.hasPermission("lillihome.delhome") || Files.config.getBoolean(".defaultpermissions") == true) {
		if (args.length == 1) {
		    File file = new File("plugins/LilliHome/home/players/" + p.getUniqueId(),
			    args[0] + ".yml");
		    if (file.exists()) {
			file.delete();
			if (Files.config.getString(".Language").contains("de")) {
			    p.sendMessage(String.valueOf(LilliHome.prefix)
				    + Files.de.getString(".homedeletedmessage").replace("%home%", args[0]).replace("&", "§"));
			} else {
			    p.sendMessage(String.valueOf(LilliHome.prefix)
				    + Files.en.getString(".homedeletedmessage").replace("%home%", args[0]).replace("&", "§"));
			}
		    } else {
			if (Files.config.getString(".Language").contains("de")) {
			    p.sendMessage(String.valueOf(LilliHome.prefix)
				    + Files.de.getString(".homedeletedmessageerror").replace("%home%", args[0]).replace("&", "§"));
			} else {
			    p.sendMessage(String.valueOf(LilliHome.prefix)
				    + Files.en.getString(".homedeletedmessageerror").replace("%home%", args[0]).replace("&", "§"));
			}
		    }
		} else {
		    if (Files.config.getString(".Language").contains("de")) {
			p.sendMessage(String.valueOf(LilliHome.prefix) + Files.de.getString(".delhomeusemessage").replace("&", "§"));
		    } else {
			p.sendMessage(String.valueOf(LilliHome.prefix) + Files.en.getString(".delhomeusemessage").replace("&", "§"));
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
	}
	return true;
    }
}
