package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WeatherCommand implements CommandExecutor {
	ServerManager plugin;
	public WeatherCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("weather")){
			Player player = null;
			if(sender instanceof Player){
				player = (Player) sender;
			}
			
			if(player != null && !plugin.hasPermission(player.getName(), "weather")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("sun")){
					if(player != null){
						player.getWorld().setStorm(false);
						player.getWorld().setThundering(false);
						sender.sendMessage(Util.formatMessage("Set weather to " + ChatColor.GRAY + "sun" + ChatColor.BLUE + "."));
					} else {
						Bukkit.getWorlds().get(0).setStorm(true);
						Bukkit.getWorlds().get(0).setThundering(false);
						sender.sendMessage(Util.formatMessage("Set weather to " + ChatColor.GRAY + "sun" + ChatColor.BLUE + "."));
					}
					return true;
				} else if(args[0].equalsIgnoreCase("rain")){
					if(player != null){
						player.getWorld().setStorm(true);
						player.getWorld().setThundering(false);
						sender.sendMessage(Util.formatMessage("Set weather to " + ChatColor.GRAY + "rain" + ChatColor.BLUE + "."));
					} else {
						Bukkit.getWorlds().get(0).setStorm(true);
						Bukkit.getWorlds().get(0).setThundering(false);
						sender.sendMessage(Util.formatMessage("Set weather to " + ChatColor.GRAY + "rain" + ChatColor.BLUE + "."));
					}
					return true;
				} else if(args[0].equalsIgnoreCase("thunder")){
					if(player != null){
						player.getWorld().setStorm(true);
						player.getWorld().setThundering(true);
						sender.sendMessage(Util.formatMessage("Set weather to " + ChatColor.GRAY + "thunder" + ChatColor.BLUE + "."));
					} else {
						Bukkit.getWorlds().get(0).setStorm(true);
						Bukkit.getWorlds().get(0).setThundering(true);
						sender.sendMessage(Util.formatMessage("Set weather to " + ChatColor.GRAY + "thunder" + ChatColor.BLUE + "."));
					}
					return true;
				} else {
					return false;
				}
			}
			return false;
		}
		return false;
	}
}
