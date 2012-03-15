package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
	
	ServerManager plugin;
	public GamemodeCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("gamemode")){
			if(!(sender instanceof Player)){
				if(args.length == 1){
					if(Bukkit.getPlayer(args[0]) != null){
						Player target = Bukkit.getPlayer(args[0]);
						if(target.getGameMode().equals(GameMode.CREATIVE)){
							target.setGameMode(GameMode.SURVIVAL);
						} else {
							target.setGameMode(GameMode.CREATIVE);
						}
						return true;
					} else {
						sender.sendMessage(Util.formatMessage("That player does not exist."));
					}
				}
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "gamemode")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			if(args.length == 1){
				if(Bukkit.getPlayer(args[0]) != null){
					Player target = Bukkit.getPlayer(args[0]);
					if(target.getGameMode().equals(GameMode.CREATIVE)){
						target.setGameMode(GameMode.SURVIVAL);
					} else {
						target.setGameMode(GameMode.CREATIVE);
					}
					return true;
				} else {
					sender.sendMessage(Util.formatMessage("That player does not exist."));
				}
			} else {
				if(player.getGameMode().equals(GameMode.CREATIVE)){
					player.setGameMode(GameMode.SURVIVAL);
				} else {
					player.setGameMode(GameMode.CREATIVE);
				}
				return true;
			}
		}
		return false;
	}

}
