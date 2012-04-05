package me.ryan7745.servermanager.commands;

import java.util.Set;

import me.ryan7745.servermanager.ConfigUtil;
import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
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
			if(args.length > 0){
				if(args[0].equalsIgnoreCase("create") && args.length == 2){
					if(!plugin.hasPermission(player.getName(), "warp")){
						Util.SendMessageNoPerms(sender);
						return true;
					}
					
					if(plugin.warpConf.get("warps." + args[1]) != null){
						player.sendMessage(Util.formatMessage("Warp " + ChatColor.GRAY + args[1] + ChatColor.BLUE + " already exists."));
						return true;
					}
					Location pLoc = player.getLocation();
					String loc_string = player.getWorld().getName() + ":" + pLoc.getBlockX() + ":" + pLoc.getBlockY() + ":" + pLoc.getBlockZ();
					
					plugin.warpConf.set("warps." + args[1], loc_string);
					ConfigUtil.saveConfig(plugin.warpConf, "warps");
					player.sendMessage(Util.formatMessage("Warp " + ChatColor.GRAY + args[1] + ChatColor.BLUE + " is now set."));
					
					return true;
				} else if(args[0].equalsIgnoreCase("remove") && args.length == 2){
					if(!plugin.hasPermission(player.getName(), "warp")){
						Util.SendMessageNoPerms(sender);
						return true;
					}
					
					if(plugin.warpConf.get("warps." + args[1]) == null){
						player.sendMessage(Util.formatMessage("Warp " + ChatColor.GRAY + args[1] + ChatColor.BLUE + " does not exist."));
						return true;
					}
					
					plugin.warpConf.set("warps."+args[1], null);
					ConfigUtil.saveConfig(plugin.warpConf, "warps");
					player.sendMessage(Util.formatMessage("Warp " + ChatColor.GRAY + args[1] + ChatColor.BLUE + " has been removed."));
					
					return true;
				} else if(args[0].equalsIgnoreCase("list")){
					if(!plugin.hasPermission(player.getName(), "warp")){
						Util.SendMessageNoPerms(sender);
						return true;
					}
					
					Set<String> warps_list = plugin.warpConf.getConfigurationSection("warps").getKeys(true);
					
					int page = 0, count = 0;
					if(args.length > 1){
						try{
							page = Integer.parseInt(args[1]) - 1;
						} catch (Exception e){
							page = 0;
						}

					}
						
					int start = page * 8;
					int finish = start + 8;
					Util.debug(start + " " + finish);
					
					if(start > warps_list.size()){
						player.sendMessage(Util.formatMessage("Too big a page number!"));
						return true;
					}
					
					player.sendMessage(Util.formatMessage("------------------" + " Warps page: " + ChatColor.GRAY + (page + 1) + " " + ChatColor.BLUE + "------------------"));
					for(String s: warps_list){
						count++;
						if(count <= start) continue;
						if(count > finish) break;
						player.sendMessage(ChatColor.GRAY + s);
					}
					player.sendMessage(Util.formatMessage("----------------------------------------------"));
					
					return true;
				} else {
					if(!plugin.hasPermission(player.getName(), "warp.use")){
						Util.SendMessageNoPerms(sender);
						return true;
					}
					if(plugin.warpConf.get("warps." + args[0]) != null){
						String[] splitLoc = plugin.warpConf.getString("warps." + args[0]).split(":");
						if(splitLoc.length != 4){
							player.sendMessage(Util.formatMessage("Warp " + ChatColor.GRAY + args[0] + ChatColor.BLUE + " has an invalid location."));
							return true;
						}
						World w = Bukkit.getWorld(splitLoc[0]);
						Util.debug("Warping... world:" + w.toString());
						if(w == null) return false;
						int x, y, z;
						x = Integer.parseInt(splitLoc[1]);
						y = Integer.parseInt(splitLoc[2]);
						z = Integer.parseInt(splitLoc[3]);
						Location loc = new Location(w, x, y, z, player.getLocation().getYaw(), player.getLocation().getPitch());
						player.sendMessage(Util.formatMessage("Warping you to " + ChatColor.GRAY + args[0] +  ChatColor.BLUE + "."));
						player.teleport(loc);
					}
					return true;
				}
			}
			return false;
		}
		return false;
	}
}
