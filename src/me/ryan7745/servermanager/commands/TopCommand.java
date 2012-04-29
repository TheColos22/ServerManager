package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TopCommand implements CommandExecutor {
	
	ServerManager plugin;
	public TopCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("top")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "top")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			Location loc = player.getLocation();
			Block highestBlock = player.getWorld().getHighestBlockAt(loc);
			
			player.teleport(highestBlock.getLocation());
			
			return true;
		}
		return false;
	}
}
