package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealthCommand implements CommandExecutor {

	ServerManager plugin;
	public HealthCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = null;
		if(sender instanceof Player){
			player = (Player) sender;
		}	
		
		if(cmd.getName().equalsIgnoreCase("heal")){
			if(player != null && !plugin.hasPermission(player.getName(), "heal")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			if(args.length == 1){
				if(Bukkit.getPlayer(args[0]) != null){
					Player target = Bukkit.getPlayer(args[0]);
					if(target.isOnline()){
						target.setHealth(target.getMaxHealth());
					} else {
						Util.sendMessagePlayerNotOnline(sender);
					}
				}
			} else if(player != null){
				player.setHealth(player.getMaxHealth());
			}
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("feed")){
			if(player != null && !plugin.hasPermission(player.getName(), "feed")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			if(args.length == 1){
				if(Bukkit.getPlayer(args[0]) != null){
					Player target = Bukkit.getPlayer(args[0]);
					if(target.isOnline()){
						target.setFoodLevel(target.getMaxHealth());
					} else {
						Util.sendMessagePlayerNotOnline(sender);
					}
				}
			} else if(player != null){
				player.setFoodLevel(player.getMaxHealth());
			}
			return true;
		}
		return false;
	}

}
