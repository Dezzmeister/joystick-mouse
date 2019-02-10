package main;

import net.java.games.input.Component;

/**
 * Represents any class that uses {@link net.java.games.input.Component Component} info
 * from a polled {@link net.java.games.input.Controller Controller}.
 *
 * @author Joe Desmond
 */
public interface ControllerUser {
	void update(Component[] components);
}
