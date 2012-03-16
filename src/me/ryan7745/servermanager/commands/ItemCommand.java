package me.ryan7745.servermanager.commands;

import java.util.HashMap;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ItemCommand implements CommandExecutor {

	ServerManager plugin;
	public ItemCommand(ServerManager instance){
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("item")) {
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "item")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			if(args.length == 0){
				return false;
			}
			if(args.length > 0){
				Player p = player;
				Integer ID = null;
				Short data = null;
				Integer amount = plugin.mainConf.getInt("item.default-stack-size", 64);
				String called = args[0];
				String cdata = null;
				if(called.contains(":")){
					String[] calleds = called.split(":");
					called = calleds[0].trim();
					cdata = calleds[1].trim();
				}
				try{
					ID = Integer.parseInt(called);
				} catch(Exception e){
					try {
	                    ID = Material.getMaterial(called.trim().replace(" ", "_").toUpperCase()).getId();
	                } catch (Exception e2) {
	                    sender.sendMessage(Util.formatMessage("That block does not exist!"));
	                    return true;
	                }
				}
				if(ID == 0){
					sender.sendMessage(Util.formatMessage("Cannot give air!"));
					return true;
				}
				
				if (Material.getMaterial(ID) == null) {
	                sender.sendMessage(Util.formatMessage("Invalid item ID!"));
	                return true;
	            }
				if (cdata != null) {
					try {
						data = Short.parseShort(cdata);
					} catch (Exception e){
						sender.sendMessage(Util.formatMessage("The metadata was invalid!"));
						return true;
					}
					if (data < 0) {
						sender.sendMessage(Util.formatMessage("The metadata was invalid!"));
	                    return true;
	                }
				}
				if(args.length == 2){
					Integer amt = null;
					try {
						amt = Integer.parseInt(args[1]);
					} catch (Exception e){
						sender.sendMessage(Util.formatMessage("Invalid amount!"));
						return true;
					}
					if(amt < 1){
						amt = 1;
					}
					if(amt > 64){
						amt = 64;
					}
					amount = amt;
				}
				
				ItemStack toInv;
				if(data != null){
					toInv = new ItemStack(Material.getMaterial(ID), amount, data);
				} else {
					toInv = new ItemStack(Material.getMaterial(ID), amount);
				}
				HashMap<Integer, ItemStack> left = p.getInventory().addItem(toInv);
                if (!left.isEmpty() && plugin.mainConf.getBoolean("item.drop-extra-items", true)) {
                    for (ItemStack item : left.values()) {
                        p.getWorld().dropItemNaturally(p.getLocation(), item);
                    }
                }
                sender.sendMessage(Util.formatMessage("Giving " + ChatColor.GRAY + amount + ChatColor.BLUE + " of " + ChatColor.GRAY + Material.getMaterial(ID).toString().toLowerCase().replace("_", " ") + ChatColor.BLUE + " to " + ChatColor.GRAY + p.getName() + ChatColor.BLUE + "."));
                return true;
			}
		}
		return false;
	}
}
