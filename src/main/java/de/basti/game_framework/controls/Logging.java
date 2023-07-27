package de.basti.game_framework.controls;

import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import java.util.logging.SimpleFormatter;



public class Logging {
	public static final Logger LOGGER = Logger.getLogger("de.basti.game_framework");
	
	public static void init(){
		
		LOGGER.setLevel(Level.ALL);
		ConsoleHandler h = new ConsoleHandler();
		h.setLevel(Level.ALL);
		LOGGER.addHandler(h);
		LOGGER.finest("Initialized root logger");

		

	}
}
