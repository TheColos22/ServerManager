package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;
import me.ryan7745.servermanager.actions.Teleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {
	
	Teleport TeleportHandler;
	
	ServerManager plugin;
	public TeleportCommand(ServerManager instance) {
		plugin = instance;
		TeleportHandler = new Teleport(instance);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tp")){
			Player player = null;
			if(sender instanceof Player){
				player = (Player) sender;
			}
			if(player != null && !plugin.hasPermission(player.getName(), "tp")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			if(args.length == 1 && player != null){
				TeleportHandler.teleport(player, args[0]);
			}
			
			if(args.length == 2){
				TeleportHandler.teleport(args[0], args[1]);
			}
			
			return true;
		} else if(cmd.getName().equalsIgnoreCase("tphere")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "tphere")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			if(args.length == 1)
				TeleportHandler.teleportHere(player, args[0]);
				
			return true;
		} else if(cmd.getName().equalsIgnoreCase("tpall")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "tpall")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			TeleportHandler.teleportAll(player);
			
			return true;
		} else if(cmd.getName().equalsIgnoreCase("tpworld")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "tpworld")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			if(args.length == 1)
				TeleportHandler.teleportWorld(player, args[0]);
			
			return true;
		}
		return false;
	}
}
