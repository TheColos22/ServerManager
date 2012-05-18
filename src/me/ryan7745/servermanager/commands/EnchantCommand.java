package me.ryan7745.servermanager.commands;

import java.util.HashMap;
import java.util.Map;

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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantCommand implements CommandExecutor {
	Map<String, Enchantment> enchantment = new HashMap();
	
	ServerManager plugin;
	public EnchantCommand(ServerManager instance) {
		plugin = instance;
		setEnchantments();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("enchant")){
			if(!(sender instanceof Player)){
				Util.SendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player.getName(), "enchant")){
				Util.SendMessageNoPerms(sender);
				return true;
			}
			
			ItemStack is = player.getItemInHand();
			
			if(!this.enchantment.containsKey(args[0])){
				player.sendMessage(Util.formatMessage("Enchantment not found."));
				return true;
			}
            Enchantment e = (Enchantment)this.enchantment.get(args[0]);
            if (e.canEnchantItem(is)) {
              int max = e.getMaxLevel();
              int current = Integer.parseInt(args[1]);
              if (current > max) {
                current = max;
                is.addEnchantment((Enchantment)this.enchantment.get(args[0]), current);
                player.sendMessage(Util.formatMessage("Item enchanted to max level."));
                return true;
              }
              is.addEnchantment((Enchantment)this.enchantment.get(args[0]), current);
              player.sendMessage(Util.formatMessage("Item enchanted to level " + current + "."));
              return true;
            }
			
            player.sendMessage(Util.formatMessage("Cannot enchant item."));
			return true;
		}
		return false;
	}
	
	public void setEnchantments() {
	    this.enchantment.put("aquaaffinity", Enchantment.WATER_WORKER);
	    this.enchantment.put("waterworker", Enchantment.WATER_WORKER);
	    this.enchantment.put("water_worker", Enchantment.WATER_WORKER);
	    this.enchantment.put("silktouch", Enchantment.SILK_TOUCH);
	    this.enchantment.put("silk_touch", Enchantment.SILK_TOUCH);
	    this.enchantment.put("projectileprotection", Enchantment.PROTECTION_PROJECTILE);
	    this.enchantment.put("protectionprojectile", Enchantment.PROTECTION_PROJECTILE);
	    this.enchantment.put("protection_projetile", Enchantment.PROTECTION_PROJECTILE);
	    this.enchantment.put("fireprotection", Enchantment.PROTECTION_FIRE);
	    this.enchantment.put("protectionfire", Enchantment.PROTECTION_FIRE);
	    this.enchantment.put("protection_fire", Enchantment.PROTECTION_FIRE);
	    this.enchantment.put("protectionfall", Enchantment.PROTECTION_FALL);
	    this.enchantment.put("protection_fall", Enchantment.PROTECTION_FALL);
	    this.enchantment.put("featherfalling", Enchantment.PROTECTION_FALL);
	    this.enchantment.put("blastprotection", Enchantment.PROTECTION_EXPLOSIONS);
	    this.enchantment.put("protectionexplosions", Enchantment.PROTECTION_EXPLOSIONS);
	    this.enchantment.put("protection_explosions", Enchantment.PROTECTION_EXPLOSIONS);
	    this.enchantment.put("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
	    this.enchantment.put("protectionenvironmental", Enchantment.PROTECTION_ENVIRONMENTAL);
	    this.enchantment.put("protection_environmental", Enchantment.PROTECTION_ENVIRONMENTAL);
	    this.enchantment.put("respiration", Enchantment.OXYGEN);
	    this.enchantment.put("oxygen", Enchantment.OXYGEN);
	    this.enchantment.put("looting", Enchantment.LOOT_BONUS_MOBS);
	    this.enchantment.put("lootbonusmobs", Enchantment.LOOT_BONUS_MOBS);
	    this.enchantment.put("loot_bonus_mobs", Enchantment.LOOT_BONUS_MOBS);
	    this.enchantment.put("fortune", Enchantment.LOOT_BONUS_BLOCKS);
	    this.enchantment.put("lootbonusblocks", Enchantment.LOOT_BONUS_BLOCKS);
	    this.enchantment.put("loot_bonus_blocks", Enchantment.LOOT_BONUS_BLOCKS);
	    this.enchantment.put("knockback", Enchantment.KNOCKBACK);
	    this.enchantment.put("fire_aspect", Enchantment.FIRE_ASPECT);
	    this.enchantment.put("fireaspect", Enchantment.FIRE_ASPECT);
	    this.enchantment.put("unbreaking", Enchantment.DURABILITY);
	    this.enchantment.put("durability", Enchantment.DURABILITY);
	    this.enchantment.put("efficiency", Enchantment.DIG_SPEED);
	    this.enchantment.put("dig_speed", Enchantment.DIG_SPEED);
	    this.enchantment.put("digspeed", Enchantment.DIG_SPEED);
	    this.enchantment.put("smite", Enchantment.DAMAGE_UNDEAD);
	    this.enchantment.put("damageundead", Enchantment.DAMAGE_UNDEAD);
	    this.enchantment.put("damage_undead", Enchantment.DAMAGE_UNDEAD);
	    this.enchantment.put("baneofarthropods", Enchantment.DAMAGE_ARTHROPODS);
	    this.enchantment.put("damage_arthropods", Enchantment.DAMAGE_ARTHROPODS);
	    this.enchantment.put("damagearthropods", Enchantment.DAMAGE_ARTHROPODS);
	    this.enchantment.put("damagespider", Enchantment.DAMAGE_ARTHROPODS);
	    this.enchantment.put("sharpness", Enchantment.DAMAGE_ALL);
	    this.enchantment.put("damageall", Enchantment.DAMAGE_ALL);
	    this.enchantment.put("damage_all", Enchantment.DAMAGE_ALL);
	  }
}
