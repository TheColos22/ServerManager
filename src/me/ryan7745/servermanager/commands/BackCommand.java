package me.ryan7745.servermanager.commands;

import java.util.HashMap;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements CommandExecutor {
	
	ServerManager plugin;
	public BackCommand(ServerManager instance) {
		plugin = instance;
	}
	
	public static HashMap<String, Location> backdb = new HashMap<String, Location>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("back")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "back")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			if(backdb.containsKey(player.getName())){
				Location loc = backdb.get(player.getName());
				player.teleport(loc);
				player.sendMessage(Util.formatMessage("Teleporting to previous location"));
				return true;
			}
			
			return false;
		}
		return false;
	}
}
