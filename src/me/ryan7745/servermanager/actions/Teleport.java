package me.ryan7745.servermanager.actions;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

public class Teleport {

	ServerManager plugin;
	public Teleport(ServerManager instance) {
		plugin = instance;
	}

	public void teleport(String playerName, String targetName){
		Player player = Bukkit.getPlayer(targetName);
		if(player == null){
			player.sendMessage(Util.formatMessage("Could not find player."));
			return;
		}
		teleport(player, targetName);
	}
	
	public void teleport(Player player, String targetName){
		Player target = Bukkit.getPlayer(targetName);
		if(target == null){
			player.sendMessage(Util.formatMessage("Could not find player."));
			return;
		}
		player.sendMessage(Util.formatMessage("Teleporting to " + ChatColor.GRAY + target.getName() + ChatColor.BLUE + "."));
		player.teleport(target.getLocation());
		return;
	}
	
	public void teleportHere(Player player, String targetName){
		Player target = Bukkit.getPlayer(targetName);
		if(target == null){
			player.sendMessage(Util.formatMessage("Could not find player."));
			return;
		}
		player.sendMessage(Util.formatMessage("Teleporting " + ChatColor.GRAY + target.getName() + ChatColor.BLUE + " to you."));
		target.teleport(player.getLocation());
		return;
	}
	
	public void teleportAll(Player player){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(!p.getName().equalsIgnoreCase(player.getName())){
				p.teleport(player.getLocation());
			}
		}
		player.sendMessage(Util.formatMessage("Teleporting " + ChatColor.GRAY + "everyone" + ChatColor.BLUE + " to you."));
		return;
	}
	
	public void teleportWorld(Player player, String worldName){
		World world = Bukkit.getWorld(worldName);
		if(world == null){
			File worldFile = new File(plugin.getServer().getWorldContainer(), worldName);
			if(worldFile.exists()){
				WorldCreator wcreator = new WorldCreator(worldName);
				wcreator.createWorld();
				wcreator.environment(World.Environment.NORMAL);
				plugin.getServer().createWorld(wcreator);
				world = Bukkit.getWorld(worldName);
				if(world != null)
					teleportWorld(player, world);
			}
			player.sendMessage(Util.formatMessage("World not found"));
			return;
		}
		teleportWorld(player, world);
		return;
	}
	
	private void teleportWorld(Player player, World world){
		Location loc = world.getSpawnLocation();
		if(loc.getBlock().getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getType().equals(Material.AIR)){
			loc = loc.getWorld().getHighestBlockAt(loc).getLocation();
		}
		player.teleport(loc);
		player.sendMessage(Util.formatMessage("You have teleported to world " + ChatColor.GRAY + world.getName()+ ChatColor.BLUE + "."));
	}
}
