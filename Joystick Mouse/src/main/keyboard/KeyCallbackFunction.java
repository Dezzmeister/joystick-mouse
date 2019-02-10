package main.keyboard;

import net.java.games.input.Component;

/**
 * Specifies a template for behavior to be run whenever a key is pressed. Basically a <code>Consumer</><</>Component.Identifier.Key</>></></code>
 * that has been renamed for clarity.
 *
 * @author Joe Desmond
 */
@FunctionalInterface
public interface KeyCallbackFunction {
	void keyPressed(Component.Identifier.Key identifier);
}
