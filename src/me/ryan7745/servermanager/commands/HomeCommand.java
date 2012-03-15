package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ConfigUtil;
import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {
	
	ServerManager plugin;
	public HomeCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("home")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "home")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			if(args.length == 1){
				Player target = Bukkit.getPlayer(args[0]);
				if(target != null){
					if(ConfigUtil.getPConfExists(target)){
						if(ConfigUtil.getPVal(target, "home") != null){
							String home = ConfigUtil.getPValString(target, "home");
							String[] homeLoc = home.split(":");
							if(homeLoc.length == 4){
								World world;
								Integer X;
								Integer Y;
								Integer Z;
								try{
									world = Bukkit.getWorld(homeLoc[0]);
									X = Integer.parseInt(homeLoc[1]);
									Y = Integer.parseInt(homeLoc[2]);
									Z = Integer.parseInt(homeLoc[3]);
								} catch (Exception e){
									player.sendMessage(Util.formatMessage("Invalid home in player config."));
									return true;
								}
								if(world != null && X != null && Y != null && Z != null){
									Location loc = new Location(world, X, Y, Z);
									player.teleport(loc);
									player.sendMessage(Util.formatMessage("Teleported to " + ChatColor.GRAY + target.getName() + "s" + ChatColor.BLUE + " home."));
									return true;
								}
							}
						} else {
							player.sendMessage(Util.formatMessage(ChatColor.GRAY + target.getName() + "s" + ChatColor.RED + " home is not set."));
							return true;
						}
					}
				}
			} else if(args.length == 0) {
				if(ConfigUtil.getPConfExists(player)){
					if(ConfigUtil.getPVal(player, "home") != null){
						String home = ConfigUtil.getPValString(player, "home");
						String[] homeLoc = home.split(":");
						if(homeLoc.length == 4){
							World world;
							Integer X;
							Integer Y;
							Integer Z;
							try{
								world = Bukkit.getWorld(homeLoc[0]);
								X = Integer.parseInt(homeLoc[1]);
								Y = Integer.parseInt(homeLoc[2]);
								Z = Integer.parseInt(homeLoc[3]);
							} catch (Exception e){
								player.sendMessage(Util.formatMessage("Invalid home in player config."));
								return true;
							}
							if(world != null && X != null && Y != null && Z != null){
								Location loc = new Location(world, X, Y, Z);
								player.teleport(loc);
								player.sendMessage(Util.formatMessage("Teleported to home."));
								return true;
							}
						}
					} else {
						player.sendMessage(Util.formatMessage(ChatColor.RED + "Your home is not set."));
						return true;
					}
				}
			}
			
			return false;
		} else if(cmd.getName().equalsIgnoreCase("sethome")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "sethome")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			if(ConfigUtil.getPConfExists(player)){
				String world = player.getWorld().getName();
				Location loc = player.getLocation();
				Integer X = loc.getBlockX();
				Integer Y = loc.getBlockY();
				Integer Z = loc.getBlockZ();
				String home = world + ":" + X + ":" + Y + ":" + Z;
				
				ConfigUtil.setPValString(player, home, "home");
				player.sendMessage(Util.formatMessage("Your home has been set."));
			}
			
			return true;
		}
		return false;
	}
}
