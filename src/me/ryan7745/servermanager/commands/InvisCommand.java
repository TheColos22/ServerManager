package me.ryan7745.servermanager.commands;

import java.util.HashMap;

import me.ryan7745.servermanager.ConfigUtil;
import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvisCommand implements CommandExecutor {
	
	ServerManager plugin;
	public InvisCommand(ServerManager instance) {
		plugin = instance;
	}
	
	public HashMap<Player, Boolean> hidden = new HashMap<Player,Boolean>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("invis")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "invis")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			
				for(Player p: player.getWorld().getPlayers()){
					p.hidePlayer(player);
				}
		}
		return false;
	}
}
