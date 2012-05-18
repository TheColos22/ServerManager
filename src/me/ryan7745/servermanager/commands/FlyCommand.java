package me.ryan7745.servermanager.commands;

import java.util.HashMap;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
	
	ServerManager plugin;
	public FlyCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("fly")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "fly")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			if(player.getAllowFlight())
				player.setAllowFlight(false);
			else
				player.setAllowFlight(true);
			
			return true;
		}
		return false;
	}
}
