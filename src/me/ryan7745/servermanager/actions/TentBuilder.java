package me.ryan7745.servermanager.actions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import me.ryan7745.servermanager.ServerManager;

public class TentBuilder {

	ServerManager plugin;
	public TentBuilder(ServerManager instance) {
		plugin = instance;
	}
	
	public void buildTent(Block baseBlock){
		BlockFace LocalEast = BlockFace.EAST;
		BlockFace LocalWest = BlockFace.WEST;
		BlockFace LocalNorth = BlockFace.NORTH;
		BlockFace LocalSouth = BlockFace.SOUTH;
		BlockFace LocalDown = BlockFace.DOWN;
		BlockFace LocalUp = BlockFace.UP;
		
		Block row1b1 = baseBlock.getRelative(LocalEast, 2).getRelative(LocalUp);
		Block row2b1 = baseBlock.getRelative(LocalEast, 2).getRelative(LocalUp, 2);
		Block row3b1 = baseBlock.getRelative(LocalEast).getRelative(LocalUp, 3);
		Block row4b1 = baseBlock.getRelative(LocalUp, 4);
		Block row5b1 = baseBlock.getRelative(LocalWest).getRelative(LocalUp, 3);
		Block row6b1 = baseBlock.getRelative(LocalWest, 2).getRelative(LocalUp, 2);
		Block row7b1 = baseBlock.getRelative(LocalWest, 2).getRelative(LocalUp);
		
		Block row1b2, row1b3, row1b4, row1b5;
		row1b2 = row1b1.getRelative(LocalNorth);
		row1b3 = row1b2.getRelative(LocalNorth);
		row1b4 = row1b3.getRelative(LocalNorth);
		row1b5 = row1b4.getRelative(LocalNorth);
		Block row2b2, row2b3, row2b4, row2b5;
		row2b2 = row2b1.getRelative(LocalNorth);
		row2b3 = row2b2.getRelative(LocalNorth);
		row2b4 = row2b3.getRelative(LocalNorth);
		row2b5 = row2b4.getRelative(LocalNorth);
		Block row3b2, row3b3, row3b4, row3b5;
		row3b2 = row3b1.getRelative(LocalNorth);
		row3b3 = row3b2.getRelative(LocalNorth);
		row3b4 = row3b3.getRelative(LocalNorth);
		row3b5 = row3b4.getRelative(LocalNorth);
		Block row4b2, row4b3, row4b4, row4b5;
		row4b2 = row4b1.getRelative(LocalNorth);
		row4b3 = row4b2.getRelative(LocalNorth);
		row4b4 = row4b3.getRelative(LocalNorth);
		row4b5 = row4b4.getRelative(LocalNorth);
		Block row5b2, row5b3, row5b4, row5b5;
		row5b2 = row5b1.getRelative(LocalNorth);
		row5b3 = row5b2.getRelative(LocalNorth);
		row5b4 = row5b3.getRelative(LocalNorth);
		row5b5 = row5b4.getRelative(LocalNorth);
		Block row6b2, row6b3, row6b4, row6b5;
		row6b2 = row6b1.getRelative(LocalNorth);
		row6b3 = row6b2.getRelative(LocalNorth);
		row6b4 = row6b3.getRelative(LocalNorth);
		row6b5 = row6b4.getRelative(LocalNorth);
		Block row7b2, row7b3, row7b4, row7b5;
		row7b2 = row7b1.getRelative(LocalNorth);
		row7b3 = row7b2.getRelative(LocalNorth);
		row7b4 = row7b3.getRelative(LocalNorth);
		row7b5 = row7b4.getRelative(LocalNorth);
		
		Block pole1 = baseBlock.getRelative(LocalUp);
		Block pole2 = baseBlock.getRelative(LocalUp, 2);
		Block pole3 = baseBlock.getRelative(LocalUp, 3);
		
		Material poleBlock = Material.FENCE;
		pole1.setType(poleBlock);
		pole2.setType(poleBlock);
		pole3.setType(poleBlock);
		
		Material tentBlock = Material.WOOL;
		row1b1.setType(tentBlock);
		row1b2.setType(tentBlock);
		row1b3.setType(tentBlock);
		row1b4.setType(tentBlock);
		row1b5.setType(tentBlock);
		row2b1.setType(tentBlock);
		row2b2.setType(tentBlock);
		row2b3.setType(tentBlock);
		row2b4.setType(tentBlock);
		row2b5.setType(tentBlock);
		row3b1.setType(tentBlock);
		row3b2.setType(tentBlock);
		row3b3.setType(tentBlock);
		row3b4.setType(tentBlock);
		row3b5.setType(tentBlock);
		row4b1.setType(tentBlock);
		row4b2.setType(tentBlock);
		row4b3.setType(tentBlock);
		row4b4.setType(tentBlock);
		row4b5.setType(tentBlock);
		row5b1.setType(tentBlock);
		row5b2.setType(tentBlock);
		row5b3.setType(tentBlock);
		row5b4.setType(tentBlock);
		row5b5.setType(tentBlock);
		row6b1.setType(tentBlock);
		row6b2.setType(tentBlock);
		row6b3.setType(tentBlock);
		row6b4.setType(tentBlock);
		row6b5.setType(tentBlock);
		row7b1.setType(tentBlock);
		row7b2.setType(tentBlock);
		row7b3.setType(tentBlock);
		row7b4.setType(tentBlock);
		row7b5.setType(tentBlock);
		
		Material backBlock = tentBlock;
		Block backb1 = row4b5.getRelative(LocalDown);
		Block backb2 = backb1.getRelative(LocalDown);
		Block backb3 = backb2.getRelative(LocalEast);
		Block backb4 = backb2.getRelative(LocalWest);
		Block backb5 = backb4.getRelative(LocalDown);
		Block backb6 = backb5.getRelative(LocalEast);
		Block backb7 = backb6.getRelative(LocalEast);
		
		backb1.setType(backBlock);
		backb2.setType(backBlock);
		backb3.setType(backBlock);
		backb4.setType(backBlock);
		backb5.setType(backBlock);
		backb6.setType(backBlock);
		backb7.setType(backBlock);
	}
}
