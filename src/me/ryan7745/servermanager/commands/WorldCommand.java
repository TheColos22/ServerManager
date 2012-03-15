package me.ryan7745.servermanager.commands;

import java.io.File;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCommand implements CommandExecutor {
	
	ServerManager plugin;
	public WorldCommand(ServerManager instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("world")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "world")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			if(args.length == 1){
				World world = Bukkit.getWorld(args[0]);
				if(world != null){
					teleport(world, player);
					return true;
				} else {
					File worldFile = new File(this.plugin.getServer().getWorldContainer(), args[0]);
					if(worldFile.exists()){
						WorldCreator wcreator = new WorldCreator(args[0]);
						wcreator.createWorld();
						wcreator.environment(World.Environment.NORMAL);
						plugin.getServer().createWorld(wcreator);
						world = Bukkit.getWorld(args[0]);
						if(world != null){
							teleport(world, player);
						}
					}
					player.sendMessage(Util.formatMessage("World not found"));
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	private void teleport(World world, Player player){
		Location loc = world.getSpawnLocation();
		if(loc.getBlock().getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getType().equals(Material.AIR)){
			loc = loc.getWorld().getHighestBlockAt(loc).getLocation();
		}

		player.teleport(loc);
		player.sendMessage(Util.formatMessage("You have teleported to world " + ChatColor.GRAY + world.getName()+ ChatColor.BLUE + "."));
	}
}
