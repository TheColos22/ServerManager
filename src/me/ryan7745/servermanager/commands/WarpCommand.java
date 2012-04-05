package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ConfigUtil;
import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {
	
	ServerManager plugin;
	public WarpCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("warp")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			
			if(args.length > 1){
				if(args[0].equalsIgnoreCase("create") && args.length == 2){
					if(!plugin.hasPermission(player.getName(), "warp")){
						Util.SendMessageNoPerms(sender);
						return true;
					}
					
					return true;
				} else if(args[0].equalsIgnoreCase("remove") && args.length == 2){
					if(!plugin.hasPermission(player.getName(), "warp")){
						Util.SendMessageNoPerms(sender);
						return true;
					}
					
					return true;
				} else if(args[0].equalsIgnoreCase("list")){
					if(!plugin.hasPermission(player.getName(), "warp")){
						Util.SendMessageNoPerms(sender);
						return true;
					}
					
					return true;
				} else {
					if(!plugin.hasPermission(player.getName(), "warp.use")){
						Util.SendMessageNoPerms(sender);
						return true;
					}
					if(plugin.warpConf.get("warps." + args[0]) != null){
						String[] splitLoc = plugin.warpConf.getString("warps." + args[0]).split(":");
						if(splitLoc.length != 3){
							player.sendMessage(Util.formatMessage(args[0] + " has an invalid location"));
							return true;
						}
						
					}
					return true;
				}
			}
			return false;
		}
		return false;
	}
}
