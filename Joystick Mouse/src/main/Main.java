package main;

import java.awt.AWTException;

import main.keyboard.KeyboardController;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;

public class Main {
	private static Controller joystick = null;
	private static Controller keyboard = null;
	private static ControllerManager joystickManager = null;
	private static ControllerManager keyboardManager = null;
	private static MouseMover mouseMover;
	private static KeyboardController keyboardController;
	
	public static void main(String[] args) {
		initializeControllers();
		initializeAndStartKeyboardManager();
		try {
			initializeAndStartJoystickManager();
			//mouseMover.disable();
			while (true) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		debug_printControllerInfo();
	}
	
	private static void keyCallbackFunc(final Component.Identifier.Key identifier) {
		if (identifier == Component.Identifier.Key.J) {
			System.out.println("J pressed");
		}
	}
	
	private static void initializeAndStartJoystickManager() throws AWTException {
		if (joystick != null) {
			joystickManager = new ControllerManager(joystick);
			
			mouseMover = new MouseMover();
			joystickManager.addControllerUser(mouseMover);
			joystickManager.start();
		} else {
			System.err.println("No joystick plugged in!");
		}
	}
	
	private static void initializeAndStartKeyboardManager() {
		if (keyboard != null) {
			keyboardManager = new ControllerManager(keyboard);
			keyboardController = new KeyboardController(Main::keyCallbackFunc);
			
			keyboardManager.addControllerUser(keyboardController);
			keyboardManager.start();
		} else {
			System.err.println("No keyboard plugged in!");
		}
	}
	
	private static void initializeControllers() {
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		
		for (Controller c : controllers) {
			if (c.getType() == Type.STICK) {
				joystick = c;
			} else if (c.getType() == Type.KEYBOARD) {
				keyboard = c;
			}
		}
	}
	
	@SuppressWarnings("unused")
	private static void debug_printControllerInfo() {
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for (Controller c : controllers) {
			System.out.println(c);
		}
	}
}
