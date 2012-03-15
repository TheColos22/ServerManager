package me.ryan7745.servermanager.commands;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
	
	ServerManager plugin;
	public SpawnCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spawn")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "spawn")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			Location loc = player.getWorld().getSpawnLocation();
			if(loc.getBlock().getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getType().equals(Material.AIR)){
				loc = loc.getWorld().getHighestBlockAt(loc).getLocation();
			}

			player.teleport(loc);
			player.sendMessage(Util.formatMessage("Teleporting to " + ChatColor.GRAY + "spawn" + ChatColor.BLUE + "."));
			return true;
		} else if(cmd.getName().equalsIgnoreCase("setspawn")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "setspawn")){
				Util.SendMessageNoPerms(sender);
				return true;
			}

			player.getWorld().setSpawnLocation(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
			player.sendMessage(Util.formatMessage("Spawn location set for world " + ChatColor.GRAY + player.getWorld().getName() + ChatColor.BLUE + "."));
			return true;
		}
		return false;
	}
}
