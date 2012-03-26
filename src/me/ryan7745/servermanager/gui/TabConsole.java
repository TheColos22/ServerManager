package me.ryan7745.servermanager.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class TabConsole extends JPanel {
	private JTextField logInput;
	private JScrollPane log;
	private JTextPane logTextPane;
	public TabConsole(){
		//setup console tab
	    this.setLayout(new BorderLayout());
	    
	    logTextPane = new JTextPane();
	    logTextPane.setEditable(false);
	    logTextPane.setFocusable(false);
	    log = new JScrollPane(logTextPane);
	    this.add(log, BorderLayout.CENTER);
	    
	    logInput = new JTextField();
	    logInput.addKeyListener(new KeyAdapter() {
	    	@Override
	    	public void keyPressed(KeyEvent event) {
	    		int chr = event.getKeyCode();
	    		if(chr == KeyEvent.VK_ENTER){
	    			//handle send message
	    			String c = logInput.getText();
	    			ActionHandler.sendConsoleMessage(c);
	    			logInput.setText("");
	    		}
	    	}
	    });
	    this.add(logInput, BorderLayout.SOUTH);
	    logInput.setColumns(10);
	}
	
	public void addToConsole(String s){
		s = s.replaceAll("(\\[[0-9][0-9]?m)", "") + "\n";
		append(s, logTextPane);
		Document d = logTextPane.getDocument();
		logTextPane.select(d.getLength(), d.getLength());
	}
	
	private void append(String s, JTextPane pane) {
		try {
			Document doc = pane.getDocument();
			doc.insertString(doc.getLength(), s, null);
		} catch(BadLocationException exc) {
			exc.printStackTrace();
		}
	}
}
