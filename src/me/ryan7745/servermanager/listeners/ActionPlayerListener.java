package me.ryan7745.servermanager.listeners;

import java.util.Random;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.actions.TentBuilder;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

public class ActionPlayerListener implements Listener {
	
	TentBuilder tentBuilder;
	
	ServerManager plugin;
	public ActionPlayerListener(ServerManager instance){
		plugin = instance;
		tentBuilder = new TentBuilder(instance);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		PlayerInventory inv = player.getInventory();
		Action a = event.getAction();
		if(a.equals(Action.RIGHT_CLICK_BLOCK)){
			Block cb = event.getClickedBlock();
			if(player.getItemInHand().getType().equals(Material.MAGMA_CREAM) &&
				inv.contains(Material.WOOL, 35) &&
				inv.contains(Material.FENCE, 3)){
				tentBuilder.buildTent(cb);
				ItemStack wool = new ItemStack(Material.WOOL, 35);
				ItemStack fence = new ItemStack(Material.FENCE, 3);
				ItemStack magmaCream = new ItemStack(Material.MAGMA_CREAM, 1);
				inv.remove(wool);
				inv.remove(fence);
				inv.remove(magmaCream);
			} else if(player.getItemInHand().getType().equals(Material.SAPLING) && player.getGameMode().equals(GameMode.CREATIVE)){
				TreeSpecies species = TreeSpecies.getByData(player.getItemInHand().getData().getData());
				Random r = new Random();
				int rand = r.nextInt(10);
				if(species.equals(TreeSpecies.GENERIC)){
					if(rand > 5)
						cb.getWorld().generateTree(cb.getLocation(), TreeType.TREE);
					else
						cb.getWorld().generateTree(cb.getLocation(), TreeType.BIG_TREE);
				} else if(species.equals(TreeSpecies.BIRCH)){
					cb.getWorld().generateTree(cb.getLocation(), TreeType.BIRCH);
				} else if(species.equals(TreeSpecies.JUNGLE)){
					if(rand > 8)
						cb.getWorld().generateTree(cb.getLocation(), TreeType.JUNGLE);
					else
						cb.getWorld().generateTree(cb.getLocation(), TreeType.SMALL_JUNGLE);
				} else if(species.equals(TreeSpecies.REDWOOD)){
					if(rand > 8)
						cb.getWorld().generateTree(cb.getLocation(), TreeType.TALL_REDWOOD);
					else
						cb.getWorld().generateTree(cb.getLocation(), TreeType.REDWOOD);
				}
			}
		} else if(a.equals(Action.LEFT_CLICK_BLOCK)){
			Block cb = event.getClickedBlock();
			if(cb.getType().equals(Material.TNT) && plugin.mainConf.getBoolean("general.old-tnt")){
				cb.setType(Material.AIR);
				Vector v = new Vector(0, 0.1, 0);
				TNTPrimed tnt = cb.getWorld().spawn(cb.getLocation(), TNTPrimed.class);
				tnt.setVelocity(v);
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
    public void VehicleExit(VehicleExitEvent event){
    	if (event.isCancelled()) return;
    	if(event.getExited() instanceof Player){
    		Vehicle ve = event.getVehicle();
    	    Player player = (Player)event.getExited();
    	    if (ve instanceof Boat) {
    	        ve.remove();
    	        if(player.getGameMode().equals(GameMode.SURVIVAL)){
    	        	PlayerInventory inventory = player.getInventory();
    	        	ItemStack boatstack = new ItemStack(Material.BOAT, 1);
    	        	inventory.addItem(new ItemStack[] { boatstack });
    	    	}
    	    } else if (ve instanceof Minecart) {
    	        ve.remove();
    	        if(player.getGameMode().equals(GameMode.SURVIVAL)){
    	        	PlayerInventory inventory = player.getInventory();
    	        	ItemStack cartstack = new ItemStack(Material.MINECART, 1);
    	        	inventory.addItem(new ItemStack[] { cartstack });
    	        }
    	    }
    	    
    	}
    }
}
