package ccamelie.lillihome;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class home implements CommandExecutor, Listener {
    ConsoleCommandSender console;
    @SuppressWarnings("unused")
    private LilliHome plugin;
    int i;

    public home(LilliHome plugin) {
	this.i = 3;
	this.console = Bukkit.getServer().getConsoleSender();
	this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	Player p = (Player) sender;
	if (cmd.getName().equalsIgnoreCase("home")) {
	    if (p.hasPermission("lillihome.home") || Files.config.getBoolean(".defaultpermissions") == true) {
		if (args.length == 1) {
		    File file = new File("plugins/LilliHome/home/players/" + p.getUniqueId(),
			    args[0] + ".yml");
		    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

		    if (file.exists()) {
			double x = yamlConfiguration.getDouble("X");
			double y = yamlConfiguration.getDouble("Y");
			double z = yamlConfiguration.getDouble("Z");
			double yaw = yamlConfiguration.getDouble("Yaw");
			double pitch = yamlConfiguration.getDouble("Pitch");
			String worldname = yamlConfiguration.getString("Worldname");

			Location loc = new Location(Bukkit.getWorld(worldname), x, y, z);
			loc.setPitch((float) pitch);
			loc.setYaw((float) yaw);
			p.teleport(loc);
			p.getWorld().playEffect(loc, Effect.ENDER_SIGNAL, 0);
			p.getWorld().playEffect(loc, Effect.ENDER_SIGNAL, 0);
			p.getWorld().playEffect(loc, Effect.ENDER_SIGNAL, 0);
			p.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 0);
			p.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
			if (Files.config.getString(".Language").contains("de")) {
			    p.sendMessage(String.valueOf(LilliHome.prefix)
				    + Files.de.getString(".athomemessage").replace("%home%", args[0]).replace("&", "§"));
			} else {
			    p.sendMessage(String.valueOf(LilliHome.prefix)
				    + Files.en.getString(".athomemessage").replace("%home%", args[0]).replace("&", "§"));
			}
		    } else {
			if (Files.config.getString(".Language").contains("de")) {
			    p.sendMessage(String.valueOf(LilliHome.prefix)
				    + Files.de.getString(".homenotexistmessage").replace("%home%", args[0]).replace("&", "§"));
			} else {
			    p.sendMessage(String.valueOf(LilliHome.prefix)
				    + Files.en.getString(".homenotexistmessage").replace("%home%", args[0]).replace("&", "§"));
			}
		    }
		} else {
		    if (Files.config.getString(".Language").contains("de")) {
			p.sendMessage(String.valueOf(LilliHome.prefix) + Files.de.getString(".homeusemessage").replace("&", "§"));
		    } else {
			p.sendMessage(String.valueOf(LilliHome.prefix) + Files.en.getString(".homeusemessage").replace("&", "§"));
		    }
		}
	    }
	}
	return false;
    }
}
