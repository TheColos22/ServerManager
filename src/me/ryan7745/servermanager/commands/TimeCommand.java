package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {
	
	ServerManager plugin;
	public TimeCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("time")){
			Player player = null;
			if(sender instanceof Player){
				player = (Player) sender;
			}
			
			if(player != null && !plugin.hasPermission(player.getName(), "time")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("day")){
					if(player != null){
						player.getWorld().setTime(0);
						sender.sendMessage(Util.formatMessage("Time set to day."));
					} else {
						Bukkit.getWorlds().get(0).setTime(0);
						sender.sendMessage(Util.formatMessage("Time set to day."));
					}
					return true;
				} else if(args[0].equalsIgnoreCase("night")){
					if(player != null){
						player.getWorld().setTime(16000);
						sender.sendMessage(Util.formatMessage("Time set to night."));
					} else {
						Bukkit.getWorlds().get(0).setTime(0);
						sender.sendMessage(Util.formatMessage("Time set to night."));
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
