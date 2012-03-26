package me.ryan7745.servermanager.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import me.ryan7745.servermanager.ServerManager;
import me.ryan7745.servermanager.Util;
import javax.swing.JToggleButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ServerManagerGUI {

	public JFrame frame;
	
	ServerManager plugin;
	ActionHandler actHandler;
	
	public TabConsole consoleTab;
	public TabWorld worldTab;
	public TabPlayer playerTab;

	/**
	 * Create the application.
	 */
	public ServerManagerGUI(ServerManager instance) {
		plugin = instance;
		actHandler = new ActionHandler(this);
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setName("ServerManagerGUI");
		frame.setTitle("ServerManager GUI");
		
		Container content = frame.getContentPane();
	    content.setBackground(Color.GRAY);
	    content.setLayout(new BorderLayout());
	    
	    //new tabbed panel
	    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	    
	    worldTab = new TabWorld();
	    consoleTab = new TabConsole();
	    playerTab = new TabPlayer();
	    
	    //add tabs
	    tabbedPane.addTab("Console", null, consoleTab, null);
	    tabbedPane.addTab("World", null, worldTab, null);
	    tabbedPane.addTab("Player", null, playerTab, null);
	    
	    JPanel topBar = new JPanel();
	    topBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    topBar.setBackground(Color.getHSBColor(0.5f, 0.4f, 0.3f));
	    topBar.setBorder(new EmptyBorder(10, 10, 10, 10));
	    
	    JLabel logo = new JLabel("Server Manager " + "v" + Util.pdfFile.getVersion());
	    logo.setForeground(Color.WHITE);
	    logo.setHorizontalAlignment(SwingConstants.LEFT);
	    logo.setFont(new Font(null, Font.ITALIC, 20));
	    topBar.add(logo);
	   
	    
	    content.add(topBar, BorderLayout.NORTH);
	    content.add(tabbedPane, BorderLayout.CENTER);
	    
	    //set window settings
		frame.setBounds(100, 100, 1200, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
