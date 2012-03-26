package me.ryan7745.servermanager.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TabPlayer extends JPanel {
	public DefaultListModel listModel;
	JList list;
	
	public TabPlayer(){ 
		setLayout(new BorderLayout());
		
		listModel = new DefaultListModel();
		for(Player p: Bukkit.getOnlinePlayers()){
			listModel.addElement(p.getName());
		}
 
        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setMinimumSize(listScrollPane.getPreferredSize());
        
        JPanel actions = new JPanel();
        actions.setBorder(new EmptyBorder(10, 10, 10, 10));
        actions.setLayout(new GridLayout(2, 2));
        
        JButton btnOp = new JButton("Op");
	    btnOp.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent paramMouseEvent) {
	    		if(list.getSelectedIndex() == -1) return;
	    		ActionHandler.OpPlayer(list.getSelectedValue());
	    	}
	    });
	    
	    JButton btnDeOp = new JButton("DeOp");
	    btnDeOp.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent paramMouseEvent) {
	    		if(list.getSelectedIndex() == -1) return;
	    		ActionHandler.DeOpPlayer(list.getSelectedValue());
	    	}
	    });
        
        JButton btnBan = new JButton("Ban");
	    btnBan.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent paramMouseEvent) {
	    		if(list.getSelectedIndex() == -1) return;
	    		ActionHandler.BanPlayer(list.getSelectedValue());
	    	}
	    });
	    
	    JButton btnKick = new JButton("Kick");
	    btnKick.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent paramMouseEvent) {
	    		if(list.getSelectedIndex() == -1) return;
	    		ActionHandler.kickPlayer(list.getSelectedValue());
	    	}
	    });
	    
	    actions.add(btnOp);
	    actions.add(btnDeOp);
	    actions.add(btnBan);
	    actions.add(btnKick);
	    
        add(listScrollPane, BorderLayout.WEST);
        add(actions, BorderLayout.CENTER);
	}
	
}
