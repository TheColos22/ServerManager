package me.ryan7745.servermanager.gui;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogHandler extends Handler {

	ServerManagerGUI smg;
	public LogHandler(ServerManagerGUI smginstance){
		smg = smginstance;
	}
	
	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publish(LogRecord record) {
		if (!isLoggable(record))
			return;
		
		// Output the formatted data to the file
		smg.consoleTab.addToConsole(record.getMessage());
	}

}
