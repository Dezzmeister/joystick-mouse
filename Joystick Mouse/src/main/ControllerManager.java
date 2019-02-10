package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.java.games.input.Component;
import net.java.games.input.Controller;

/**
 * Polls a {@link net.java.games.input.Controller Controller} on a separate {@link java.lang.Thread Thread} 
 * and dispatches updated input info to various {@link ControllerUser ControllerUsers}, which use the data as they see fit.
 *
 * @author Joe Desmond
 */
public class ControllerManager {
	private final Controller controller;
	private final List<ControllerUser> controllerUsers = new ArrayList<ControllerUser>();
	private final Poller poller;
	private Thread thread;
	
	public ControllerManager(final Controller _controller, final ControllerUser ... _controllerUsers) {
		controller = _controller;
		poller = new Poller();
		
		Arrays.<ControllerUser>stream(_controllerUsers).forEach(controllerUsers::add);
	}
	
	public void addControllerUser(final ControllerUser user) {
		controllerUsers.add(user);
	}
	
	public void start() {
		poller.isRunning = true;
		thread = new Thread(poller, "Joystick Poller Thread");
		thread.start();
	}
	
	public void stop() {
		poller.isRunning = false;
	}
	
	/**
	 * Polls a <i>Controller</i> and sends data to every <i>ControllerUser</i> requiring it.
	 *
	 * @author Joe Desmond
	 */
	private class Poller implements Runnable {
		public volatile boolean isRunning = false;
		
		@Override
		public void run() {
			if (isRunning) {
				while (controller.poll())	{
					Component[] components = controller.getComponents();
					controllerUsers.forEach((c) -> c.update(components));
				}
			}
		}
		
	}
}
