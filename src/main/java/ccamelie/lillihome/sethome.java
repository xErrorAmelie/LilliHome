package ccamelie.lillihome;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.md_5.bungee.api.ChatColor;

public class sethome implements CommandExecutor, Listener {
    public sethome(LilliHome plugin) {
	this.console = Bukkit.getServer().getConsoleSender();
	this.plugin = plugin;
    }

    ConsoleCommandSender console;
    @SuppressWarnings("unused")
    private LilliHome plugin;
    HashMap<String, Boolean> blocked = new HashMap<String, Boolean>();
    HashMap<String, Integer> homeamount = new HashMap<String, Integer>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	Player p = (Player) sender;

	File ordner = new File("plugins/LilliHome/home/players/");
	if (!ordner.exists()) {
	    ordner.mkdir();
	}
	if (cmd.getName().equalsIgnoreCase("sethome")) {
	    blocked.put(p.getName(), false);
	    if (sender.hasPermission("lillihome.sethome") || Files.config.getBoolean(".defaultpermissions") == true) {
		if (args.length == 1) {
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
		    } else {
			if (homeamount.get(p.getName()) == null) {
			    homeamount.put(p.getName(), 0);
			}
		    }
		    if (homeamount.get(p.getName()) >= Files.config.getInt(".homelimit")
			    && (Files.config.getInt(".homelimit")) != 0) {
			if (Files.config.getString(".Language").contains("de")) {
			    p.sendMessage(String.valueOf(LilliHome.prefix) + Files.de.getString(".homelimitmessage")
				    .replace("&", "§").replace("%homelimit%", "" + Files.config.getInt(".homelimit")));
			} else {
			    p.sendMessage(String.valueOf(LilliHome.prefix) + Files.en.getString(".homelimitmessage")
				    .replace("&", "§").replace("%homelimit%", "" + Files.config.getInt(".homelimit")));
			}
		    } else {
			for (String blockedworlds : Files.config.getStringList(".blockedworlds")) {
			    if (p.getLocation().getWorld().getName().equals(blockedworlds)) {
				blocked.put(p.getName(), true);
				if (Files.config.getString(".Language").contains("de")) {
				    p.sendMessage(LilliHome.prefix
					    + Files.de.getString(".blockedworldmessage").replace("&", "§"));
				} else {
				    p.sendMessage(LilliHome.prefix
					    + Files.en.getString(".blockedworldmessage").replace("&", "§"));
				}
				break;
			    }
			}
			if ((blocked.get(p.getName())) == false) {
			    File file = new File("plugins/LilliHome/home/players/" + p.getUniqueId(), args[0] + ".yml");

			    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
			    if (!file.exists()) {
				Location loc = p.getLocation();
				double x = loc.getX();
				double y = loc.getY();
				double z = loc.getZ();
				double yaw = loc.getYaw();
				double pitch = loc.getPitch();
				String worldname = loc.getWorld().getName();

				yamlConfiguration.set("X", Double.valueOf(x));
				yamlConfiguration.set("Y", Double.valueOf(y));
				yamlConfiguration.set("Z", Double.valueOf(z));
				yamlConfiguration.set("Yaw", Double.valueOf(yaw));
				yamlConfiguration.set("Pitch", Double.valueOf(pitch));
				yamlConfiguration.set("Worldname", worldname);

				try {
				    yamlConfiguration.save(file);
				} catch (IOException e) {

				    e.printStackTrace();
				}
				if (Files.config.getString(".Language").contains("de")) {
				    p.sendMessage(String.valueOf(LilliHome.prefix) + Files.de
					    .getString(".sethomemessage").replace("%home%", args[0]).replace("&", "§"));
				} else {
				    p.sendMessage(String.valueOf(LilliHome.prefix) + Files.en
					    .getString(".sethomemessage").replace("%home%", args[0]).replace("&", "§"));
				}

			    } else {
				if (Files.config.getString(".Language").contains("de")) {
				    p.sendMessage(String.valueOf(LilliHome.prefix)
					    + Files.de.getString(".homealreadyexistmessage").replace("%home%", args[0])
						    .replace("&", "§"));
				} else {
				    p.sendMessage(String.valueOf(LilliHome.prefix)
					    + Files.en.getString(".homealreadyexistmessage").replace("%home%", args[0])
						    .replace("&", "§"));
				}
			    }
			} else {
			    blocked.put(p.getName(), false);
			}

		    }
		} else {
		    if (Files.config.getString(".Language").contains("de")) {
			p.sendMessage(String.valueOf(LilliHome.prefix)
				+ Files.de.getString(".sethomeusemessage").replace("&", "§"));
		    } else {
			p.sendMessage(String.valueOf(LilliHome.prefix)
				+ Files.en.getString(".sethomeusemessage").replace("&", "§"));
		    }
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