package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearInventoryCommand implements CommandExecutor {

	ServerManager plugin;
	public ClearInventoryCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("clear")){
			if(!(sender instanceof Player)){
				if(args.length == 1){
					if(Bukkit.getPlayer(args[0]) != null){
						Player target = Bukkit.getPlayer(args[0]);
						if(target.isOnline()){
							target.sendMessage(Util.formatMessage("Your inventory has been cleared."));
							target.getInventory().clear();
							return true;
						} else {
							Util.sendMessagePlayerNotOnline(sender);
							return true;
						}
					}
				}
				return false;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "clear")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			if(args.length == 1){
				if(!plugin.hasPermission(player.getName(), "clear.others")){
					Util.SendMessageNoPerms(sender);
					return true;
				}
				if(Bukkit.getPlayer(args[0]) != null){
					Player target = Bukkit.getPlayer(args[0]);
					if(target.isOnline()){
						target.sendMessage(Util.formatMessage("Your inventory has been cleared."));
						target.getInventory().clear();
					} else {
						Util.sendMessagePlayerNotOnline(sender);
					}
				}
			} else {
				sender.sendMessage(Util.formatMessage("Your inventory has been cleared."));
				((Player) sender).getInventory().clear();
			}
			return true;
		}
		return false;
	}

}
