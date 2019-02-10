package main;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;

import net.java.games.input.Component;

/**
 * Uses axis data from a {@link net.java.games.input.Controller Controller} to control
 * movement of the mouse on the screen. Can be disabled to relinquish control to the
 * default device(s).
 *
 * @author Joe Desmond
 */
public class MouseMover implements ControllerUser {
	private static final Dimension SCREEN_DIM = Toolkit.getDefaultToolkit().getScreenSize();
	private static final int SCREEN_WIDTH = SCREEN_DIM.width;
	private static final int SCREEN_HEIGHT = SCREEN_DIM.height;
	
	private double xPos = SCREEN_WIDTH/2;
	private double yPos = SCREEN_HEIGHT/2;
	private double xAxis;
	private double yAxis;
	private final Robot robot;
	private volatile boolean mouseMoveEnabled = true;
	
	private boolean button1Pressed = false;
	private boolean button2Pressed = false;
	private boolean button3Pressed = false;
	private boolean button4Pressed = false;
	private boolean button5Pressed = false;
	
	public MouseMover() throws AWTException {
		robot = new Robot();
	}
	
	private final static double DEADZONE = 0.04;
	@Override
	public void update(final Component[] components) {
		if (mouseMoveEnabled) {
			xAxis = components[2].getPollData();
			yAxis = components[1].getPollData();
			double throttle = Math.abs((components[0].getPollData() - 1))/2;
						
			double xDelta = xAxis;
			double yDelta = yAxis;
			if (Math.abs(xDelta) < DEADZONE) {
				xDelta = 0;
			}
			if (Math.abs(yDelta) < DEADZONE) {
				yDelta = 0;
			}
			
			//System.out.println(xPos + "\t" + yPos);
			xPos = constrain(xPos + pixelScale(xDelta, throttle), 0, SCREEN_WIDTH);
			yPos = constrain(yPos + pixelScale(yDelta, throttle), 0, SCREEN_HEIGHT);
			
			robot.mouseMove((int) xPos, (int) yPos);
			
			if (components[3].getPollData() == 1 && !button1Pressed) {
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				button1Pressed = true;
			} else if (components[3].getPollData() == 0 && button1Pressed){
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				button1Pressed = false;
			}
			
			if (components[4].getPollData() == 1 && !button3Pressed) {
				robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
				button3Pressed = true;
			} else if (components[4].getPollData() == 0 && button3Pressed) {
				robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
				button3Pressed = false;
			}
			
			if (components[5].getPollData() == 1 && !button2Pressed) {
				robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
				button2Pressed = true;
			} else if (components[5].getPollData() == 0 && button2Pressed) {
				robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
				button2Pressed = false;
			}
		}
	}
	
	public void disable() {
		mouseMoveEnabled = false;
	}
	
	public void enable() {
		mouseMoveEnabled = true;
	}
	
	public static final double PIXEL_SCALE_FACTOR = 0.05;
	private double pixelScale(double axisVal, double throttle) {
		return axisVal * PIXEL_SCALE_FACTOR * throttle;
	}
	
	private double constrain(double value, int min, int max) {
		if (value < min) {
			return min;
		} else if (value >= max) {
			return max - 1;
		} else {
			return value;
		}
	}
}
