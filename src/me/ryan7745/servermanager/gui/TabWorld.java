package me.ryan7745.servermanager.gui;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TabWorld extends JPanel {

	public TabWorld(){ 
		setLayout(new BorderLayout());
		
		JPanel timeAndWeather = new JPanel();
		timeAndWeather.setLayout(new GridLayout(1, 2));
		
	    JPanel timeSettings = new JPanel();
	    timeSettings.setLayout(new GridLayout(2, 3, 4, 4));
	    
	    JLabel labelTime = new JLabel("Time");
	    labelTime.setHorizontalAlignment(SwingConstants.CENTER);
	    labelTime.setFont(new Font(null, Font.BOLD, 20));
	    
	    JButton btnDay = new JButton("Day");
	    btnDay.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent paramMouseEvent) {
	    		ActionHandler.timeDay();
	    	}
	    });
	    JButton btnNight = new JButton("Night");
	    btnNight.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent paramMouseEvent) {
	    		ActionHandler.timeNight();
	    	}
	    });
	    
	    timeSettings.add(Box.createRigidArea(new Dimension(0,5)));
	    timeSettings.add(labelTime);
	    timeSettings.add(Box.createRigidArea(new Dimension(0,5)));
	    timeSettings.add(btnDay);
	    timeSettings.add(btnNight);
	    
	    timeAndWeather.add(timeSettings);
	    
	    JPanel weatherSettings = new JPanel();
	    weatherSettings.setLayout(new GridLayout(2, 1, 4, 4));
	    
	    JLabel labelWeather = new JLabel("Weather");
	    labelWeather.setHorizontalAlignment(SwingConstants.CENTER);
	    labelWeather.setFont(new Font(null, Font.BOLD, 20));
	    
	    JButton btnSun = new JButton("Sun");
	    btnSun.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent paramMouseEvent) {
	    		ActionHandler.weatherSun();
	    	}
	    });
	    JButton btnRain = new JButton("Rain");
	    btnRain.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent paramMouseEvent) {
	    		ActionHandler.weatherRain();
	    	}
	    });
	    JButton btnThunder = new JButton("Thunder");
	    btnThunder.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent paramMouseEvent) {
	    		ActionHandler.weatherThunder();
	    	}
	    });

	    weatherSettings.add(labelWeather);
	    
	    JPanel weatherButtons = new JPanel();
	    weatherButtons.setLayout(new GridLayout(1, 3));
	    weatherButtons.add(btnSun);
	    weatherButtons.add(btnRain);
	    weatherButtons.add(btnThunder);
	    
	    weatherSettings.add(weatherButtons);
	    
	    timeAndWeather.add(weatherSettings);
	    
	    this.add(timeAndWeather, BorderLayout.CENTER);
	    
	    
	}
}
