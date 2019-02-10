package main.keyboard;

import java.util.Arrays;

import main.ControllerUser;
import net.java.games.input.Component;

/**
 * For use with a keyboard: sends all keys currently pressed to a callback function.
 *
 * @author Joe Desmond
 */
public class KeyboardController implements ControllerUser {
	private final KeyCallbackFunction callbackFunc;
	
	public KeyboardController(final KeyCallbackFunction _callbackFunc) {
		callbackFunc = _callbackFunc;
	}

	@Override
	public void update(final Component[] components) {
		Arrays.<Component>stream(components)
			.filter(c -> c.getPollData() != 0)
			.forEach(c -> callbackFunc.keyPressed((Component.Identifier.Key) c.getIdentifier()));
	}
}
